import React from 'react';
import SingleInput from './common/SingleInput';

const SimpleView = ({
  title, description,
}) => (
  <SingleInput title={title} description={description} />
);

export default SimpleView;
