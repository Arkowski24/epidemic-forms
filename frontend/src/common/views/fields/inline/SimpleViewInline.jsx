import React from 'react';

import InlineView from './common/InlineView';


const SimpleViewInline = ({
  title,
  isHighlighted,
}) => (
  <InlineView isHighlighted={isHighlighted}>
    <div className="w-100 m-1">
      <h4>{title}</h4>
    </div>
  </InlineView>
);

export default SimpleViewInline;
