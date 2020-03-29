import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';


const ChoiceForm = ({
  choices, isMultiple,
  response, setResponse,
}) => {
  const buttons = choices.map((c, i) => {
    const setChecked = () => {
      const newResponse = isMultiple ? response.slice() : response.map(() => false);
      newResponse[i] = !response[i];
      setResponse(newResponse);
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
          checked={response[i]}
          readOnly={true}
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
  currentPage, totalPages,
  message,
  choices, isMultiple,
  onClickPrev, onClickNext,
  response, setResponse,
}) => (
  <SinglePageForm
    currentPage={currentPage}
    totalPages={totalPages}
    header={isMultiple ? 'Choose multiple' : 'Choose one'}
    message={message}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  >
    <ChoiceForm
      choices={choices}
      isMultiple={isMultiple}
      response={response}
      setResponse={setResponse}
    />
  </SinglePageForm>
);

export default ChoiceView;
