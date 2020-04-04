import React, { useEffect, useState } from 'react';

import { Button, Container, Row } from 'react-bootstrap';
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

  const createField = (fieldSchema, index, prevPage, nextPage) => {
    const input = form.state[index].value;
    const { multiPage } = form;

    const totalPages = pageIndexMapping.length;
    const disabled = form.schema[index].fieldType === 'BLOCKED';
    const setInput = (newInput) => { if (!disabled) formStreamService.sendInput(newInput, index); };

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isInline={fieldSchema.inline}
          choices={fieldSchema.choices}
          isMultiChoice={fieldSchema.multiChoice}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
          isMultipage={multiPage}
        />
      );
    }

    if (fieldSchema.type === 'derived') {
      return (
        <DerivedView
          derivedType={fieldSchema.derivedType}
          titles={fieldSchema.titles}
          descriptions={fieldSchema.descriptions}
          isInline={fieldSchema.inline}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
          isMultipage={multiPage}
        />
      );
    }

    if (fieldSchema.type === 'slider') {
      return (
        <SliderView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isInline={fieldSchema.inline}
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
          isMultipage={multiPage}
        />
      );
    }

    if (fieldSchema.type === 'text') {
      return (
        <TextView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isMultiline={fieldSchema.multiline}
          isInline={fieldSchema.inline}
          currentPage={currentPage}
          totalPages={totalPages}
          onClickPrev={prevPage}
          onClickNext={nextPage}
          input={input}
          setInput={setInput}
          disabled={disabled}
          isMultipage={multiPage}
        />
      );
    }

    return (
      <SimpleView
        title={fieldSchema.title}
        description={fieldSchema.description}
        isInline={fieldSchema.inline}
        currentPage={currentPage}
        totalPages={totalPages}
        onClickPrev={prevPage}
        onClickNext={nextPage}
        input={input}
        setInput={setInput}
        disabled={disabled}
        isMultipage={multiPage}
      />
    );
  };


  const buildFieldsSinglePage = () => {
    const header = (
      <Row>
        <div className="w-100 m-2 p-1 border-bottom">
          <h1>Patient Form</h1>
          <p>This form is filled by patient.</p>
        </div>
      </Row>
    );

    const fields = form.schema
      // eslint-disable-next-line react/no-array-index-key
      .map((s, i) => (<Row key={i}>{createField(s, i, () => {}, () => {})}</Row>));

    const footer = (
      <Row>
        <div className="w-100 m-2 p-1 border-top">
          <Button
            className="btn float-right"
            type="submit"
            onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
          >
            Send
          </Button>
        </div>
      </Row>
    );

    return (
      <Container>
        {header}
        {fields}
        {footer}
      </Container>
    );
  };

  const buildFieldsMultiPage = () => {
    const { index } = pageIndexMapping[currentPage - 1];
    const fieldSchema = form.schema[index];

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

    return createField(fieldSchema, index, prevPage, nextPage);
  };

  return (
    <>
      {form.multiPage ? buildFieldsMultiPage() : buildFieldsSinglePage()}
    </>
  );
};

export default FormView;
