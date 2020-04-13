import React from 'react';
import InlineView from './common/InlineView';

const SimpleViewInline = ({
  title,
  highlighted,
}) => (
  <InlineView highlighted={highlighted}>
    <div className="w-100 m-1">
      <h4>{title}</h4>
    </div>
  </InlineView>
);

export default SimpleViewInline;
