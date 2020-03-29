import axios from 'axios';

const baseUrl = 'http://localhost:3004';

const getForm = (id) => axios
  .get(`${baseUrl}/forms/${id}`)
  .then((res) => res.data);

const postResponse = (response) => axios
  .post(`${baseUrl}/responses/`, response)
  .then((res) => res.data);

export default { getForm, postResponse };
