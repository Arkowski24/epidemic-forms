import {
  Col,
  Container, Form, Row,
} from 'react-bootstrap';
import React from 'react';
import BootstrapSwitchButton from 'bootstrap-switch-button-react';
import InlineView from '../common/InlineView';

const ChoiceFormTwoValues = ({
  title, choices,
  input, setInput,
}) => {
  const setChecked = (checked) => {
    const newInput = input.map(() => false);
    newInput[0] = checked;
    newInput[1] = !checked;
    setInput(newInput);
  };

  return (
    <div className="m-1">
      <Row>
        <Col sm="auto">
          <div className="ml-0 pt-1">
            <h4>{title}</h4>
          </div>
        </Col>
        <Col>
          <div className="pt-1">
            <BootstrapSwitchButton
              checked={input[0]}
              onlabel={choices[0]}
              offlabel={choices[1]}
              style="w-25 float-right"
              onChange={(checked) => { setChecked(checked); }}
            />
          </div>
        </Col>
      </Row>
    </div>
  );
};

const ChoiceFormSingleChoice = ({
  title, choices,
  input, setInput,
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
          <Form>
            <Form.Group>
              <Form.Control
                as="select"
                size="lg"
                onChange={(event) => setChecked(Number(event.target.value))}
                value={value >= 0 ? value : 0}
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
}) => {
  const buttons = choices.map((c, i) => {
    const setChecked = () => {
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
  highlighted,
}) => (
  <InlineView highlighted={highlighted}>
    {isMultiChoice && (
      <ChoiceFormMultiChoice
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
      />
    )}
    {!isMultiChoice && (choices.length === 2) && (
      <ChoiceFormTwoValues
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
      />
    )}
    {!isMultiChoice && !(choices.length === 2) && (
      <ChoiceFormSingleChoice
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
      />
    )}
  </InlineView>
);

export default ChoiceViewInline;