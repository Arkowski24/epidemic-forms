import React from 'react';
import { Form } from 'react-bootstrap';

import PageForm from './common/PageForm';
import ChoiceViewInline from './inline/ChoiceViewInline';

const ChoiceForm = ({
  choices, isMultiChoice,
  input, setInput,
  disabled,
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
          disabled={disabled}
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
  currentPage, totalPages,
  onClickPrev, onClickNext,
  input, setInput,
  disabled,
  isMultiPage,
}) => {
  if (!isMultiPage && isInline) {
    return (
      <ChoiceViewInline
        title={title}
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
      />
    );
  }

  return (
    <PageForm
      currentPage={currentPage}
      totalPages={totalPages}
      title={title}
      description={description}
      onClickPrev={onClickPrev}
      onClickNext={onClickNext}
      isMultiPage={isMultiPage}
    >
      <ChoiceForm
        choices={choices}
        isMultiChoice={isMultiChoice}
        input={input}
        setInput={setInput}
        disabled={disabled}
      />
    </PageForm>
  );
};

export default ChoiceView;
