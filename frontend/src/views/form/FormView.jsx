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
  const [responses, setResponses] = useState([{}]);

  const nextPage = (event) => {
    event.preventDefault();
    if (currentPage + 1 <= form.pages.length) setCurrentPage(currentPage + 1);
  };
  const prevPage = (event) => {
    event.preventDefault();
    if (currentPage - 1 > 0) setCurrentPage(currentPage - 1);
  };

  const setResponse = (response) => {
    const newResponses = responses.slice();
    newResponses[currentPage - 1] = response;
    setResponses(newResponses);
  };

  const createFieldResponse = (f) => {
    if (f.type === 'choice') return f.choices.map(() => false);
    if (f.type === 'sign') return {};
    if (f.type === 'text') return '';
    return null;
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
      const values = fields.map((f) => createFieldResponse(f));

      setForm({ pages: fields });
      setResponses(values);
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
  const response = responses[currentPage - 1];
  return (
    <div>
      {page.type === 'choice' && (
        <ChoiceView
          message={page.message}
          choices={page.choices}
          currentPage={currentPage}
          isMultiple={page.isMultiple}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          response={response}
          setResponse={setResponse}
        />
      )}
      {page.type === 'sign' && (
        <SignView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          response={response}
          setResponse={setResponse}
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
          response={response}
          setResponse={setResponse}
        />
      )}
    </div>
  );
};

export default FormView;