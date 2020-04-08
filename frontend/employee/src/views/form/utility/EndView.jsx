import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';
import formStreamService from '../../../services/FormsStreamService';

const EndView = () => {
  const history = useHistory();
  useEffect(() => {
    formStreamService.disconnect();
    setTimeout(() => { history.push('/employee/'); }, 2000);
  }, [history]);

  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-5 border rounded">
        <h1 className="text-center border-bottom p-1">Dziękujemy!</h1>
        <h4 className="text-center">Ankieta została zapisana.</h4>
      </div>
    </Container>
  );
};

export default EndView;
