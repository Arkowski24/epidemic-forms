import React, { useState } from 'react';
import {
  Button, Col, Container, Form, Row,
} from 'react-bootstrap';

const commonStyle = { padding: '5px' };

const Header = () => (
  <div style={commonStyle}>
    <h1>Fill-in the blank</h1>
  </div>
);

const InfoMessage = ({ message }) => (
  <div style={commonStyle}>
    {message}
  </div>
);

const InputForm = ({ text, setText }) => (
  <div style={commonStyle}>
    <Form>
      <Form.Control
        as="textarea"
        rows="3"
        value={text}
        onChange={(event) => setText(event.target.value)}
      />
    </Form>
  </div>
);

const NavigationButtons = () => (
  <div style={commonStyle}>
    <Row>
      <Col>
        <Button variant="light" type="button">
          Previous
        </Button>
      </Col>
      <Col>
        <Button variant="primary" type="submit">
          Next
        </Button>
      </Col>
    </Row>
  </div>
);

const TextView = ({ message }) => {
  const [text, setText] = useState('');

  return (
    <Container style={{ padding: '30px' }}>
      <Header />
      <InfoMessage message={message} />
      <InputForm text={text} setText={setText} />
      <NavigationButtons />
    </Container>
  );
};

export default TextView;
