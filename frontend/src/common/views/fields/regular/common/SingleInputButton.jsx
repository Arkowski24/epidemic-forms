import React from 'react';

import { Button, Col, Row } from 'react-bootstrap';
import { FaEye, FaEyeSlash } from 'react-icons/fa';


const Header = ({
  message,
  clicked, onClick,
  isBlocked,
}) => (
  <div className="pl-2 pr-2 mb-1 pb-1 border-bottom">
    <Row>
      <Col>
        <h4>{message}</h4>
      </Col>
      <Col xs="auto">
        <div className="pr-2">
          <Button type="button" variant="info" disabled={isBlocked} onClick={(e) => { e.preventDefault(); onClick(); }}>
            {clicked && (<FaEyeSlash />)}
            {!clicked && (<FaEye />)}
          </Button>
        </div>
      </Col>
    </Row>
  </div>
);

const InfoMessage = ({ message }) => (
  <div className="p-2 overflow-auto" style={{ maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const SingleInputButton = ({
  title, description,
  children,
  isHighlighted, isEmployee,
  clicked, onClick,
  isBlocked,
}) => {
  const highlightColor = isEmployee ? 'border-secondary' : 'border-danger';
  return (
    <div className={`w-100 ml-1 mr-1 mt-1 pl-1 pr-1 pt-1 rounded border ${isHighlighted ? highlightColor : ''}`}>
      <Header message={title} clicked={clicked} onClick={onClick} isBlocked={isBlocked} />
      {description && (<InfoMessage message={description} />)}
      {children}
    </div>
  );
};

export default SingleInputButton;