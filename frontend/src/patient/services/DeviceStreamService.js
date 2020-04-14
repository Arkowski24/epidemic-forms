import { Stomp } from '@stomp/stompjs/esm6/compatibility/stomp';
import { WS_URL } from '../../config';
import authService from '../../common/services/AuthService';

const url = `${WS_URL}/requests`;

const webSocket = new Stomp.client(url);
webSocket.debug = () => {};
webSocket.reconnect_delay = 1000;


const subscribe = async (history) => {
  if (webSocket.connected) return;

  const rawStaffCredentials = localStorage.getItem('staff-credentials');
  const staffCredentials = rawStaffCredentials ? JSON.parse(rawStaffCredentials) : null;
  if (!staffCredentials || staffCredentials.employee.role !== 'DEVICE') { return; }

  const setForm = (pinCode) => {
    authService.loginPatient(pinCode)
      .then((c) => localStorage.setItem('form-credentials', JSON.stringify(c)))
      .then(() => history.push('/form'));
  };
  const cancelForm = () => {
    localStorage.removeItem('form-credentials');
    history.push('/');
  };
  const handleRequest = (message) => {
    const request = JSON.parse(message.body);
    const rawCredentials = localStorage.getItem('form-credentials');

    if (request.requestType === 'FORM_NEW') {
      if (rawCredentials) return;

      const deviceId = staffCredentials.employee.id;
      if (request.deviceId === deviceId) { setForm(request.pinCode); }
    } else if (request.requestType === 'FORM_CANCEL') {
      if (!rawCredentials) return;

      const credentials = JSON.parse(rawCredentials);
      if (request.formId === credentials.formId) { cancelForm(); }
    }
  };

  try {
    await authService.meDevice(staffCredentials.token);
    webSocket.connect({ Authorization: `Bearer ${staffCredentials.token}` }, () => {
      webSocket.subscribe('/forms/', handleRequest);
    });
  } catch (e) {
    localStorage.removeItem('staff-credentials');
    history.push('/device/login');
  }
};

export default { subscribe };
