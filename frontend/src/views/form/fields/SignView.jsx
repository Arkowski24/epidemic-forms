import React from 'react';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Row } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import SinglePageForm from './common/SinglePageForm';

const SignField = () => {
  const canvasRef = React.createRef();
  const handleClear = () => canvasRef.current.clear();

  return (
    <Container className="w-100 border mt-2 mb-2 rounded">
      <Row>
        <div className="w-100 border-bottom rounded">
          <Button className="btn float-right" variant="dark" onClick={handleClear}>
            Clear
          </Button>
        </div>
      </Row>
      <Row>
        <div className="w-100">
          <SignatureCanvas
            penColor="black"
            canvasProps={{ className: 'w-100 h-100 m-0' }}
            ref={canvasRef}
          />
        </div>
      </Row>
    </Container>
  );
};

const SignView = ({
  currentPage, totalPages,
  message,
  onClickPrev, onClickNext,
}) => (
  <SinglePageForm
    header="Sign Form"
    currentPage={currentPage}
    totalPages={totalPages}
    message={message}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  >
    <SignField />
  </SinglePageForm>
);

export default SignView;
