import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import webSocketsHelper from '../helper/WebSocketsHelper';

const url = 'ws://localhost:8080/requests';
const webSocket = new Stomp.client(url);

let token = null;
let internalForms = null;

const setToken = (newToken) => {
  token = newToken;
};

const subscribe = (formHandler) => {
  const setFieldState = (newInput, index) => {
    const oldFieldState = internalForms.state[index];
    internalForms.state[index] = { ...oldFieldState, value: newInput };

    const newForm = { ...internalForms };
    formHandler(newForm);
  };

  const buildSchema = (schema) => {
    const { fields } = schema;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const sign = fields.sign.map((s) => ({ ...s, type: 'sign' }));
    const simple = fields.simple.map((s) => ({ ...s, type: 'simple' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));
    const allFields = choice.concat(sign, simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const buildState = (state, simpleFields) => {
    const fields = state;

    const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
    const sign = fields.sign.map((s) => ({ ...s, type: 'sign' }));
    const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
    const text = fields.text.map((t) => ({ ...t, type: 'text' }));

    const simple = simpleFields.map((s) => ({ type: 'simple', fieldNumber: s.fieldNumber, value: '' }));
    const allFields = choice.concat(sign, simple, slider, text);
    allFields.sort((a, b) => a.fieldNumber - b.fieldNumber);

    return allFields;
  };

  const handleResponse = (message) => {
    const response = JSON.parse(message.body);

    if (response.responseType === 'STATE') {
      const responseForm = response.payload;
      const schema = buildSchema(responseForm.schema);
      const state = buildState(responseForm.state, responseForm.schema.fields.simple);

      internalForms = { schema, state };
      formHandler(internalForms);
    } else {
      setFieldState(response.payload.value, response.payload.fieldNumber);
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

export default { setToken, subscribe, sendInput };
