import React, { useEffect, useState } from 'react';
import {
  Button, Form, Col, Row,
} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import { useHistory } from 'react-router-dom';

import authService from '../../services/AuthService';

const LoginDeviceView = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);
  const [user, setUser] = useState('');

  const history = useHistory();
  const isLoggedIn = localStorage.getItem('device-token') !== null;

  useEffect(() => {
    const fetchInfo = async () => {
      try {
        const token = localStorage.getItem('device-token');
        const response = await authService.meDevice(token);
        setUser(response.fullName);
      } catch (e) {
        localStorage.removeItem('device-token');
      }
    };

    if (isLoggedIn) {
      fetchInfo();
    }
  }, [isLoggedIn, history]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const credentials = await authService.loginDevice(username, password);
      localStorage.setItem('device-token', credentials.token);
      history.push('/');
    } catch (e) {
      setError(true);
      setTimeout(() => setError(false), 1000);
    }
  };

  const handleLogout = async (event) => {
    event.preventDefault();
    localStorage.removeItem('device-token');
    history.push('/');
  };

  if (!isLoggedIn) {
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
  }

  return (
    <Container>
      <div className="m-5 p-2 border rounded">
        <Row>
          <Col>
            <h4>{`Zalogowany jako ${user}`}</h4>
          </Col>
          <Col xs="auto">
            <Button type="button" onClick={handleLogout}>
              Wyloguj się
            </Button>
          </Col>
        </Row>
      </div>
    </Container>
  );
};

export default LoginDeviceView;
