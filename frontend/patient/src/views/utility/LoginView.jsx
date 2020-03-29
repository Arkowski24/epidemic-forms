import React, { useState } from 'react';
import { Button, Form, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

const LoginView = ({ setToken }) => {
  const [text, setText] = useState('');

  const handleLogin = (event) => {
    event.preventDefault();
    setToken(text);
  };

  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-5 border rounded">
        <Form onSubmit={handleLogin}>
          <Form.Group>
            <Form.Row>
              <Form.Label column>Token</Form.Label>
              <Col sm={10}>
                <Form.Control type="text" value={text} onChange={(event) => setText(event.target.value)} />
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
