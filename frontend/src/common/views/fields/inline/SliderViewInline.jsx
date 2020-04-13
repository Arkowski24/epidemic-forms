import React from 'react';

import {
  Button, Col, Row,
} from 'react-bootstrap';
import {
  FaMinus, FaPlus, FaEye, FaEyeSlash,
} from 'react-icons/fa';
import InlineView from './common/InlineView';


const RangeForm = ({
  title,
  minValue, maxValue, step, defaultValue,
  value, setValue,
  isBlocked,
}) => {
  const decValue = () => { if (value - step >= minValue) setValue(value - step); };
  const incValue = () => { if (value + step <= maxValue) setValue(value + step); };
  const hidden = value < minValue;

  const setHidden = () => {
    if (value < minValue) setValue(defaultValue);
    if (value >= minValue) setValue(minValue - step);
  };

  return (
    <Row className="pl-1 pr-1 pt-1 pb-1">
      <Col xs={6}>
        <div>
          <h4>{title}</h4>
        </div>
      </Col>
      <Col xs="auto">
        <Button type="button" disabled={isBlocked} onClick={(e) => { e.preventDefault(); setHidden(); }}>
          {hidden && (<FaEyeSlash />)}
          {!hidden && (<FaEye />)}
        </Button>
      </Col>
      {!hidden && (
      <>
        <Col>
          <div className="w-25">
            <Button
              onClick={decValue}
              variant="danger"
              disabled={isBlocked || value - step < minValue}
            >
              <FaMinus />
            </Button>
          </div>
        </Col>
        <Col>
          <div className="w-100 pt-1">
            <h4 className="text-center">{Math.round(value * 100) / 100}</h4>
          </div>
        </Col>
        <Col>
          <div className="w-25 float-right">
            <Button
              className="btn float-right"
              onClick={incValue}
              variant="success"
              disabled={isBlocked || value + step > maxValue}
            >
              <FaPlus />
            </Button>
          </div>
        </Col>
      </>
      )}
    </Row>
  );
};

const SliderViewInline = ({
  title,
  minValue, maxValue, step, defaultValue,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => (
  <InlineView isHighlighted={isHighlighted} isEmployee={isEmployee}>
    <RangeForm
      title={title}
      minValue={minValue}
      maxValue={maxValue}
      step={step}
      defaultValue={defaultValue}
      value={input}
      setValue={setInput}
      isBlocked={isBlocked}
    />
  </InlineView>
);

export default SliderViewInline;
