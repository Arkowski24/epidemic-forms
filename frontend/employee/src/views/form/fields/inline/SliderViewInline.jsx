import React from 'react';
import {
  Button, Col, Row,
} from 'react-bootstrap';
import InlineView from '../common/InlineView';

const RangeForm = ({
  title,
  minValue, maxValue, step,
  value, setValue,
}) => {
  const decValue = () => { if (value - step >= minValue) setValue(value - step); };
  const incValue = () => { if (value + step <= maxValue) setValue(value + step); };

  return (
    <Row className="pl-1 pr-1 pt-1 pb-1">
      <Col xs={6}>
        <div>
          <h4>{title}</h4>
        </div>
      </Col>
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
        <div className="w-100 pt-1">
          <h4 className="text-center">{value}</h4>
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
  );
};

const SliderViewInline = ({
  title,
  minValue, maxValue, step,
  input, setInput,
  highlighted,
}) => (
  <InlineView highlighted={highlighted}>
    <RangeForm
      title={title}
      minValue={minValue}
      maxValue={maxValue}
      step={step}
      value={input}
      setValue={setInput}
    />
  </InlineView>
);

export default SliderViewInline;
