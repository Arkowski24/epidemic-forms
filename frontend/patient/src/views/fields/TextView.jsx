import React from 'react';
import { Form } from 'react-bootstrap';

import SinglePageForm from './common/SinglePageForm';
import PageForm from './common/PageForm';

const InputForm = ({
  text, setText,
  isMultiline, disabled,
}) => (
  <div className="p-1">
    <Form>
      <Form.Control
        as="textarea"
        rows={isMultiline ? 3 : 1}
        value={text}
        onChange={(event) => setText(event.target.value)}
        disabled={disabled}
      />
    </Form>
  </div>
);

const TextView = ({
  title, description,
  isMultiline,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  input, setInput,
  disabled,
  isMultiPage,
}) => (
  <PageForm
    title={title}
    description={description}
    currentPage={currentPage}
    totalPages={totalPages}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
    isMultiPage={isMultiPage}
  >
    <InputForm
      text={input}
      setText={setInput}
      isMultiline={isMultiline}
      disabled={disabled}
    />
  </PageForm>
);

export default TextView;
