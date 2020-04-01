import React from 'react';
import { Container, Spinner } from 'react-bootstrap';

const LoadingView = ({ message = 'Your form will load shortly.' }) => (
  <Container className="d-flex justify-content-center align-items-center">
    <div className="w-50 m-5 p-2 border rounded">
      <div className="m-1 p-1 border-bottom d-flex justify-content-center align-items-center">
        <Spinner animation="border" />
      </div>
      <p className="text-center">{message}</p>
    </div>
  </Container>
);

export default LoadingView;
