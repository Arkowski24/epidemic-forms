import axios from 'axios';

const baseUrl = 'http://localhost:3004';

const getPatients = () => axios
  .get(`${baseUrl}/patients/`)
  .then((res) => res.data);

const createPatient = (name, schemaId) => axios
  .post(`${baseUrl}/patients/`, { name, schemaId })
  .then((res) => res.data);

const postResponse = (response) => axios
  .post(`${baseUrl}/responses/`, response)
  .then((res) => res.data);

export default { getPatients, createPatient, postResponse };
