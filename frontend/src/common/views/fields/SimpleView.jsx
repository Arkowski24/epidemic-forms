import React from 'react';

import SimpleViewInline from './inline/SimpleViewInline';
import SimpleViewRegular from './regular/SimpleViewRegular';


const SimpleView = ({
  title, description,
  isInline,
  isHighlighted, isEmployee,
}) => {
  if (isInline) {
    return (
      <SimpleViewInline
        title={title}
        isHighlighted={isHighlighted}
        isEmployee={isEmployee}
      />
    );
  }

  return (
    <SimpleViewRegular
      title={title}
      description={description}
      isInline={isInline}
      isHighlighted={isHighlighted}
      isEmployee={isEmployee}
    />
  );
};

export default SimpleView;
