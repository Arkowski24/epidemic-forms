import React from 'react';

import DerivedViewInline from './inline/DerivedViewInline';
import DerivedViewRegular from './regular/DerivedViewRegular';


const DerivedView = ({
  derivedType,
  titles, descriptions,
  isInline,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => {
  if (isInline) {
    return (
      <DerivedViewInline
        derivedType={derivedType}
        titles={titles}
        input={input}
        setInput={setInput}
        isHighlighted={isHighlighted}
        isEmployee={isEmployee}
        isBlocked={isBlocked}
      />
    );
  }

  return (
    <DerivedViewRegular
      derivedType={derivedType}
      titles={titles}
      descriptions={descriptions}
      input={input}
      setInput={setInput}
      isHighlighted={isHighlighted}
      isEmployee={isEmployee}
      isBlocked={isBlocked}
    />
  );
};

export default DerivedView;
