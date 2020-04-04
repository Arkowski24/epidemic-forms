import React, { useEffect, useState } from 'react';

import formService from '../services/FormService';
import formStreamService from '../services/FormsStreamService';

import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from './utility/LoadingView';
import EndView from './utility/EndView';
import SliderView from './fields/SliderView';
import LoginView from './utility/LoginView';

import SignatureView from './signature/SignatureView';
import DerivedView from './fields/DerivedView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [credentials, setCredentials] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);

  const sendFormResponse = () => {
    formStreamService.sendMove('FILLED');
  };

  const sendSignature = (signature) => {
    formService.createSignature(form.id, signature)
      .then(() => formStreamService.sendMove('SIGNED'));
  };

  useEffect(() => {
    if (credentials === null) return;
    const setNewForm = (newForm) => setForm(newForm);

    formStreamService.setCredentials(credentials);
    formService.setCredentials(credentials);
    formStreamService.subscribe(setNewForm);
  }, [credentials]);

  if (credentials === null) { return (<LoginView setCredentials={setCredentials} />); }
  if (form === null) { return (<LoadingView />); }
  if (form.status === 'FILLED') { return (<LoadingView message="Waiting for employee to accept." />); }
  if (form.status === 'ACCEPTED') { return (<SignatureView title={form.patientSignature.title} description={form.patientSignature.description} sendSignature={sendSignature} />); }
  if (form.status === 'SIGNED') { return (<LoadingView message="Waiting for employee to sign." />); }
  if (form.status === 'CLOSED') { return (<EndView setForm={setForm} setCurrentPage={setCurrentPage} setCredentials={setCredentials} />); }

  const pageIndexMapping = form.schema
    .map((f, i) => ({ type: f.fieldType, index: i }))
    .filter((r) => r.type !== 'HIDDEN');

  const nextPage = (event) => {
    event.preventDefault();
    if (currentPage === pageIndexMapping.length) {
      sendFormResponse();
    } else {
      formStreamService.sendPageChange(currentPage + 1);
      setCurrentPage(currentPage + 1);
    }
  };

  const prevPage = (event) => {
    event.preventDefault();
    if (currentPage - 1 > 0) {
      formStreamService.sendPageChange(currentPage - 1);
      setCurrentPage(currentPage - 1);
    }
  };

  const createField = () => {
    const { index } = pageIndexMapping[currentPage - 1];
    const fieldSchema = form.schema[index];
    const input = form.state[index].value;

    const totalPages = pageIndexMapping.length;
    const disabled = form.schema[index].fieldType === 'BLOCKED';
    const setInput = (newInput) => { if (!disabled) formStreamService.sendInput(newInput, index); };

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          title={fieldSchema.title}
          description={fieldSchema.description}
          choices={fieldSchema.choices}
          isMultiChoice={fieldSchema.multiChoice}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
        />
      );
    }

    if (fieldSchema.type === 'derived') {
      return (
        <DerivedView
          derivedType={fieldSchema.derivedType}
          titles={fieldSchema.titles}
          descriptions={fieldSchema.descriptions}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
        />
      );
    }

    if (fieldSchema.type === 'slider') {
      return (
        <SliderView
          title={fieldSchema.title}
          description={fieldSchema.description}
          minValue={fieldSchema.minValue}
          maxValue={fieldSchema.maxValue}
          step={fieldSchema.step}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
        />
      );
    }

    if (fieldSchema.type === 'text') {
      return (
        <TextView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isMultiline={fieldSchema.multiline}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
        />
      );
    }

    return (
      <SimpleView
        title={fieldSchema.title}
        description={fieldSchema.description}
        currentPage={currentPage}
        totalPages={totalPages}
        onClickPrev={prevPage}
        onClickNext={nextPage}
        input={input}
        setInput={setInput}
        disabled={disabled}
      />
    );
  };

  return (
    <>
      {createField()}
    </>
  );
};

export default FormView;
