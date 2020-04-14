import React, { useState } from 'react';

import { Container, Spinner } from 'react-bootstrap';


const LoadingView = ({
  message = 'Formularz zaraz się załaduje.',
  redirectHandler = () => {},
}) => {
  const [clicks, setClicks] = useState(0);

  return (
    <Container className="d-flex justify-content-center align-items-center">
      <div className="w-50 m-5 p-2 border rounded">
        <div
          className="m-1 p-1 border-bottom d-flex justify-content-center align-items-center"
          onClick={(e) => {
            e.preventDefault();
            if (clicks > 1) { setClicks(0); redirectHandler(); } else { setClicks(clicks + 1); }
          }}
        >
          <Spinner animation="border" />
        </div>
        <h4 className="text-center">{message}</h4>
      </div>
    </Container>
  );
};

export default LoadingView;
