import React from 'react';


const Header = ({ message }) => (
  <div className="ml-2 mr-2 mb-1 border-bottom">
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
  <div className={`w-100 ml-1 mr-1 mt-1 pl-1 pr-1 pt-1 rounded border ${highlighted ? 'border-secondary' : ''}`}>
    <Header message={title} />
    {description && (<InfoMessage message={description} />)}
    {children}
  </div>
);

export default SinglePageForm;
