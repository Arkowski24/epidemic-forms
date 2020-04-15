import axios from 'axios';
import { API_URL } from '../../config';

const baseUrl = `${API_URL}/employees`;
let token = null;

const setToken = (newToken) => {
  token = newToken;
};

const getEmployees = () => axios
  .get(
    `${baseUrl}/`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const getEmployee = (employeeId) => axios
  .get(
    `${baseUrl}/${employeeId}`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const getDevices = () => axios
  .get(
    `${baseUrl}/devices`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const createEmployee = (username, fullName, password, role) => axios
  .post(
    `${baseUrl}/`,
    {
      username, fullName, password, role,
    },
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const modifyEmployee = (employeeId, username, fullName, password, role) => axios
  .put(
    `${baseUrl}/${employeeId}`,
    {
      username, fullName, password, role,
    },
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const deleteEmployee = (employeeId) => axios
  .delete(
    `${baseUrl}/${employeeId}`,
    { headers: { Authorization: `Bearer ${token}` } },
  );

export default {
  setToken, getEmployees, getEmployee, getDevices, createEmployee, modifyEmployee, deleteEmployee,
};
