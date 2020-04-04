import React, { useEffect, useState } from 'react';
import { Button, Form, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import authService from '../../services/AuthService';

const LoginView = ({ setCredentials }) => {
  const [text, setText] = useState('');
  const [error, setError] = useState(false);

  useEffect(() => {
    const rawCredentials = localStorage.getItem('credentials');
    if (!rawCredentials) return;
    const credentials = JSON.parse(rawCredentials);

    authService.me(credentials.token)
      .then(() => setCredentials(credentials))
      .catch(() => localStorage.removeItem('credentials'));
  }, [setCredentials]);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const credentials = await authService.login(text);
      localStorage.setItem('credentials', JSON.stringify(credentials));
      setCredentials(credentials);
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
              <Form.Label column>Token</Form.Label>
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
            Submit
          </Button>
        </Form>
      </div>
    </Container>
  );
};

export default LoginView;
