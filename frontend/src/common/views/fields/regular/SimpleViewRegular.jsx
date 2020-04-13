import React from 'react';

import SingleInput from './common/SingleInput';
import SimpleViewInline from '../inline/SimpleViewInline';


const SimpleViewRegular = ({
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
    <SingleInput title={title} description={description} isHighlighted={isHighlighted} />
  );
};

export default SimpleViewRegular;
