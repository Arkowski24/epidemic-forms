import React from 'react';
import {
  Button, Col, Container, Form, Row,
} from 'react-bootstrap';
import { FaPlus, FaMinus } from 'react-icons/fa';
import SingleInputButton from './common/SingleInputButton';
import SliderViewInline from './inline/SliderViewInline';

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
        <Form className="w-100">
          <Form.Group controlId="formRange">
            <Form.Control
              type="range"
              min={minValue}
              max={maxValue}
              step={step}
              value={value}
              onChange={(event) => setValue(Number(event.target.value))}
              disabled={isBlocked}
            />
          </Form.Group>
        </Form>
      </Row>
    </Container>
  );
};

const SliderView = ({
  title, description,
  isInline,
  minValue, maxValue, step, defaultValue,
  input, setInput,
  highlighted,
  isBlocked,
  isInvalid
}) => {
  const hidden = input < minValue;

  const setHidden = () => {
    if (input < minValue) setInput(defaultValue);
    if (input >= minValue) setInput(minValue - step);
  };

  if (isInline) {
    return (
      <SliderViewInline
        title={title}
        minValue={minValue}
        maxValue={maxValue}
        step={step}
        defaultValue={defaultValue}
        input={input}
        setInput={setInput}
        highlighted={highlighted}
        isBlocked={isBlocked}
        isInvalid={isInvalid}
      />
    );
  }

  return (
    <SingleInputButton
      title={title}
      description={description}
      highlighted={highlighted || isInvalid}
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

export default SliderView;
