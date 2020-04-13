import axios from 'axios';
import { API_URL } from '../../config';

const baseUrl = `${API_URL}/forms`;
let token = null;

const setToken = (newToken) => {
  token = newToken;
};

const getForms = () => axios
  .get(
    `${baseUrl}/`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const getForm = (formId) => axios
  .get(
    `${baseUrl}/${formId}`,
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const createForm = (schemaId, formName) => axios
  .post(
    `${baseUrl}/`,
    { schemaId, formName },
    { headers: { Authorization: `Bearer ${token}` } },
  )
  .then((res) => res.data);

const deleteForm = (formId) => axios
  .delete(
    `${baseUrl}/${formId}`,
    { headers: { Authorization: `Bearer ${token}` } },
  );

const createSignatureEmployee = (formId, signature) => axios
  .post(
    `${baseUrl}/${formId}/signature/employee`,
    { signature },
    { headers: { Authorization: `Bearer ${token}` } },
  );

const createSignaturePatient = (formId, signature) => axios
  .post(
    `${baseUrl}/${formId}/signature/patient`,
    { signature },
    { headers: { Authorization: `Bearer ${token}` } },
  );

export default {
  setToken,
  getForms,
  getForm,
  createForm,
  deleteForm,
  createSignatureEmployee,
  createSignaturePatient,
};
