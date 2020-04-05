import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';

const EndView = ({ setForm, setCredentials, setCurrentPage }) => {
  useEffect(() => {
    setTimeout(() => {
      localStorage.removeItem('credentials');
      setForm(null);
      setCurrentPage(1);
      setCredentials(null);
    }, 2000);
  }, [setCredentials]);
  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-5 border rounded">
        <h1 className="text-center border-bottom p-1">Dziękujemy!</h1>
        <p className="text-center">Twoja ankieta została przyjęta.</p>
      </div>
    </Container>
  );
};

export default EndView;
