import { Container, ProgressBar } from 'react-bootstrap';
import React from 'react';
import PrevNextButtons from './PrevNextButtons';

const Header = ({ message }) => (
  <div className="m-2 border-bottom">
    <h1>{message}</h1>
  </div>
);

const InfoMessage = ({ message }) => (
  <div className="p-2 overflow-auto" style={{ maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const SinglePageForm = ({
  currentPage, totalPages,
  header, message,
  onClickPrev, onClickNext,
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
      <PrevNextButtons
        onClickPrev={onClickPrev}
        onClickNext={onClickNext}
        isFirst={currentPage === 1}
        isFinal={currentPage === totalPages}
      />
    </Container>
  );
};

export default SinglePageForm;
