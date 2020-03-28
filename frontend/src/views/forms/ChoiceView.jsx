import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';

const choiceFormStyle = { padding: '10px 20px' };

const ChoiceForm = ({ choices, isMultiple }) => {
  const buttons = choices.map((c) => (
    <Form.Check
      key={c}
      type={isMultiple ? 'checkbox' : 'radio'}
      name="choiceForm"
      label={c}
    />
  ));

  return (
    <div style={choiceFormStyle}>
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
}) => (
  <SinglePageForm
    currentPage={currentPage}
    totalPages={totalPages}
    header={isMultiple ? 'Choose multiple' : 'Choose one'}
    message={message}
  >
    <ChoiceForm choices={choices} isMultiple={isMultiple} />
  </SinglePageForm>
);

export default ChoiceView;
