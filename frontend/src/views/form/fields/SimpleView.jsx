import React from 'react';
import SinglePageForm from './common/SinglePageForm';

const SimpleView = ({
  currentPage, totalPages,
  message,
  onClickPrev, onClickNext,
}) => (
  <SinglePageForm
    header="Read message"
    currentPage={currentPage}
    totalPages={totalPages}
    message={message}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  />
);

export default SimpleView;
