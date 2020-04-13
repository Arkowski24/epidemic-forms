import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import webSocketsHelper from '../../common/helpers/WebSocketsHelper';
import { WS_URL } from '../../config';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;

let credentials = null;
let internalForms = null;
let timeouts = null;

const setCredentials = (newCredentials) => {
  credentials = newCredentials;
};

const setNewFieldState = (newInput, index, formHandler) => {
  if (internalForms === null) return;
  const oldFieldState = internalForms.state[index];
  internalForms.state[index] = { ...oldFieldState, value: newInput };

  const newForm = { ...internalForms };
  formHandler(newForm);
};

const subscribe = (formHandler) => {
  const setFormStatus = (newStatus) => {
    const newForm = { ...internalForms, status: newStatus };
    internalForms = newForm;
    formHandler(newForm);
  };

  const setFieldState = (newInput, index) => setNewFieldState(newInput, index, formHandler);

  const buildSchema = (schema) => {
    const { fields } = schema;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const derived = fields.derived.map((c) => ({ ...c, type: 'derived' }));
    const simple = fields.simple.map((s) => ({ ...s, type: 'simple' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));
    const allFields = choice.concat(derived, simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const buildState = (state, simpleFields) => {
    const fields = state;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const derived = fields.derived.map((c) => ({ ...c, type: 'derived' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));

    const simple = simpleFields.map((s) => ({ type: 'simple', fieldNumber: s.fieldNumber, value: '' }));
    const allFields = choice.concat(derived, simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const handleStateResponse = (response) => {
    const responseForm = response.payload;
    const schema = buildSchema(responseForm.schema);
    const state = buildState(responseForm.state, responseForm.schema.fields.simple);
    const { patientSignature, employeeSignature } = responseForm.schema;
    const { multiPage } = responseForm.schema;
    timeouts = state.map(() => null);

    internalForms = {
      ...responseForm, schema, state, patientSignature, employeeSignature, multiPage,
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

  webSocket.connect({ Authorization: `Bearer ${credentials.token}` }, () => {
    internalForms = null;
    webSocket.subscribe(`/updates/${credentials.formId}`, handleResponse);
    webSocket.publish(webSocketsHelper.buildInitialRequest(credentials.formId));
  });
};

const disconnect = () => {
  webSocket.disconnect();
};

const sendInput = (newInput, index, formHandler) => {
  const field = internalForms.schema[index];
  const fieldType = field.type.toUpperCase();
  const stateId = internalForms.state[index].id;

  const requestType = `UPDATE_${fieldType}`;
  const payload = JSON.stringify({ id: stateId, newValue: newInput });
  const request = JSON.stringify({ requestType, payload });

  setNewFieldState(newInput, index, formHandler);
  if (timeouts[index] !== null) { clearTimeout(timeouts[index]); }
  timeouts[index] = setTimeout(() => {
    if (webSocket.connected) { webSocket.publish({ destination: `/app/requests/${credentials.formId}`, body: request }); }
    timeouts[index] = null;
  }, 1000);
};

const sendMove = (newStatus) => {
  const requestType = `MOVE_${newStatus}`;
  const request = JSON.stringify({ requestType });

  if (webSocket.connected) { webSocket.publish({ destination: `/app/requests/${credentials.formId}`, body: request }); }
};

export default {
  setCredentials, subscribe, disconnect, sendInput, sendMove,
};
