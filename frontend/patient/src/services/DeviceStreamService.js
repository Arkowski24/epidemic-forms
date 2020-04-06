import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import { WS_URL } from '../config';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;

let token = null;

const subscribe = (newToken, setForm, cancelForm) => {
  token = newToken;

  const handleRequest = (message) => {
    const request = JSON.parse(message.body);
    const rawCredentials = localStorage.getItem('credentials');
    if (request.requestType === 'FORM_NEW') {
      if (rawCredentials) return;
      setForm(request.pinCode);
    } else if (request.requestType === 'FORM_CANCEL') {
      if (!rawCredentials) return;
      const credentials = JSON.parse(rawCredentials);
      if (request.formId === credentials.formId) cancelForm();
    }
  };

  webSocket.connect({ Authorization: `Bearer ${token}` }, () => {
    webSocket.subscribe('/forms/', handleRequest);
  });
};

export default { subscribe };
