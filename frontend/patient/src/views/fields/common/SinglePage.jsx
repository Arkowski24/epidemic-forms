import { Container, ProgressBar } from 'react-bootstrap';
import React from 'react';
import PrevNextButtons from './PrevNextButtons';

const SinglePage = ({
  currentPage, totalPages,
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

export default SinglePage;
