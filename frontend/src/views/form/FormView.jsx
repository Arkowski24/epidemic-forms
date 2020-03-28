import React, { useEffect, useState } from 'react';

import formsService from '../../services/FormsService';

import SignView from './fields/SignView';
import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from '../LoadingView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);

  const nextPage = (event) => {
    event.preventDefault();
    if (currentPage + 1 <= form.pages.length) setCurrentPage(currentPage + 1);
  };
  const prevPage = (event) => {
    event.preventDefault();
    if (currentPage - 1 > 0) setCurrentPage(currentPage - 1);
  };

  useEffect(() => {
    async function fetchData() {
      const rawForm = await formsService.getForm(1);

      const choice = rawForm.choice.map((c) => ({ ...c, type: 'choice' }));
      const sign = rawForm.sign.map((s) => ({ ...s, type: 'sign' }));
      const simple = rawForm.simple.map((s) => ({ ...s, type: 'simple' }));
      const text = rawForm.text.map((t) => ({ ...t, type: 'text' }));
      const fields = choice.concat(sign, simple, text);

      fields.sort((a, b) => a.order - b.order);
      setForm({ pages: fields });
    }
    fetchData();
  }, []);

  if (form === null) {
    return (
      <>
        <LoadingView />
      </>
    );
  }

  const page = form.pages[currentPage - 1];
  return (
    <div>
      {page.type === 'choice' && (
        <ChoiceView
          message={page.message}
          choices={page.choices}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
        />
      )}
      {page.type === 'sign' && (
        <SignView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
        />
      )}
      {page.type === 'simple' && (
        <SimpleView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
        />
      )}
      {page.type === 'text' && (
        <TextView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
        />
      )}
    </div>
  );
};

export default FormView;
