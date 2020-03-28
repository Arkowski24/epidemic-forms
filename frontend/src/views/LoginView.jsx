import React, { useState } from 'react';
import { Button, Form, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

const LoginView = () => {
  const [token, setToken] = useState('');

  const handleLogin = (event) => {
    event.preventDefault();
    console.log(token);
  };

  return (
    <Container style={{ padding: '30px' }}>
      <Form onSubmit={handleLogin}>
        <Form.Group>
          <Form.Row>
            <Form.Label column>Token</Form.Label>
            <Col sm={10}>
              <Form.Control type="text" value={token} onChange={(event) => setToken(event.target.value)} />
            </Col>
          </Form.Row>
        </Form.Group>
        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </Container>
  );
};

export default LoginView;
