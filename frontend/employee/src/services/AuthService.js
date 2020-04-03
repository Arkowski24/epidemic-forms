import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/login/employee`;

const login = (username, password) => axios
  .post(`${baseUrl}`, { username, password })
  .then((res) => res.data);

export default { login };
