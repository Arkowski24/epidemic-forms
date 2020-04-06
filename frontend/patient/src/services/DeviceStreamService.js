import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import { WS_URL } from '../config';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;

let token = null;
let formId = null;

const setFormId = (newFormId) => {
  formId = newFormId;
};

const subscribe = (newToken, setForm, cancelForm) => {
  token = newToken;

  const handleRequest = (message) => {
    const request = JSON.parse(message.body);
    if (request.requestType === 'FORM_NEW') {
      if (formId === null) setForm(request.pinCode);
    } else if (request.requestType === 'FORM_CANCEL') {
      if (request.formId === formId) cancelForm();
    }
  };

  webSocket.connect({ Authorization: `Bearer ${token}` }, () => {
    webSocket.subscribe('/forms/', handleRequest);
  });
};

export default { setFormId, subscribe };
