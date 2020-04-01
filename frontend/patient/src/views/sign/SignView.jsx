import React, { useState } from 'react';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Row } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

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

const SubmitButton = ({ sendNewSign }) => (
  <div className="w-100 m-1 p-1 border-top">
    <Button
      className="btn float-right"
      variant="primary"
      type="submit"
      onClick={(e) => { e.preventDefault(); sendNewSign(); }}
    >
      Submit
    </Button>
  </div>
);

const SignField = ({ setInput }) => {
  const canvasRef = React.createRef();
  const handleClear = () => { canvasRef.current.clear(); setInput(null); };

  return (
    <div className="w-100 border mt-2 mb-2 rounded">
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
    </div>
  );
};

const SignView = ({
  title, description,
  sendSign,
}) => {
  const [input, setInput] = useState('');

  const sendNewSign = () => {
    const base64 = input.split(',')[1];
    console.log(base64);
    sendSign(base64);
  };

  return (
    <Container>
      <Header message={title} />
      <InfoMessage message={description} />
      <SignField setInput={setInput} />
      <SubmitButton sendNewSign={sendNewSign} />
    </Container>
  );
};

export default SignView;
