import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/schemas`;

const getSchemas = () => axios
  .get(`${baseUrl}/`)
  .then((res) => res.data);

const getSchema = (id) => axios
  .get(`${baseUrl}/${id}`)
  .then((res) => res.data);

export default { getSchemas, getSchema };
