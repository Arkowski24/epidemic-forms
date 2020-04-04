import React, { useState } from 'react';
import {
  Col, Container, Form, Row,
} from 'react-bootstrap';

import { validatePolish } from 'validate-polish';
import derivedHelper from '../../../helper/DerivedHelper';

const InputForm = ({
  title, text, setText, isInvalid,
}) => (
  <Form>
    <Form.Control
      as="input"
      size="lg"
      rows={1}
      value={text}
      placeholder={title}
      onChange={(event) => setText(event.target.value)}
      isInvalid={isInvalid}
    />
  </Form>
);

const OneField = ({
  derivedType, index,
  title,
  input, setInput,
}) => {
  const [fieldValidator, setFieldValidator] = useState('PESEL');

  if (derivedType === 'BIRTHDAY_PESEL' && index === 0) {
    const validators = ['PESEL', 'NIP', 'ID', 'REGON', 'NONE'];
    const options = ['PESEL', 'NIP', 'ID', 'REGON', 'NONE']
      .map((o, i) => (<option key={o} value={validators[i]}>{o}</option>));

    const setNewInput = (value) => {
      const newInput = input.slice();
      newInput[index] = value;
      const newValues = fieldValidator === 'PESEL' ? derivedHelper.calculateDerived(derivedType, index, newInput) : newInput;
      newValues[index] = JSON.stringify({ type: fieldValidator, value });
      setInput(newValues);
    };

    const text = input[index] ? JSON.parse(input[index]).value : input[index];
    const validateInput = () => {
      if (fieldValidator === 'PESEL') { return validatePolish.pesel(text); }
      if (fieldValidator === 'NIP') { return validatePolish.nip(text); }
      if (fieldValidator === 'ID') {
        return validatePolish.identityCard(text)
          || validatePolish.identityCardWithSeparator(text);
      }
      if (fieldValidator === 'REGON') { return validatePolish.regon(text); }
      return true;
    };
    const isValid = validateInput();

    return (
      <>
        <Col>
          <div className="w-100">
            <InputForm
              title={fieldValidator}
              text={text}
              setText={setNewInput}
              isInvalid={!isValid}
            />
          </div>
        </Col>
        <Col sm="auto">
          <div className="w-100">
            <Form>
              <Form.Group>
                <Form.Control
                  as="select"
                  size="lg"
                  onChange={(event) => setFieldValidator(event.target.value)}
                  value={fieldValidator}
                >
                  {options}
                </Form.Control>
              </Form.Group>
            </Form>
          </div>
        </Col>
      </>
    );
  }

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
