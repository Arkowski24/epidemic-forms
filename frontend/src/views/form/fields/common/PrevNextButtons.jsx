import { Button, Col, Row } from 'react-bootstrap';
import React from 'react';

const PrevNextButtons = ({
  onClickPrev, onClickNext,
  isFirst, isFinal,
}) => (
  <div className="p-2 border-top">
    <Row>
      <Col>
        <Button
          variant="light"
          type="button"
          onClick={onClickPrev}
          disabled={isFirst}
        >
          Previous
        </Button>
      </Col>
      <Col>
        <Button
          className="btn float-right"
          variant={isFinal ? 'success' : 'primary'}
          type={isFinal ? 'submit' : 'button'}
          onClick={onClickNext}
        >
          {isFinal ? 'Submit' : 'Next'}
        </Button>
      </Col>
    </Row>
  </div>
);

export default PrevNextButtons;
