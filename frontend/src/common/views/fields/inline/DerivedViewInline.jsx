import React from 'react';

import { Container, Row } from 'react-bootstrap';
import {
  InputForm,
  PersonalInfoField,
  PostcodeCityField,
  ChoiceInfoField,
} from './derived';

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

  if (derivedType === 'CHOICE_INFO' && index === 0) {
    return (
      <ChoiceInfoField
        title={title}
        index={index}
        input={input}
        setInput={setInput}
        isBlocked={isBlocked}
      />
    );
  }

  if (derivedType === 'ADDRESS' && index === 1) {
    return (
      <PostcodeCityField
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

  const isBlockedChoice = derivedType === 'CHOICE_INFO' && (input[0].length === 0 || input[0] === 'NIE');
  const isBlockedField = isBlocked || isBlockedChoice;
  return (
    <div className="w-100 ml-2 mr-2 pl-2 pr-2 pb-1">
      <InputForm title={title} text={input[index]} setText={setNewInput} isBlocked={isBlockedField} />
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
