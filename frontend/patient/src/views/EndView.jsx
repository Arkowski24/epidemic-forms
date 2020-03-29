import React from 'react';
import { Container } from 'react-bootstrap';

const EndView = () => (
  <Container className="d-flex justify-content-center align-items-center">
    <div className="w-50 m-5 p-5 border rounded">
      <h1 className="text-center border-bottom p-1">Thank you for your time!</h1>
      <p className="text-center">We will forward your form to designated person.</p>
    </div>
  </Container>
);

export default EndView;
