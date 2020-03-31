import axios from 'axios';

const baseUrl = 'http://localhost:8080/schemas';

const getSchemas = () => axios
  .get(`${baseUrl}/`)
  .then((res) => res.data);

const getSchema = (id) => axios
  .get(`${baseUrl}/${id}`)
  .then((res) => res.data);

export default { getSchemas, getSchema };
