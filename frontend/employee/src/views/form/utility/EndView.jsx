import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';

const EndView = ({ history }) => {
  useEffect(() => {
    setTimeout(() => {
      history.push('/employee/');
    }, 5000);
  }, [history]);

  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-5 border rounded">
        <h1 className="text-center border-bottom p-1">Dziękujemy!</h1>
        <p className="text-center">Ankieta została zapisana.</p>
      </div>
    </Container>
  );
};

export default EndView;
