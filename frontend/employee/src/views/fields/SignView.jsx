import React, { useEffect } from 'react';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Row } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import SingleInput from './common/SingleInput';

const SignField = ({ input, setInput }) => {
  const canvasRef = React.createRef();
  const handleClear = () => { canvasRef.current.clear(); setInput(null); };

  useEffect(() => {
    if (canvasRef.current && canvasRef.current.isEmpty()) canvasRef.current.fromDataURL(input);
  }, [input, canvasRef]);

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
            onEnd={() => { setInput(canvasRef.current.toDataURL()); }}
          />
        </div>
      </Row>
    </Container>
  );
};

const SignView = ({
  message,
  input, setInput,
}) => (
  <SingleInput
    header="Sign Form"
    message={message}
  >
    <SignField input={input} setInput={setInput} />
  </SingleInput>
);

export default SignView;
