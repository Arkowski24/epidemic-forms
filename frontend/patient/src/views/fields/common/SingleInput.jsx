import React from 'react';

const Header = ({ message }) => (
  <div className="m-2 border-bottom">
    <h4>{message}</h4>
  </div>
);

const InfoMessage = ({ message }) => (
  <div className="p-2 overflow-auto" style={{ maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const SinglePageForm = ({
  title, description,
  children,
  highlighted,
}) => (
  <div className={`w-100 m-1 p-1 rounded border ${highlighted ? 'border-primary shadow-sm' : ''}`}>
    <Header message={title} />
    {description && (<InfoMessage message={description} />)}
    {children}
  </div>
);

export default SinglePageForm;
