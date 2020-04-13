import React from 'react';

import SingleInput from './common/SingleInput';
import SimpleViewInline from '../inline/SimpleViewInline';


const SimpleViewRegular = ({
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
    <SingleInput title={title} description={description} isHighlighted={isHighlighted} />
  );
};

export default SimpleViewRegular;
