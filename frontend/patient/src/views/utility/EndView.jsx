import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';

const EndView = ({ setForm, setCredentials }) => {
  useEffect(() => {
    setTimeout(() => {
      localStorage.removeItem('credentials');
      setForm(null);
      setCredentials(null);
    }, 2000);
  }, [setCredentials]);
  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-5 border rounded">
        <h1 className="text-center border-bottom p-1">Dziękujemy!</h1>
        <h4 className="text-center">Twoja ankieta została przyjęta.</h4>
      </div>
    </Container>
  );
};

export default EndView;
