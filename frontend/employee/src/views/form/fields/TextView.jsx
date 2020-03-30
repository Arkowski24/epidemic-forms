import React from 'react';
import { Form } from 'react-bootstrap';

import SingleInput from './common/SingleInput';

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
  message,
  input, setInput,
}) => (
  <SingleInput
    header="Fill-in the blank"
    message={message}
  >
    <InputForm text={input} setText={setInput} isMultiline={isMultiline} />
  </SingleInput>
);

export default TextView;