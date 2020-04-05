import React from 'react';

import PageForm from './common/PageForm';
import SimpleViewInline from './inline/SimpleViewInline';

const SimpleView = ({
  title, description,
  isInline,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  isMultiPage,
}) => {
  if (!isMultiPage && isInline) {
    return (
      <SimpleViewInline
        title={title}
      />
    );
  }

  return (
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
};

export default SimpleView;
