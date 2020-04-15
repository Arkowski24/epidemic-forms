import React from 'react';

import { Col, Form } from 'react-bootstrap';
import InputForm from './InputForm';

import derivedHelper from '../../../../helpers/DerivedHelper';
import dataValidator from '../../../../helpers/DataValidator';


const PersonalInfoField = ({
  input, index, derivedType, setInput, isBlocked,
}) => {
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
        <div className="w-100 mb-1">
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
          <Form onSubmit={(e) => e.preventDefault()}>
            <Form.Group className="mb-1">
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
};

export default PersonalInfoField;
