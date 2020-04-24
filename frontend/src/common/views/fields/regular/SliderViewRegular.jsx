import React, { useState } from 'react';

import {
  Button, ButtonGroup, Col, Row,
} from 'react-bootstrap';
import { FaPlus, FaMinus } from 'react-icons/fa';
import Slider from 'rc-slider';
import SingleInputButtons from './common/SingleInputButtons';
import 'rc-slider/assets/index.css';


const RangeForm = ({
  minValue, maxValue, step,
  value, setValue,
  isBlocked,
}) => {
  const decValue = (multiple) => { if (value - multiple * step >= minValue) { setValue(value - multiple * step); } else { setValue(minValue); } };
  const incValue = (multiple) => { if (value + multiple * step <= maxValue) { setValue(value + multiple * step); } else { setValue(maxValue); } };

  return (
    <div className="w-100 pl-3 pr-3">
      <Row>
        <Col>
          <ButtonGroup>
            <Button
              type="button"
              variant="danger"
              size="lg"
              onClick={() => { decValue(1); }}
              disabled={isBlocked || value - step < minValue}
            >
              <FaMinus />
            </Button>
            <Button
              type="button"
              variant="outline-danger"
              size="lg"
              onClick={() => { decValue(5); }}
              disabled={isBlocked || value - step < minValue}
            >
              5-
            </Button>
          </ButtonGroup>
        </Col>
        <Col>
          <div className="w-100">
            <h2 className="text-center">{(Math.round(value * 100) / 100).toFixed(1)}</h2>
          </div>
        </Col>
        <Col>
          <ButtonGroup className="float-right">
            <Button
              type="button"
              variant="outline-primary"
              size="lg"
              onClick={() => { incValue(5); }}
              disabled={isBlocked || value + step > maxValue}
            >
              5+
            </Button>
            <Button
              type="button"
              variant="primary"
              size="lg"
              onClick={() => { incValue(1); }}
              disabled={isBlocked || value + step > maxValue}
            >
              <FaPlus />
            </Button>
          </ButtonGroup>
        </Col>
      </Row>
      <Row>
        <div className="w-100 mt-1 p-2 ml-1 mr-1">
          <Slider
            min={minValue}
            max={maxValue}
            step={step}
            value={value}
            onChange={(v) => setValue(v)}
            disabled={isBlocked}
            handleStyle={{
              height: 20,
              width: 20,
              marginTop: -8,
            }}
          />
        </div>
      </Row>
    </div>
  );
};

const SliderViewRegular = ({
  title, description,
  minValue, maxValue, step, defaultValue,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => {
  const [isLocked, setIsLocked] = useState(false);
  const hidden = input < minValue;

  const setHidden = () => {
    if (input < minValue) setInput(defaultValue);
    if (input >= minValue) setInput(minValue - step);
  };

  const setLocked = () => { setIsLocked(!isLocked); };

  return (
    <SingleInputButtons
      title={title}
      description={description}
      isHighlighted={isHighlighted}
      isEmployee={isEmployee}
      clickedLeft={hidden}
      onClickLeft={setHidden}
      clickedRight={isLocked}
      onClickRight={setLocked}
      isBlocked={isBlocked}
    >
      {!hidden && (
        <RangeForm
          minValue={minValue}
          maxValue={maxValue}
          step={step}
          defaultValue={defaultValue}
          value={input}
          setValue={setInput}
          isBlocked={isBlocked || isLocked}
        />
      )}
    </SingleInputButtons>
  );
};

export default SliderViewRegular;
