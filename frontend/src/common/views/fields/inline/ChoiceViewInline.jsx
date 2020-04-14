import React from 'react';

import {
  Button, ButtonGroup,
  Col,
  Container, Form, Row,
} from 'react-bootstrap';
import InlineView from './common/InlineView';


const ChoiceFormTwoValues = ({
  title, choices,
  input, setInput,
  isBlocked,
}) => {
  const setChecked = (checked) => {
    const newInput = input.map(() => false);
    newInput[0] = checked;
    newInput[1] = !checked;
    setInput(newInput);
  };

  return (
    <div className="mt-1 ml-1 mr-1">
      <Row>
        <Col>
          <div className="pt-1">
            <h4>{title}</h4>
          </div>
        </Col>
        <Col sm="3">
          <div className="w-100 pb-1">
            <ButtonGroup bsPrefix="w-100 btn-group btn-group-justified">
              <Button
                type="button"
                size="lg"
                variant={input[0] ? 'primary' : 'outline-secondary'}
                onClick={(e) => { e.preventDefault(); setChecked(true); }}
                disabled={isBlocked}
              >
                {choices[0]}
              </Button>
              <Button
                type="button"
                size="lg"
                variant={input[1] ? 'primary' : 'outline-secondary'}
                onClick={(e) => { e.preventDefault(); setChecked(false); }}
                disabled={isBlocked}
              >
                {choices[1]}
              </Button>
            </ButtonGroup>
          </div>
        </Col>
      </Row>
    </div>
  );
};

const ChoiceFormSingleChoice = ({
  title, choices,
  input, setInput,
  isBlocked,
}) => {
  const options = choices.map((c, i) => (<option value={i} key={i}>{c}</option>));
  const setChecked = (index) => {
    const newInput = input.map(() => false);
    newInput[index] = !input[index];
    setInput(newInput);
  };
  const value = input.findIndex((v) => v);

  return (
    <Container className="m-1">
      <Row>
        <h4>{title}</h4>
      </Row>
      <Row>
        <div className="w-100 pr-2">
          <Form onSubmit={(e) => e.preventDefault()}>
            <Form.Group>
              <Form.Control
                as="select"
                size="lg"
                onChange={(event) => setChecked(Number(event.target.value))}
                value={value >= 0 ? value : 0}
                disabled={isBlocked}
              >
                {options}
              </Form.Control>
            </Form.Group>
          </Form>
        </div>
      </Row>
    </Container>
  );
};

const ChoiceFormMultiChoice = ({
  title, choices,
  isMultiChoice,
  input, setInput,
  isBlocked,
}) => {
  const buttons = choices.map((c, i) => {
    const setChecked = () => {
      if (isBlocked) return;
      const newInput = isMultiChoice ? input.slice() : input.map(() => false);
      newInput[i] = !input[i];
      setInput(newInput);
    };
    return (
      <div
        className="border rounded p-1 m-1"
        onClick={() => setChecked(true)}
        key={c}
      >
        <Form.Check
          key={c}
          type={isMultiChoice ? 'checkbox' : 'radio'}
          name="choiceForm"
          label={c}
          checked={input[i]}
          readOnly
          disabled={isBlocked}
        />
      </div>
    );
  });

  return (
    <div className="m-1">
      <h4>{title}</h4>
      <Form>
        <fieldset>
          <Form.Group className="mb-0" size="lg">
            {buttons}
          </Form.Group>
        </fieldset>
      </Form>
    </div>
  );
};

const ChoiceViewInline = ({
  title, choices,
  isMultiChoice,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => (
  <InlineView isHighlighted={isHighlighted} isEmployee={isEmployee}>
    {isMultiChoice && (
      <ChoiceFormMultiChoice
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        isBlocked={isBlocked}
      />
    )}
    {!isMultiChoice && (choices.length === 2) && (
      <ChoiceFormTwoValues
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        isBlocked={isBlocked}
      />
    )}
    {!isMultiChoice && !(choices.length === 2) && (
      <ChoiceFormSingleChoice
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        isBlocked={isBlocked}
      />
    )}
  </InlineView>
);

export default ChoiceViewInline;
