import React from 'react';

import SinglePageForm from './common/SinglePageForm';

const SimpleView = ({
  title, description,
  currentPage, totalPages,
  onClickPrev, onClickNext,
}) => (
  <SinglePageForm
    title={title}
    description={description}
    currentPage={currentPage}
    totalPages={totalPages}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
  />
);

export default SimpleView;
