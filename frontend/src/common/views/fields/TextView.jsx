import React from 'react';

import TextViewInline from './inline/TextViewInline';
import TextViewRegular from './regular/TextViewRegular';


const TextView = ({
  title, description,
  isInline,
  isMultiline,
  input, setInput,
  isHighlighted,
  isBlocked,
  isInvalid,
}) => {
  if (isInline) {
    return (
      <TextViewInline
        title={title}
        input={input}
        setInput={setInput}
        isMultiline={isMultiline}
        isHighlighted={isHighlighted}
        isBlocked={isBlocked}
        isInvalid={isInvalid}
      />
    );
  }

  return (
    <TextViewRegular
      title={title}
      description={description}
      input={input}
      setInput={setInput}
      isMultiline={isMultiline}
      isHighlighted={isHighlighted}
      isBlocked={isBlocked}
      isInvalid={isInvalid}
    />
  );
};

export default TextView;
