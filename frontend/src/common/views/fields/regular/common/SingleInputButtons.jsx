import React from 'react';

import {
  Button, ButtonGroup, Col, Row,
} from 'react-bootstrap';
import {
  FaEye, FaEyeSlash, FaLock, FaLockOpen,
} from 'react-icons/fa';


const Header = ({
  message,
  clickedLeft, onClickLeft,
  clickedRight, onClickRight,
  isBlocked,
}) => (
  <div className="pl-2 pr-2 mb-1 pb-1 border-bottom">
    <Row>
      <Col>
        <h4>{message}</h4>
      </Col>
      <Col xs="auto">
        <ButtonGroup className="pr-2">
          <Button
            type="button"
            variant={clickedLeft ? 'info' : 'outline-info'}
            size="lg"
            disabled={isBlocked}
            onClick={(e) => { e.preventDefault(); onClickLeft(); }}
          >
            {clickedLeft && (<FaEyeSlash />)}
            {!clickedLeft && (<FaEye />)}
          </Button>
          <Button
            type="button"
            variant={clickedRight ? 'warning' : 'outline-warning'}
            size="lg"
            disabled={isBlocked}
            onClick={(e) => { e.preventDefault(); onClickRight(); }}
          >
            {clickedRight && (<FaLock />)}
            {!clickedRight && (<FaLockOpen />)}
          </Button>
        </ButtonGroup>
      </Col>
    </Row>
  </div>
);

const InfoMessage = ({ message }) => (
  <div className="p-2 overflow-auto" style={{ maxHeight: '30vh' }}>
    <p className="text-justify">{message}</p>
  </div>
);

const SingleInputButtons = ({
  title, description,
  children,
  isHighlighted, isEmployee,
  clickedLeft, onClickLeft,
  clickedRight, onClickRight,
  isBlocked,
}) => {
  const highlightColor = isEmployee ? 'border-secondary' : 'border-danger';
  return (
    <div className={`w-100 ml-1 mr-1 mt-1 pl-1 pr-1 pt-1 rounded border ${isHighlighted ? highlightColor : ''}`}>
      <Header
        message={title}
        clickedLeft={clickedLeft}
        onClickLeft={onClickLeft}
        clickedRight={clickedRight}
        onClickRight={onClickRight}
        isBlocked={isBlocked}
      />
      {description && (<InfoMessage message={description} />)}
      {children}
    </div>
  );
};

export default SingleInputButtons;
