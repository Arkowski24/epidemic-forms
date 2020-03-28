import axios from 'axios';

const baseUrl = 'http://localhost:3004/forms';

const getForm = (id) => axios
  .get(`${baseUrl}/${id}`)
  .then((res) => res.data);

export default { getForm };
