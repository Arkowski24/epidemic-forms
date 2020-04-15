import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Form, Col, Container,
} from 'react-bootstrap';

import authService from '../../common/services/AuthService';


const LoginView = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);

  const history = useHistory();


  useEffect(() => {
    const fetchCredentials = () => {
      const rawCredentials = localStorage.getItem('staff-credentials');
      if (!rawCredentials) return;

      const credentials = JSON.parse(rawCredentials);
      authService.meEmployee(credentials.token)
        .then(() => (credentials.employee.role === 'DEVICE' ? history.push('/') : history.push('/employee/')))
        .catch(() => { localStorage.removeItem('staff-credentials'); });
    };

    fetchCredentials();
  },
  [history]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const { token } = await authService.loginEmployee(username, password);
      const employee = await authService.meEmployee(token);
      const credentials = { employee, token };

      localStorage.setItem('staff-credentials', JSON.stringify(credentials));
      history.push('/employee/');
    } catch (e) {
      setError(true);
      setTimeout(() => setError(false), 1000);
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-2 border rounded">
        <Form onSubmit={handleLogin}>
          <Form.Group>
            <Form.Row>
              <Form.Label column>Login</Form.Label>
              <Col sm={10}>
                <Form.Control
                  type="text"
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                />
              </Col>
            </Form.Row>
          </Form.Group>
          <Form.Group>
            <Form.Row>
              <Form.Label column>Hasło</Form.Label>
              <Col sm={10}>
                <Form.Control
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  isInvalid={error}
                />
              </Col>
            </Form.Row>
          </Form.Group>
          <Button className="btn float-right" variant="primary" type="submit">
            Prześlij
          </Button>
        </Form>
      </div>
    </Container>
  );
};

export default LoginView;
