import React, { useEffect, useState } from 'react';
import {
  Button, Form, Col, Image, Row,
} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import { useHistory } from 'react-router-dom';
import ClickNHold from 'react-click-n-hold';
import authService from '../../services/AuthService';
import deviceStreamService from '../../services/DeviceStreamService';
import formStreamService from '../../services/FormsStreamService';
import LoadingView from './LoadingView';

import hospitalLogo from '../../public/hospital_logo.png';

const HospitalIcon = () => (
  <Image src={hospitalLogo} style={{ height: '40px' }} alt="Logo of the hospital" fluid />
);


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
    deviceStreamService.subscribe(history);
  }, [history]);

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

  const header = (
    <Row>
      <Col className="m-0">
        <ClickNHold
          time={2}
          onClickNHold={() => history.push('/device')}
        >
          <div className="w-100 m-1 p-1 border-bottom text-center">
            <HospitalIcon />
            <h4>{' Krakowski Szpital Specjalistyczny im. Jana Pawła II'}</h4>
          </div>
        </ClickNHold>
      </Col>
    </Row>
  );

  const loginPanel = (
    <Row xs="auto">
      <div className="w-100 p-2 mt-2 border rounded">
        <Col>
          <Form onSubmit={handleLogin}>
            <Form.Group>
              <Form.Row>
                <Col xs="auto">
                  <Form.Label column>Kod jednorazowy:</Form.Label>
                </Col>
                <Col>
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
        </Col>
      </div>
    </Row>
  );

  if (isContinuous) {
    return (
      <LoadingView message="Oczekiwanie na rozpoczęcie." redirectHandler={() => history.push('/device')} />
    );
  }
  return (
    <Container className="d-flex justify-content-center align-items-center">
      <Col>
        {header}
        {loginPanel}
      </Col>
    </Container>
  );
};

export default LoginView;
