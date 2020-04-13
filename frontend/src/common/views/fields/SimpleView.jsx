import React from 'react';

import SimpleViewInline from './inline/SimpleViewInline';
import SimpleViewRegular from './regular/SimpleViewRegular';

const SimpleView = ({
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
    <SimpleViewRegular
      title={title}
      description={description}
      isInline={isInline}
      highlighted={highlighted}
    />
  );
};

export default SimpleView;
