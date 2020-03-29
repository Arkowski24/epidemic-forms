import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';

const InputForm = ({ text, setText, isMultiline }) => (
  <div className="p-1">
    <Form>
      <Form.Control
        as="textarea"
        rows={isMultiline ? 3 : 1}
        value={text}
        onChange={(event) => setText(event.target.value)}
      />
    </Form>
  </div>
);

const TextView = ({
  isMultiline,
  currentPage, totalPages,
  message,
  onClickPrev, onClickNext,
  response, setResponse,
}) => (
  <SinglePageForm
    currentPage={currentPage}
    totalPages={totalPages}
    header="Fill-in the blank"
    message={message}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  >
    <InputForm text={response} setText={setResponse} isMultiline={isMultiline} />
  </SinglePageForm>
);

export default TextView;
