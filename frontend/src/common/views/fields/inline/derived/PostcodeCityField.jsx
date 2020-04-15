import React from 'react';

import { Col } from 'react-bootstrap';
import InputForm from './InputForm';


const PostcodeCityField = ({
  input, index, setInput, isBlocked,
}) => {
  const postcodeAndCity = input[index] ? JSON.parse(input[index]) : { postcode: '', city: input[index] };
  const { postcode, city } = postcodeAndCity;

  const setNewPostcode = (newPostcode) => {
    const newInput = input.slice();
    newInput[index] = JSON.stringify({ postcode: newPostcode, city });
    setInput(newInput);
  };

  const setNewCity = (newCity) => {
    const newInput = input.slice();
    newInput[index] = JSON.stringify({ postcode, city: newCity });
    setInput(newInput);
  };

  return (
    <>
      <Col sm="4">
        <div className="w-100">
          <InputForm
            title="Kod pocztowy"
            text={postcode}
            setText={setNewPostcode}
            isBlocked={isBlocked}
          />
        </div>
      </Col>
      <Col>
        <div className="w-100 mr-1">
          <InputForm
            title="Miejscowość"
            text={city}
            setText={setNewCity}
            isBlocked={isBlocked}
          />
        </div>
      </Col>
    </>
  );
};

export default PostcodeCityField;
