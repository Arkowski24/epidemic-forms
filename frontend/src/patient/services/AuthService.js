import axios from 'axios';
import { API_URL } from '../../config';

const baseUrl = `${API_URL}/auth`;

const login = (pinCode) => axios
  .post(`${baseUrl}/login/patient`, { pinCode })
  .then((res) => res.data);

const me = (token) => axios
  .get(
    `${baseUrl}/me/patient`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const loginDevice = (username, password) => axios
  .post(`${baseUrl}/login/employee`, { username, password })
  .then((res) => res.data);

const meDevice = (token) => axios
  .get(
    `${baseUrl}/me/employee`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

export default {
  login, me, loginDevice, meDevice,
};