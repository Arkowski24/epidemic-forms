import React from 'react';

import { Form } from 'react-bootstrap';

const InputForm = ({
  title, text, setText, isInvalid,
  isBlocked,
}) => {
  const dirty = text.length > 0;
  return (
    <Form onSubmit={(e) => e.preventDefault()}>
      <Form.Control
        as="input"
        size="lg"
        rows={1}
        value={text}
        placeholder={title}
        onChange={(event) => setText(event.target.value)}
        isInvalid={dirty && isInvalid}
        disabled={isBlocked}
      />
    </Form>
  );
};

export default InputForm;
