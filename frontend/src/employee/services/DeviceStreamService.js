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

  if (!staffCredentials || staffCredentials.employee.role === 'DEVICE') { return; }
  try {
    await authService.meEmployee(staffCredentials.token);
    webSocket.connect({ Authorization: `Bearer ${staffCredentials.token}` }, () => { });
  } catch (e) {
    localStorage.removeItem('staff-credentials');
    history.push('/employee/login');
  }
};

const sendNewForm = (deviceId, pinCode) => {
  const requestType = 'FORM_NEW';
  const request = JSON.stringify({ requestType, deviceId, pinCode });

  if (webSocket.connected) webSocket.publish({ destination: '/forms/', body: request });
};

const sendCancelForm = async (formId) => {
  const requestType = 'FORM_CANCEL';
  const request = JSON.stringify({ requestType, formId });
  if (webSocket.connected) { webSocket.publish({ destination: '/forms/', body: request }); }
};

export default {
  subscribe, sendNewForm, sendCancelForm,
};
