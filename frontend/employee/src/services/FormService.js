import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/forms`;

const getForms = () => axios
  .get(`${baseUrl}/`)
  .then((res) => res.data);

const getForm = (formId) => axios
  .get(`${baseUrl}/${formId}`)
  .then((res) => res.data);

const createForm = (schemaId, formName) => axios
  .post(`${baseUrl}/`, { schemaId, formName })
  .then((res) => res.data);

export default { getForms, getForm, createForm };
