import React from 'react';

import {
  Button, ButtonGroup, Col,
} from 'react-bootstrap';

const ChoiceInfoField = ({
  title,
  index,
  input, setInput,
  isBlocked,
}) => {
  const choices = ['TAK', 'NIE'];
  const setChecked = (checked) => {
    if (checked && input[index] === choices[0]) { return; }
    const newInput = checked ? input.map(() => '') : input.map(() => 'N.D.');
    newInput[index] = checked ? choices[0] : choices[1];
    setInput(newInput);
  };

  return (
    <>
      <Col>
        <div className="pt-1 pb-1 pl-1">
          <h4>{title}</h4>
        </div>
      </Col>
      <Col sm="3">
        <div className="w-100 pt-1 pb-1 pr-1">
          <ButtonGroup bsPrefix="w-100 btn-group btn-group-justified">
            <Button
              type="button"
              size="lg"
              variant={input[index] === choices[0] ? 'primary' : 'outline-secondary'}
              onClick={(e) => { e.preventDefault(); setChecked(true); }}
              disabled={isBlocked}
            >
              {choices[0]}
            </Button>
            <Button
              type="button"
              size="lg"
              variant={input[index] === choices[1] ? 'primary' : 'outline-secondary'}
              onClick={(e) => { e.preventDefault(); setChecked(false); }}
              disabled={isBlocked}
            >
              {choices[1]}
            </Button>
          </ButtonGroup>
        </div>
      </Col>
    </>
  );
};

export default ChoiceInfoField;
