import React from 'react';

import { Container, Spinner } from 'react-bootstrap';
import ClickNHold from 'react-click-n-hold';


const LoadingView = ({
  message = 'Formularz zaraz się załaduje.',
  redirectHandler = () => {},
}) => (
  <Container className="d-flex justify-content-center align-items-center">
    <div className="w-50 m-5 p-2 border rounded">
      <ClickNHold
        time={2}
        onClickNHold={() => redirectHandler()}
      >
        <div className="m-1 p-1 border-bottom d-flex justify-content-center align-items-center">
          <Spinner animation="border" />
        </div>
      </ClickNHold>
      <h4 className="text-center">{message}</h4>
    </div>
  </Container>
);

export default LoadingView;
