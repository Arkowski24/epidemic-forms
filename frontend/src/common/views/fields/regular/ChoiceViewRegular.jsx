import React from 'react';

import { Form } from 'react-bootstrap';
import SingleInput from './common/SingleInput';


const ChoiceForm = ({
  choices,
  isMultiChoice,
  input, setInput,
  isBlocked,
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
        <Form onSubmit={(e) => e.preventDefault()}>
          <Form.Check
            key={c}
            type={isMultiChoice ? 'checkbox' : 'radio'}
            name="choiceForm"
            label={c}
            checked={input[i]}
            readOnly
            disabled={isBlocked}
          />
        </Form>
      </div>
    );
  });

  return (
    <div className="m-1">
      <Form>
        <fieldset>
          <Form.Group>
            {buttons}
          </Form.Group>
        </fieldset>
      </Form>
    </div>
  );
};

const ChoiceViewRegular = ({
  title, description,
  choices, isMultiChoice,
  input, setInput,
  isHighlighted,
  isBlocked,
}) => (
  <SingleInput title={title} description={description} isHighlighted={isHighlighted}>
    <ChoiceForm
      choices={choices}
      isMultiChoice={isMultiChoice}
      input={input}
      setInput={setInput}
      isBlocked={isBlocked}
    />
  </SingleInput>
);

export default ChoiceViewRegular;
