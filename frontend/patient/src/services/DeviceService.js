import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/devices`;
let token = null;

const setToken = (newToken) => {
  token = newToken;
};

const getDevices = () => axios
  .get(
    `${baseUrl}/`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const createDevice = (schemaId, formName) => axios
  .post(
    `${baseUrl}/`,
    { schemaId, formName },
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const deleteDevice = (formId) => axios
  .delete(
    `${baseUrl}/${formId}`,
    { headers: { Authorization: `Bearer ${token}` } },
  );

export default {
  setToken, getDevices, createDevice, deleteDevice,
};
