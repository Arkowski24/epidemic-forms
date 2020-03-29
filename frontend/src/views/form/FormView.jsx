import React, { useEffect, useState } from 'react';

import formsService from '../../services/FormsService';

import SignView from './fields/SignView';
import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from '../LoadingView';
import EndView from '../EndView';
import SliderView from './fields/SliderView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [inputsState, setInputsState] = useState([{}]);
  const [finished, setFinished] = useState(null);

  const sendFormResponse = async () => {
    await formsService.postResponse(inputsState);
    setFinished(true);
  };

  const nextPage = (event) => {
    event.preventDefault();
    if (currentPage === form.pages.length) sendFormResponse();
    else setCurrentPage(currentPage + 1);
  };
  const prevPage = (event) => {
    event.preventDefault();
    if (currentPage - 1 > 0) setCurrentPage(currentPage - 1);
  };

  const setInput = (input) => {
    const newResponses = inputsState.slice();
    newResponses[currentPage - 1] = input;
    setInputsState(newResponses);
  };

  const createFieldResponse = (f) => {
    if (f.type === 'choice') return f.choices.map(() => false);
    if (f.type === 'text') return '';
    if (f.type === 'slide') return f.minValue;
    return null;
  };

  useEffect(() => {
    async function fetchData() {
      const rawForm = await formsService.getForm(1);

      const formFinished = rawForm.finished;
      setFinished(formFinished);
      if (formFinished) return;

      const { schema } = rawForm;
      const choice = schema.choice.map((c) => ({ ...c, type: 'choice' }));
      const sign = schema.sign.map((s) => ({ ...s, type: 'sign' }));
      const simple = schema.simple.map((s) => ({ ...s, type: 'simple' }));
      const slide = schema.slide.map((s) => ({ ...s, type: 'slide' }));
      const text = schema.text.map((t) => ({ ...t, type: 'text' }));
      const fields = choice.concat(sign, simple, slide, text);

      fields.sort((a, b) => a.order - b.order);
      const values = fields.map((f) => createFieldResponse(f));

      setForm({ pages: fields });
      setInputsState(values);
    }
    fetchData();
  }, []);

  if (form === null) { return (<LoadingView />); }
  if (finished) { return (<EndView />); }

  const page = form.pages[currentPage - 1];
  const input = inputsState[currentPage - 1];
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
          input={input}
          setInput={setInput}
        />
      )}
      {page.type === 'sign' && (
        <SignView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
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
      {page.type === 'slide' && (
        <SliderView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          minValue={page.minValue}
          maxValue={page.maxValue}
          step={page.step}
          input={input}
          setInput={setInput}
        />
      )}
      {page.type === 'text' && (
        <TextView
          message={page.message}
          currentPage={currentPage}
          totalPages={form.pages.length}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          isMultiline={page.isMultiline}
          input={input}
          setInput={setInput}
        />
      )}
    </div>
  );
};

export default FormView;
