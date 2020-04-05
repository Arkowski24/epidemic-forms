import React from 'react';

const InlineView = ({
  children, highlighted,
}) => (
  <div className={`w-100 ml-1 mp-1 mt-1 pl-1 pr-1 rounded border ${highlighted ? 'border-primary shadow-sm' : ''}`}>
    {children}
  </div>
);

export default InlineView;
