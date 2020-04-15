import React from 'react';


const InlineView = ({
  children,
  isHighlighted, isEmployee,
}) => {
  const highlightColor = isEmployee ? 'border-secondary' : 'border-danger';
  return (
    <div className={`w-100 ml-1 mr-1 mt-1 pl-1 pr-1 rounded border ${isHighlighted ? highlightColor : ''}`}>
      {children}
    </div>
  );
};

export default InlineView;
