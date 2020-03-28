import React, { useState } from 'react';
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
}) => {
  const [text, setText] = useState('');

  return (
    <SinglePageForm
      currentPage={currentPage}
      totalPages={totalPages}
      header="Fill-in the blank"
      message={message}
    >
      <InputForm text={text} setText={setText} />
    </SinglePageForm>
  );
};

export default TextView;
