import { Container, ProgressBar } from 'react-bootstrap';
import React from 'react';
import NavigationButtons from './PrevNextButtons';

const commonStyle = { padding: '5px' };

const Header = ({ message }) => (
  <div style={commonStyle}>
    <h1>{message}</h1>
  </div>
);

const InfoMessage = ({ message }) => (
  <div style={{ ...commonStyle, overflow: 'auto', maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const SinglePageForm = ({
  currentPage, totalPages,
  header, message,
  children,
}) => {
  const progress = Math.round((currentPage / totalPages) * 100);
  const progressLabel = (
    <div style={{
      position: 'absolute', textAlign: 'center', color: 'black', left: 0, right: 0,
    }}
    >
      {`${currentPage}/${totalPages}`}
    </div>
  );

  return (
    <Container style={{ padding: '30px' }}>
      <ProgressBar now={progress} label={progressLabel} />
      <Header message={header} />
      <InfoMessage message={message} />
      {children}
      <NavigationButtons style={commonStyle} />
    </Container>
  );
};

export default SinglePageForm;
