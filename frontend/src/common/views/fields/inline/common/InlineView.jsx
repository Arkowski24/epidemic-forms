import React from 'react';


const InlineView = ({
  children, isHighlighted,
}) => (
  <div className={`w-100 ml-1 mr-1 mt-1 pl-1 pr-1 rounded border ${isHighlighted ? 'border-secondary' : ''}`}>
    {children}
  </div>
);

export default InlineView;
