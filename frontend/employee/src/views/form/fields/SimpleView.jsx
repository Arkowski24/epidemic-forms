import React from 'react';
import SingleInput from './common/SingleInput';

const SimpleView = ({
  message,
}) => (
  <SingleInput
    header="Read message"
    message={message}
  />
);

export default SimpleView;
