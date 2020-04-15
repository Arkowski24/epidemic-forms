import axios from 'axios';
import { API_URL } from '../../config';

const baseUrl = `${API_URL}/auth`;

const loginPatient = (pinCode) => axios
  .post(
    `${baseUrl}/login/patient`,
    { pinCode },
  )
  .then((res) => res.data);

const mePatient = (token) => axios
  .get(
    `${baseUrl}/me/patient`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const loginEmployee = (username, password) => axios
  .post(
    `${baseUrl}/login/employee`,
    { username, password },
  )
  .then((res) => res.data);

const meEmployee = (token) => axios
  .get(
    `${baseUrl}/me/employee`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const loginDevice = (username, password) => loginEmployee(username, password);

const meDevice = (token) => meEmployee(token);

export default {
  loginPatient,
  loginEmployee,
  loginDevice,
  mePatient,
  meEmployee,
  meDevice,
};
