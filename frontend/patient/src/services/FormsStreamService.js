import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import webSocketsHelper from '../helper/WebSocketsHelper';
import { WS_URL } from '../config';

const url = `${WS_URL}/requests`;
const webSocket = new Stomp.client(url);

let token = null;
let internalForms = null;

const setToken = (newToken) => {
  token = newToken;
};

const subscribe = (formHandler) => {
  const setFormStatus = (newStatus) => {
    const newForm = { ...internalForms, status: newStatus };
    formHandler(newForm);
  };

  const setFieldState = (newInput, index) => {
    const oldFieldState = internalForms.state[index];
    internalForms.state[index] = { ...oldFieldState, value: newInput };

    const newForm = { ...internalForms };
    formHandler(newForm);
  };

  const buildSchema = (schema) => {
    const { fields } = schema;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const simple = fields.simple.map((s) => ({ ...s, type: 'simple' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));
    const allFields = choice.concat(simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const buildState = (state, simpleFields) => {
    const fields = state;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));

    const simple = simpleFields.map((s) => ({ type: 'simple', fieldNumber: s.fieldNumber, value: '' }));
    const allFields = choice.concat(simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const handleStateResponse = (response) => {
    const responseForm = response.payload;
    const schema = buildSchema(responseForm.schema);
    const state = buildState(responseForm.state, responseForm.schema.fields.simple);
    const { patientSign, employeeSign } = responseForm.schema;

    internalForms = {
      ...responseForm, schema, state, patientSign, employeeSign,
    };
    formHandler(internalForms);
  };

  const handleUpdateResponse = (response) => {
    setFieldState(response.payload.value, response.payload.fieldNumber);
  };

  const handleMoveResponse = (response) => {
    const newStatus = response.responseType.substr(5);
    setFormStatus(newStatus);
  };

  const handleResponse = (message) => {
    const response = JSON.parse(message.body);

    if (response.responseType === 'STATE') {
      handleStateResponse(response);
    } else if (response.responseType.substr(0, 4) === 'MOVE') {
      handleMoveResponse(response);
    } else {
      handleUpdateResponse(response);
    }
  };

  webSocket.connect({}, () => {
    webSocket.subscribe(`/updates/${token}`, handleResponse);
    webSocket.publish(webSocketsHelper.buildInitialRequest(token));
  });
};

const sendInput = (newInput, index) => {
  const field = internalForms.schema[index];
  const fieldType = field.type.toUpperCase();
  const stateId = internalForms.state[index].id;

  const requestType = `UPDATE_${fieldType}`;
  const payload = JSON.stringify({ id: stateId, newValue: newInput });
  const request = JSON.stringify({ requestType, payload });

  webSocket.publish({ destination: `/app/requests/${token}`, body: request });
};

const sendMove = (newStatus) => {
  const requestType = `MOVE_${newStatus}`;
  const request = JSON.stringify({ requestType });

  webSocket.publish({ destination: `/app/requests/${token}`, body: request });
};

export default {
  setToken, subscribe, sendInput, sendMove,
};
