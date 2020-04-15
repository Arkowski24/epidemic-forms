import React from 'react';

import { Container, Row } from 'react-bootstrap';
import InputForm from './derived/InputForm';
import PersonalInfoField from './derived/PersonalInfoField';

import derivedHelper from '../../../helpers/DerivedHelper';


const OneField = ({
  derivedType, index,
  title,
  input, setInput,
  isBlocked,
}) => {
  if (derivedType === 'BIRTHDAY_PESEL' && index === 0) {
    return (
      <PersonalInfoField
        input={input}
        setInput={setInput}
        index={index}
        derivedType={derivedType}
        isBlocked={isBlocked}
      />
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
  isHighlighted, isEmployee,
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

  const highlightColor = isEmployee ? 'border-secondary' : 'border-danger';
  return (
    <Container className={`w-100 ml-1 mr-1 mt-1 p-1 rounded border ${isHighlighted ? highlightColor : ''}`}>
      {fields}
    </Container>
  );
};
export default DerivedViewInline;
