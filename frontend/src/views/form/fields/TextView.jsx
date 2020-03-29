import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';

const commonStyle = { padding: '5px' };

const InputForm = ({ text, setText }) => (
  <div style={commonStyle}>
    <Form>
      <Form.Control
        as="textarea"
        rows="3"
        value={text}
        onChange={(event) => setText(event.target.value)}
      />
    </Form>
  </div>
);

const TextView = ({
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
    <InputForm text={response} setText={setResponse} />
  </SinglePageForm>
);

export default TextView;
