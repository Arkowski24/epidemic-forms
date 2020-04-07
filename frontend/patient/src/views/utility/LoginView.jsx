import React, { useEffect, useState } from 'react';
import { Button, Form, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import { useHistory } from 'react-router-dom';
import authService from '../../services/AuthService';
import deviceStreamService from '../../services/DeviceStreamService';
import formStreamService from '../../services/FormsStreamService';
import LoadingView from './LoadingView';

const LoginView = () => {
  const [text, setText] = useState('');
  const [error, setError] = useState(false);
  const history = useHistory();
  const isContinuous = localStorage.getItem('device-token') !== null;

  useEffect(() => {
    formStreamService.disconnect();
  }, []);

  useEffect(() => {
    const rawCredentials = localStorage.getItem('credentials');
    if (!rawCredentials) return;
    const credentials = JSON.parse(rawCredentials);

    authService.me(credentials.token)
      .then(() => history.push('/form'))
      .catch(() => localStorage.removeItem('credentials'));
  }, [history]);

  useEffect(() => {
    const token = localStorage.getItem('device-token');
    if (!token) return;

    const setForm = (pinCode) => {
      authService.login(pinCode)
        .then((c) => localStorage.setItem('credentials', JSON.stringify(c)))
        .then(() => history.push('/form'));
    };
    const cancelForm = () => {
      localStorage.removeItem('credentials');
      history.push('/');
      window.location.reload();
    };
    deviceStreamService.subscribe(token, setForm, cancelForm);
  }, [isContinuous, history]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const credentials = await authService.login(text);
      localStorage.setItem('credentials', JSON.stringify(credentials));
      history.push('/form');
    } catch (e) {
      setError(true);
      setTimeout(() => setError(false), 1000);
    }
  };

  if (isContinuous) { return (<LoadingView message="Oczekiwanie na rozpoczęcie." />); }
  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-2 border rounded">
        <Form onSubmit={handleLogin}>
          <Form.Group>
            <Form.Row>
              <Form.Label column>Kod jednorazowy</Form.Label>
              <Col sm={10}>
                <Form.Control
                  type="text"
                  value={text}
                  onChange={(event) => setText(event.target.value)}
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
