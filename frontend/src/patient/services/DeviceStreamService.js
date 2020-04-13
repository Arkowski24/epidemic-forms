import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import { WS_URL } from '../../config';
import authService from './AuthService';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;

let token = null;

const subscribe = async (history) => {
  if (webSocket.connected) return;

  token = localStorage.getItem('device-token');
  if (!token) { return; }
  try { await authService.meDevice(token); } catch (e) { localStorage.removeItem('device-token'); history.push('/'); return; }

  const setForm = (pinCode) => {
    authService.login(pinCode)
      .then((c) => localStorage.setItem('credentials', JSON.stringify(c)))
      .then(() => history.push('/form'));
  };
  const cancelForm = () => {
    localStorage.removeItem('credentials');
    history.push('/');
  };

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
