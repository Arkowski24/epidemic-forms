import React from 'react';
import {
  Button, Col, Container, Form, Row,
} from 'react-bootstrap';

import PageForm from './common/PageForm';
import SliderViewInline from './inline/SliderViewInline';

const RangeForm = ({
  minValue, maxValue, step,
  value, setValue,
  disabled,
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
              disabled={disabled || (value - step < minValue)}
            >
              -
            </Button>
          </div>
        </Col>
        <Col>
          <div className="w-100">
            <h2 className="text-center">{value}</h2>
          </div>
        </Col>
        <Col>
          <div className="w-25 float-right">
            <Button
              className="btn float-right"
              onClick={incValue}
              variant="success"
              disabled={disabled || (value + step > maxValue)}
            >
              +
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
              disabled={disabled}
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
  minValue, maxValue, step,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  input, setInput,
  disabled,
  isMultiPage,
}) => {
  if (!isMultiPage && isInline) {
    return (
      <SliderViewInline
        title={title}
        minValue={minValue}
        maxValue={maxValue}
        step={step}
        input={input}
        setInput={setInput}
      />
    );
  }

  return (
    <PageForm
      title={title}
      description={description}
      currentPage={currentPage}
      totalPages={totalPages}
      onClickPrev={onClickPrev}
      onClickNext={onClickNext}
      isMultiPage={isMultiPage}
    >
      <RangeForm
        minValue={minValue}
        maxValue={maxValue}
        step={step}
        value={input}
        setValue={setInput}
        disabled={disabled}
      />
    </PageForm>
  );
};

export default SliderView;
