import React from 'react';

import DerivedViewInline from './inline/DerivedViewInline';
import DerivedViewRegular from './regular/DerivedViewRegular';


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

  return (
    <DerivedViewRegular
      derivedType={derivedType}
      titles={titles}
      descriptions={descriptions}
      input={input}
      setInput={setInput}
      highlighted={highlighted}
      isBlocked={isBlocked}
    />
  );
};

export default DerivedView;
