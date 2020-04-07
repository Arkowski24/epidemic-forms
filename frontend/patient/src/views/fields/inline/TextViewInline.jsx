import React from 'react';
import { Form } from 'react-bootstrap';

import InlineView from '../common/InlineView';

const InputForm = ({
  title,
  text, setText,
  isMultiline,
  isBlocked,
  isInvalid,
}) => (
  <div className="p-1">
    <Form>
      <Form.Control
        as={isMultiline ? 'textarea' : 'input'}
        size="lg"
        rows={isMultiline ? 3 : 1}
        value={text}
        placeholder={title}
        onChange={(event) => setText(event.target.value)}
        disabled={isBlocked}
        isInvalid={isInvalid}
      />
    </Form>
  </div>
);

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
