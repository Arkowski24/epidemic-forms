import axios from 'axios';

const baseUrl = 'http://localhost:8080/forms';

const getForms = () => axios
  .get(`${baseUrl}/`)
  .then((res) => res.data);

const getForm = (formId) => axios
  .get(`${baseUrl}/${formId}`)
  .then((res) => res.data);

const createForm = (schemaId, patientName) => axios
  .post(`${baseUrl}/`, { schemaId, patientName })
  .then((res) => res.data);

export default { getForms, getForm, createForm };
