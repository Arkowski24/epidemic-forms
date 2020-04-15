import React, { useState } from 'react';

import { Button, Row, Container } from 'react-bootstrap';
import SignatureCanvas from 'react-signature-canvas';


const Header = ({ message }) => (
  <Row>
    <div className="w-100 p-1 mr-2 border-bottom">
      <h4>{message}</h4>
    </div>
  </Row>
);

const InfoMessage = ({ message }) => (
  <Row>
    <div className="w-100 p-1 ml-2 mr-2 overflow-auto" style={{ maxHeight: '30vh' }}>
      <h5 className="text-justify">{message}</h5>
    </div>
  </Row>
);

const SubmitButton = ({ sendNewSignature, isBlocked }) => (
  <Row>
    <div className="w-100 m-1 p-1 border-top">
      <Button
        variant="success"
        type="submit"
        size="lg"
        block
        onClick={(e) => { e.preventDefault(); sendNewSignature(); }}
        disabled={isBlocked}
      >
        Prześlij
      </Button>
    </div>
  </Row>
);

const SignField = ({ setInput, setIsEmpty }) => {
  const canvasRef = React.createRef();
  const handleClear = () => { canvasRef.current.clear(); setInput(null); setIsEmpty(true); };

  return (
    <Row>
      <Container className="w-100 border mt-2 mb-2 rounded">
        <Row>
          <div className="w-100 border-bottom rounded">
            <Button className="btn float-right" variant="dark" onClick={handleClear}>
              Wyczyść
            </Button>
          </div>
        </Row>
        <Row>
          <div className="w-100 border rounded border-warning" style={{ backgroundColor: '#FFF7D4' }}>
            <SignatureCanvas
              penColor="black"
              canvasProps={{ className: 'w-100 h-100 m-0' }}
              ref={canvasRef}
              onEnd={() => {
                const { current } = canvasRef;
                setIsEmpty(current.isEmpty());
                setInput(current.toDataURL());
              }}
            />
          </div>
        </Row>
      </Container>
    </Row>
  );
};

const SignatureView = ({
  title, description,
  sendSignature,
}) => {
  const [input, setInput] = useState('');
  const [isEmpty, setIsEmpty] = useState(true);

  const sendNewSignature = () => {
    const base64 = input.split(',')[1];
    sendSignature(base64);
  };

  return (
    <Container>
      <Header message={title} />
      <InfoMessage message={description} />
      <SignField setInput={setInput} setIsEmpty={setIsEmpty} />
      <SubmitButton sendNewSignature={sendNewSignature} isBlocked={isEmpty} />
    </Container>
  );
};

export default SignatureView;
