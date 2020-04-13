import React from 'react';

import SliderViewInline from './inline/SliderViewInline';
import SliderViewRegular from './regular/SliderViewRegular';


const SliderView = ({
  title, description,
  isInline,
  minValue, maxValue, step, defaultValue,
  input, setInput,
  isHighlighted, isEmployee,
  isBlocked,
}) => {
  if (isInline) {
    return (
      <SliderViewInline
        title={title}
        minValue={minValue}
        maxValue={maxValue}
        step={step}
        defaultValue={defaultValue}
        input={input}
        setInput={setInput}
        isHighlighted={isHighlighted}
        isEmployee={isEmployee}
        isBlocked={isBlocked}
      />
    );
  }

  return (
    <SliderViewRegular
      title={title}
      description={description}
      minValue={minValue}
      maxValue={maxValue}
      step={step}
      defaultValue={defaultValue}
      input={input}
      setInput={setInput}
      isHighlighted={isHighlighted}
      isEmployee={isEmployee}
      isBlocked={isBlocked}
    />
  );
};

export default SliderView;
