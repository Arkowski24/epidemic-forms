import React from 'react';

import SingleInput from './common/SingleInput';
import SimpleViewInline from '../inline/SimpleViewInline';


const SimpleViewRegular = ({
  title, description,
  isInline,
  highlighted,
}) => {
  if (isInline) {
    return (
      <SimpleViewInline
        title={title}
        highlighted={highlighted}
      />
    );
  }

  return (
    <SingleInput title={title} description={description} highlighted={highlighted} />
  );
};

export default SimpleViewRegular;
