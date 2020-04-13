import React from 'react';

import SimpleViewInline from './inline/SimpleViewInline';
import SimpleViewRegular from './regular/SimpleViewRegular';


const SimpleView = ({
  title, description,
  isInline,
  isHighlighted,
}) => {
  if (isInline) {
    return (
      <SimpleViewInline
        title={title}
        isHighlighted={isHighlighted}
      />
    );
  }

  return (
    <SimpleViewRegular
      title={title}
      description={description}
      isInline={isInline}
      isHighlighted={isHighlighted}
    />
  );
};

export default SimpleView;
