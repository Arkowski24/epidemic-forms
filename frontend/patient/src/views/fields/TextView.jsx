import React from 'react';
import { Form } from 'react-bootstrap';

import PageForm from './common/PageForm';
import TextViewInline from './inline/TextViewInline';

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
  isInline,
  isMultiline,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  input, setInput,
  disabled,
  isMultiPage,
}) => {
  if (!isMultiPage && isInline) {
    return (
      <TextViewInline
        title={title}
        input={input}
        setInput={setInput}
        isMultiline={isMultiline}
      />
    );
  }

  return (
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
};

export default TextView;
