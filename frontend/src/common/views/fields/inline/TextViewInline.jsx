import React from 'react';
import { Form } from 'react-bootstrap';

import InlineView from '../common/InlineView';

const InputForm = ({
  title,
  text, setText,
  isMultiline,
  isBlocked,
  isInvalid,
}) => {
  const dirty = text.length > 0;
  return (
    <div className="p-1">
      <Form onSubmit={(e) => e.preventDefault()}>
        <Form.Control
          as={isMultiline ? 'textarea' : 'input'}
          size="lg"
          rows={isMultiline ? 10 : 1}
          value={text}
          placeholder={title}
          onChange={(event) => { setText(event.target.value); }}
          disabled={isBlocked}
          isInvalid={dirty && isInvalid}
        />
      </Form>
    </div>
  );
};

const TextViewInline = ({
  title,
  isMultiline,
  input, setInput,
  highlighted,
  isBlocked,
  isInvalid,
}) => (
  <InlineView highlighted={highlighted}>
    <InputForm title={title} text={input} setText={setInput} isMultiline={isMultiline} isBlocked={isBlocked} isInvalid={isInvalid} />
  </InlineView>
);

export default TextViewInline;
