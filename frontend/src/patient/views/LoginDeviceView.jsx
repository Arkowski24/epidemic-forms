import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Form, Col, Row, Container,
} from 'react-bootstrap';
import { FaArrowLeft, FaSignOutAlt } from 'react-icons/all';

import authService from '../../common/services/AuthService';


const LoginDeviceView = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);

  const history = useHistory();

  const rawStaffCredentials = localStorage.getItem('staff-credentials');
  const staffCredentials = rawStaffCredentials ? JSON.parse(rawStaffCredentials) : null;
  const isLoggedIn = staffCredentials !== null;


  useEffect(() => {
    const fetchCredentials = () => {
      authService.meEmployee(staffCredentials.token)
        .then(() => { if (staffCredentials.employee.role !== 'DEVICE') history.push('/employee/'); })
        .catch(() => { localStorage.removeItem('staff-credentials'); });
    };

    if (isLoggedIn) { fetchCredentials(); }
  },
  [history, staffCredentials, isLoggedIn]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const { token } = await authService.loginEmployee(username, password);
      const employee = await authService.meEmployee(token);
      const credentials = { employee, token };

      localStorage.setItem('staff-credentials', JSON.stringify(credentials));
      history.push('/device');
    } catch (e) {
      setError(true);
      setTimeout(() => setError(false), 1000);
    }
  };

  const handleLogout = async (event) => {
    event.preventDefault();
    localStorage.removeItem('staff-credentials');
    history.push('/device');
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
          <Col xs="auto">
            <Button type="button" onClick={(e) => { e.preventDefault(); history.push('/'); }}><FaArrowLeft /></Button>
          </Col>
          <Col>
            <h4>{`Zalogowany jako ${staffCredentials.employee.fullName}`}</h4>
          </Col>
          <Col xs="auto">
            <Button type="button" onClick={handleLogout} variant="light">
              <FaSignOutAlt />
            </Button>
          </Col>
        </Row>
      </div>
    </Container>
  );
};

export default LoginDeviceView;
