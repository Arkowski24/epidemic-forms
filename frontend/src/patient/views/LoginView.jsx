import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Form, Col, Row, Container,
} from 'react-bootstrap';
import { LoadingView } from '../../common/views';
import { HospitalIcon } from '../../common/public';

import authService from '../../common/services/AuthService';
import formStreamService from '../../common/services/FormsStreamService';
import deviceStreamService from '../services/DeviceStreamService';


const LoginView = () => {
  const [clicks, setClicks] = useState(0);
  const [patientToken, setPatientToken] = useState('');
  const [error, setError] = useState(false);

  const history = useHistory();

  const rawStaffCredentials = localStorage.getItem('staff-credentials');
  const staffCredentials = rawStaffCredentials ? JSON.parse(rawStaffCredentials) : null;


  useEffect(() => {
    formStreamService.disconnect();
  }, []);

  useEffect(() => {
    const rawFormCredentials = localStorage.getItem('form-credentials');
    if (rawFormCredentials) { history.push('/form'); }

    if (!staffCredentials) return;
    if (staffCredentials.employee.role !== 'DEVICE') { history.push(/employee/); return; }

    authService.meDevice(staffCredentials.token)
      .catch(() => { localStorage.removeItem('staff-credentials'); history.push('/'); });
  }, [history, staffCredentials]);

  useEffect(() => {
    deviceStreamService.subscribe(history);
  }, [history]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const credentials = await authService.loginPatient(patientToken);
      localStorage.setItem('form-credentials', JSON.stringify(credentials));
      history.push('/form');
    } catch (e) {
      setError(true);
      setTimeout(() => setError(false), 1000);
    }
  };

  const header = (
    <Row>
      <Col className="m-0">
        <div
          className="w-100 m-1 p-1 border-bottom text-center"
          onClick={(e) => {
            e.preventDefault();
            if (clicks > 1) { setClicks(0); history.push('/device'); } else { setClicks(clicks + 1); }
          }}
        >
          <HospitalIcon height="40px" />
          <h4>{' Krakowski Szpital Specjalistyczny im. Jana Pawła II'}</h4>
        </div>
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
                    value={patientToken}
                    onChange={(event) => setPatientToken(event.target.value)}
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

  if (staffCredentials) {
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
