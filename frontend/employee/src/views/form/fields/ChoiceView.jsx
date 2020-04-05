import React from 'react';
import { Form } from 'react-bootstrap';

import SingleInput from './common/SingleInput';
import ChoiceViewInline from './inline/ChoiceViewInline';

const ChoiceForm = ({
  choices,
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

const ChoiceView = ({
  title, description,
  isInline,
  choices, isMultiChoice,
  input, setInput,
  highlighted,
}) => {
  if (isInline) {
    return (
      <ChoiceViewInline
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        highlighted={highlighted}
      />
    );
  }

  return (
    <SingleInput title={title} description={description} highlighted={highlighted}>
      <ChoiceForm
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
      />
    </SingleInput>
  );
};

export default ChoiceView;
