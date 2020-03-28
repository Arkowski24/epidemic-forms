import { Button, Col, Row } from 'react-bootstrap';
import React from 'react';

const PrevNextButtons = ({ style }) => (
  <div style={style}>
    <Row>
      <Col>
        <Button variant="light" type="button">
          Previous
        </Button>
      </Col>
      <Col>
        <Button className="btn float-right" variant="primary" type="submit">
          Next
        </Button>
      </Col>
    </Row>
  </div>
);

export default PrevNextButtons;
