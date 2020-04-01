import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';

const ChoiceForm = ({
  choices, isMultiple,
  input, setInput,
}) => {
  const buttons = choices.map((c, i) => {
    const setChecked = () => {
      const newInput = isMultiple ? input.slice() : input.map(() => false);
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
          type={isMultiple ? 'checkbox' : 'radio'}
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
  choices, isMultiple,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  input, setInput,
}) => (
  <SinglePageForm
    currentPage={currentPage}
    totalPages={totalPages}
    title={title}
    description={description}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  >
    <ChoiceForm
      choices={choices}
      isMultiple={isMultiple}
      input={input}
      setInput={setInput}
    />
  </SinglePageForm>
);

export default ChoiceView;
