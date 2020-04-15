import React from 'react';

import ChoiceViewInline from './inline/ChoiceViewInline';
import ChoiceViewRegular from './regular/ChoiceViewRegular';


const ChoiceView = ({
  title, description,
  isInline,
  choices, isMultiChoice,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => {
  if (isInline) {
    return (
      <ChoiceViewInline
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        isHighlighted={isHighlighted}
        isEmployee={isEmployee}
        isBlocked={isBlocked}
      />
    );
  }

  return (
    <ChoiceViewRegular
      title={title}
      description={description}
      choices={choices}
      isMultiChoice={isMultiChoice}
      input={input}
      setInput={setInput}
      isHighlighted={isHighlighted}
      isEmployee={isEmployee}
      isBlocked={isBlocked}
    />
  );
};

export default ChoiceView;
