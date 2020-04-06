import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import { WS_URL } from '../config';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;

let token = null;

const setToken = (newToken) => {
  token = newToken;
  webSocket.connect({ Authorization: `Bearer ${token}` }, () => {});
};

const sendNewForm = (deviceId, pinCode) => {
  const requestType = 'FORM_NEW';
  const request = JSON.stringify({ requestType, deviceId, pinCode });

  webSocket.publish({ destination: '/forms/', body: request });
};

const sendCancelForm = (formId) => {
  const requestType = 'FORM_CANCEL';
  const request = JSON.stringify({ requestType, formId });

  webSocket.publish({ destination: '/forms/', body: request });
};

export default {
  setToken, sendNewForm, sendCancelForm,
};
