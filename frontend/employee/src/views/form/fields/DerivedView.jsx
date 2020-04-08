import React from 'react';
import { Container, Form, Row } from 'react-bootstrap';

import derivedHelper from '../../../helper/DerivedHelper';
import DerivedViewInline from './inline/DerivedViewInline';

const Header = ({ message }) => (
  <div className="m-2 p-1 border-bottom">
    <h1>{message}</h1>
  </div>
);

const InfoMessage = ({ message }) => (
  <div className="m-2 p-2 overflow-auto" style={{ maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const InputForm = ({ text, setText, isBlocked }) => (
  <div className="m-2 p-1">
    <Form>
      <Form.Control
        as="input"
        rows={1}
        value={text}
        onChange={(event) => setText(event.target.value)}
        disabled={isBlocked}
      />
    </Form>
  </div>
);

const OneField = ({
  derivedType, index,
  title, description,
  input, setInput,
  isBlocked,
}) => {
  const setNewInput = (value) => {
    const newInput = input.slice();
    newInput[index] = value;
    const newValues = derivedHelper.calculateDerived(derivedType, index, newInput);
    newValues[index] = JSON.stringify({ type: 'PESEL', value });
    setInput(newValues);
  };
  const text = input[index] ? JSON.parse(input[index]).value : input[index];

  return (
    <div className="w-100 m-1 p-1">
      <Header message={title} />
      <InfoMessage message={description} />
      <InputForm text={text} setText={setNewInput} isBlocked={isBlocked} />
    </div>
  );
};

const DerivedView = ({
  derivedType,
  titles, descriptions,
  isInline,
  input, setInput,
  highlighted,
  isBlocked,
}) => {
  if (isInline) {
    return (
      <DerivedViewInline
        derivedType={derivedType}
        titles={titles}
        input={input}
        setInput={setInput}
        highlighted={highlighted}
        isBlocked={isBlocked}
      />
    );
  }

  const fields = titles.map((t, i) => (
    <Row key={i}>
      <OneField
        key={i}
        derivedType={derivedType}
        index={i}
        title={t}
        description={descriptions[i]}
        input={input}
        setInput={setInput}
        isBlocked={isBlocked}
      />
    </Row>
  ));

  return (
    <Container className={`w-100 m-1 p-1 rounded border ${highlighted ? 'border-secondary' : ''}`}>
      {fields}
    </Container>
  );
};
export default DerivedView;
