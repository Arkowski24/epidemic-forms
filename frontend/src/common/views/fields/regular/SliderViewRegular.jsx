import React from 'react';

import {
  Button, Col, Container, Row,
} from 'react-bootstrap';
import { FaPlus, FaMinus } from 'react-icons/fa';
import Slider from 'rc-slider';
import SingleInputButton from './common/SingleInputButton';
import 'rc-slider/assets/index.css';


const RangeForm = ({
  minValue, maxValue, step,
  value, setValue,
  isBlocked,
}) => {
  const decValue = () => { if (value - step >= minValue) setValue(value - step); };
  const incValue = () => { if (value + step <= maxValue) setValue(value + step); };

  return (
    <Container>
      <Row>
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
          <div className="w-100">
            <h2 className="text-center">{Math.round(value * 100) / 100}</h2>
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
      </Row>
      <Row>
        <div className="w-100 mb-1 pl-2 pr-2">
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
    </Container>
  );
};

const SliderViewRegular = ({
  title, description,
  minValue, maxValue, step, defaultValue,
  input, setInput,
  highlighted,
  isBlocked,
}) => {
  const hidden = input < minValue;

  const setHidden = () => {
    if (input < minValue) setInput(defaultValue);
    if (input >= minValue) setInput(minValue - step);
  };

  return (
    <SingleInputButton
      title={title}
      description={description}
      highlighted={highlighted}
      clicked={hidden}
      onClick={setHidden}
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
          isBlocked={isBlocked}
        />
      )}
    </SingleInputButton>
  );
};

export default SliderViewRegular;
