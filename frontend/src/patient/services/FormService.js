import axios from 'axios';
import { API_URL } from '../../config';

const baseUrl = `${API_URL}/forms`;
let credentials = null;

const setCredentials = (newCredentials) => {
  credentials = newCredentials;
};

const createSignature = (formId, signature) => axios
  .post(
    `${baseUrl}/${formId}/signature/patient`,
    { signature },
    { headers: { Authorization: `Bearer ${credentials.token}` } },
  );

export default {
  setCredentials, createSignature,
};
