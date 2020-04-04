import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/schemas`;
let token = null;

const setToken = (newToken) => {
  token = newToken;
};

const getSchemas = () => axios
  .get(`${baseUrl}/`, { headers: { Authorization: `Bearer ${token}` } })
  .then((res) => res.data);

const getSchema = (id) => axios
  .get(`${baseUrl}/${id}`, { headers: { Authorization: `Bearer ${token}` } })
  .then((res) => res.data);

export default { setToken, getSchemas, getSchema };
