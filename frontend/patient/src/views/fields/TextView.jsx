import React from 'react';
import { Form } from 'react-bootstrap';

import SingleInput from './common/SingleInput';
import TextViewInline from './inline/TextViewInline';

const InputForm = ({
  text, setText, isMultiline, isBlocked,
  isInvalid,
}) => (
  <div className="p-1">
    <Form>
      <Form.Control
        as={isMultiline ? 'textarea' : 'input'}
        rows={isMultiline ? 3 : 1}
        value={text}
        onChange={(event) => setText(event.target.value)}
        disabled={isBlocked}
        isInvalid={isInvalid}
      />
    </Form>
  </div>
);

const TextView = ({
  title, description,
  isInline,
  isMultiline,
  input, setInput,
  highlighted,
  isBlocked,
  isInvalid,
}) => {
  if (isInline) {
    return (
      <TextViewInline
        title={title}
        input={input}
        setInput={setInput}
        isMultiline={isMultiline}
        highlighted={highlighted}
        isBlocked={isBlocked}
        isInvalid={isInvalid}
      />
    );
  }

  return (
    <SingleInput title={title} description={description} highlighted={highlighted}>
      <InputForm text={input} setText={setInput} isMultiline={isMultiline} isBlocked={isBlocked} isInvalid={isInvalid} />
    </SingleInput>
  );
};

export default TextView;
