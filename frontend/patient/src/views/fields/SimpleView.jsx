import React from 'react';

import SinglePageForm from './common/SinglePageForm';
import PageForm from './common/PageForm';

const SimpleView = ({
  title, description,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  isMultiPage,
}) => (
  <PageForm
    title={title}
    description={description}
    currentPage={currentPage}
    totalPages={totalPages}
    onClickPrev={onClickPrev}
    onClickNext={onClickNext}
    isMultiPage={isMultiPage}
  />
);

export default SimpleView;
