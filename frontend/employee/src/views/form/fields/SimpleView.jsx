import React from 'react';
import SingleInput from './common/SingleInput';

const SimpleView = ({
  title, description,
  highlighted,
}) => (
  <SingleInput title={title} description={description} highlighted={highlighted} />
);

export default SimpleView;
