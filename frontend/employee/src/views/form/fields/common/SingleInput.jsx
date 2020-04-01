import React from 'react';

const Header = ({ message }) => (
  <div className="m-2 border-bottom">
    <h1>{message}</h1>
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
}) => (
  <div className="w-100 m-1 p-1 border rounded">
    <Header message={title} />
    <InfoMessage message={description} />
    {children}
  </div>
);


export default SinglePageForm;
