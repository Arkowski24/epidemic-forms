import React from 'react';
import { Form } from 'react-bootstrap';

import SingleInput from './common/SingleInput';
import TextViewInline from './inline/TextViewInline';

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
  title, description,
  isInline,
  isMultiline,
  input, setInput,
  highlighted,
}) => {
  if (isInline) {
    return (
      <TextViewInline
        title={title}
        input={input}
        setInput={setInput}
        isMultiline={isMultiline}
        highlighted={highlighted}
      />
    );
  }

  return (
    <SingleInput title={title} description={description} highlighted={highlighted}>
      <InputForm text={input} setText={setInput} isMultiline={isMultiline} />
    </SingleInput>
  );
};

export default TextView;
