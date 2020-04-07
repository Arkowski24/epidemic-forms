import React, { useState } from 'react';
import {
  Col, Container, Form, Row,
} from 'react-bootstrap';

import dataValidator from '../../../helper/DataValidator';
import derivedHelper from '../../../helper/DerivedHelper';

const InputForm = ({
  title, text, setText, isInvalid,
  isBlocked,
}) => {
  const [dirty, setDirty] = useState(false);
  return (
    <Form>
      <Form.Control
        as="input"
        size="lg"
        rows={1}
        value={text}
        placeholder={title}
        onChange={(event) => { setDirty(true); setText(event.target.value); }}
        isInvalid={dirty && isInvalid}
        disabled={isBlocked}
      />
    </Form>
  );
};

const OneField = ({
  derivedType, index,
  title,
  input, setInput,
  isBlocked,
}) => {
  if (derivedType === 'BIRTHDAY_PESEL' && index === 0) {
    const textAndValidator = input[index] ? JSON.parse(input[index]) : { type: 'PESEL', value: input[index] };
    const text = textAndValidator.value;
    const fieldValidator = textAndValidator.type;

    const validators = ['PESEL', 'Nr dow. os.', 'Nr paszportu', 'Inne'];
    const options = validators
      .map((o, i) => (<option key={o} value={validators[i]}>{o}</option>));

    const setNewInput = (value) => {
      const newInput = input.slice();
      newInput[index] = value;
      const newValues = fieldValidator === 'PESEL' ? derivedHelper.calculateDerived(derivedType, index, newInput) : newInput;
      newValues[index] = JSON.stringify({ type: fieldValidator, value });
      setInput(newValues);
    };

    const setValidator = (validator) => {
      const newInput = input.slice();
      const newValues = validator === 'PESEL' ? derivedHelper.calculateDerived(derivedType, index, newInput) : newInput;
      newValues[index] = JSON.stringify({ type: validator, value: text });
      setInput(newValues);
    };

    const isValid = dataValidator.validateDerivedField(input[index], index, derivedType);
    return (
      <>
        <Col>
          <div className="w-100">
            <InputForm
              title={fieldValidator}
              text={text}
              setText={setNewInput}
              isInvalid={!isValid}
              isBlocked={isBlocked}
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
                  onChange={(event) => setValidator(event.target.value)}
                  value={fieldValidator}
                  disabled={isBlocked}
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
    <div className="w-100 ml-2 mr-2 pl-2 pr-2">
      <InputForm title={title} text={input[index]} setText={setNewInput} isBlocked={isBlocked} />
    </div>
  );
};

const DerivedViewInline = ({
  derivedType,
  titles,
  input, setInput,
  highlighted,
  isBlocked,
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
        isBlocked={isBlocked}
      />
    </Row>
  ));

  return (
    <Container className={`w-100 ml-1 mr-1 mt-1 p-1 rounded border ${highlighted ? 'border-danger shadow-sm' : ''}`}>
      {fields}
    </Container>
  );
};
export default DerivedViewInline;
