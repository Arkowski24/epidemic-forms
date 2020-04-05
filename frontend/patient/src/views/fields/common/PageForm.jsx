import React from 'react';
import SinglePageForm from './SinglePageForm';
import SingleInput from './SingleInput';

const PageForm = ({
  title, description,
  currentPage, totalPages,
  onClickPrev, onClickNext,
  children,
  isMultiPage,
}) => {
  if (isMultiPage) {
    return (
      <SinglePageForm
        currentPage={currentPage}
        totalPages={totalPages}
        title={title}
        description={description}
        onClickPrev={onClickPrev}
        onClickNext={onClickNext}
      >
        {children}
      </SinglePageForm>
    );
  }
  return (
    <SingleInput
      title={title}
      description={description}
    >
      {children}
    </SingleInput>
  );
};

export default PageForm;
