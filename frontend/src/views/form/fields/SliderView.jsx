import React from 'react';
import {
  Button, Col, Container, Form, Row,
} from 'react-bootstrap';
import SinglePageForm from './common/SinglePageForm';

const RangeForm = ({
  minValue, maxValue, step,
  value, setValue,
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
              disabled={value - step < minValue}
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
              disabled={value + step > maxValue}
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
            />
          </Form.Group>
        </Form>
      </Row>
    </Container>
  );
};

const SliderView = ({
  minValue, maxValue, step,
  currentPage, totalPages,
  message,
  onClickPrev, onClickNext,
  response, setResponse,
}) => (
  <SinglePageForm
    currentPage={currentPage}
    totalPages={totalPages}
    header="Slide the content"
    message={message}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  >
    <RangeForm
      minValue={minValue}
      maxValue={maxValue}
      step={step}
      value={response}
      setValue={setResponse}
    />
  </SinglePageForm>
);

export default SliderView;
