import axios from 'axios';
import { API_URL } from '../config';

const baseUrl = `${API_URL}/login/patient`;

const login = (pinCode) => axios
  .post(`${baseUrl}/`, { pinCode })
  .then((res) => res.data);

export default { login };
