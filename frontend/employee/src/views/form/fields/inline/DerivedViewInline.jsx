import React from 'react';
import { Container, Form, Row } from 'react-bootstrap';
import derivedHelper from '../../../../helper/DerivedHelper';

const InputForm = ({ title, text, setText }) => (
  <Form>
    <Form.Control
      as="textarea"
      size="lg"
      rows={1}
      value={text}
      placeholder={title}
      onChange={(event) => setText(event.target.value)}
    />
  </Form>
);

const OneField = ({
  derivedType, index,
  title,
  input, setInput,
}) => {
  const setNewInput = (value) => {
    const newInput = input.slice();
    newInput[index] = value;
    const newValues = derivedHelper.calculateDerived(derivedType, index, newInput);
    setInput(newValues);
  };

  return (
    <div className="w-100 ml-2 mr-2 pl-2 pr-2 pb-1">
      <InputForm title={title} text={input[index]} setText={setNewInput} />
    </div>
  );
};

const DerivedViewInline = ({
  derivedType,
  titles,
  input, setInput,
  highlighted,
}) => {
  const fields = titles.map((t, i) => (
    <Row key={i}>
      <OneField
        key={i}
        derivedType={derivedType}
        index={i}
        title={t}
        input={input}
        setInput={setInput}
      />
    </Row>
  ));

  return (
    <Container className={`w-100 ml-1 mt-1 p-1 rounded border ${highlighted ? 'border-primary shadow-sm' : ''}`}>
      {fields}
    </Container>
  );
};
export default DerivedViewInline;
