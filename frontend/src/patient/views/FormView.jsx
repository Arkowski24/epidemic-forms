import React, { useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Container, Row, Spinner,
} from 'react-bootstrap';
import {
  ChoiceView,
  DerivedView,
  SimpleView,
  SliderView,
  TextView,
  SignatureView,
  LoadingView,
} from '../../common/views';
import { HospitalIcon } from '../../common/public';
import EndView from './EndView';

import authService from '../../common/services/AuthService';
import formService from '../../common/services/FormService';
import formStreamService from '../../common/services/FormsStreamService';
import deviceStreamService from '../services/DeviceStreamService';
import dataValidator from '../../common/helpers/DataValidator';


const FormView = () => {
  const [form, setForm] = useState(null);
  const [formTouched, setFormTouched] = useState(false);
  const signatureViewRef = useRef();
  const history = useHistory();

  const sendFormResponse = () => {
    formStreamService.sendMove('FILLED');
  };

  const sendSignature = (signature) => {
    formService
      .createSignaturePatient(form.id, signature)
      .then(() => formStreamService.sendMove('SIGNED'));
  };

  useEffect(() => {
    deviceStreamService.subscribe(history);
  }, [history]);

  useEffect(() => {
    const rawCredentials = localStorage.getItem('credentials');
    if (rawCredentials === null) { history.push('/'); return; }
    const fetchData = async () => {
      try {
        const credentials = JSON.parse(rawCredentials);

        await authService.mePatient(credentials.token);
        formStreamService.setCredentials(credentials);
        formService.setToken(credentials.token);
        formStreamService.subscribe((f) => setForm(f));
      } catch (e) {
        localStorage.removeItem('credentials');
        history.push('/');
      }
    };
    fetchData();
  }, [history]);

  useEffect(() => {
    if (signatureViewRef.current && form.status === 'ACCEPTED') {
      window.scrollTo({
        behavior: 'smooth',
        top: signatureViewRef.current.offsetTop,
      });
    }
  },
  [form]);

  useEffect(() => {
    if (!form) return;
    if (form.status === 'SIGNED' || form.status === 'CLOSED') {
      localStorage.removeItem('credentials');
      setForm(null);
      history.push('/thanks');
    }
  }, [form, history]);

  if (form === null) { return (<LoadingView />); }
  if (form.status === 'SIGNED' || form.status === 'CLOSED') { return (<EndView setForm={setForm} />); }

  const isValidField = (fieldSchema, fieldIndex) => {
    if (fieldSchema.fieldType === 'HIDDEN') return true;
    const input = form.state[fieldIndex].value;
    const { required } = fieldSchema;

    if (fieldSchema.type === 'derived') {
      const { derivedType } = fieldSchema;
      return input
        .map((v, i) => !required[i] || dataValidator.validateDerivedField(v, i, derivedType))
        .filter((v) => !v)
        .length === 0;
    }

    if (!required) return true;
    if (fieldSchema.type === 'choice') { return dataValidator.validateChoiceField(input); }
    if (fieldSchema.type === 'slider') { return dataValidator.validateSliderField(input, fieldSchema.minValue); }
    if (fieldSchema.type === 'text') { return dataValidator.validateTextField(input); }
    return true;
  };

  const validFields = form.schema
    .map((f, i) => isValidField(f, i));

  const pageIndexMapping = form.schema
    .map((f, i) => ({ type: f.fieldType, index: i }))
    .filter((r) => r.type !== 'HIDDEN');

  const createField = (fieldSchema, pageIndex) => {
    const { index } = pageIndexMapping[pageIndex];
    const input = form.state[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index, setForm);
    const blocked = fieldSchema.fieldType === 'BLOCKED' || !(form.status === 'NEW');
    const isInvalid = !validFields[index];

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isInline={fieldSchema.inline}
          choices={fieldSchema.choices}
          isMultiChoice={fieldSchema.multiChoice}
          input={input}
          setInput={setInput}
          isBlocked={blocked}
          highlighted={formTouched && isInvalid}
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
          input={input}
          setInput={setInput}
          highlighted={formTouched && isInvalid}
          isBlocked={blocked}
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
          defaultValue={fieldSchema.defaultValue}
          input={input}
          setInput={setInput}
          isBlocked={blocked}
          highlighted={formTouched && isInvalid}
        />
      );
    }

    if (fieldSchema.type === 'text') {
      return (
        <TextView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isInline={fieldSchema.inline}
          isMultiline={fieldSchema.multiLine}
          input={input}
          setInput={setInput}
          isBlocked={blocked}
          highlighted={formTouched && isInvalid}
          isInvalid={isInvalid}
        />
      );
    }

    return (
      <SimpleView
        title={fieldSchema.title}
        description={fieldSchema.description}
        isInline={fieldSchema.inline}
      />
    );
  };

  const buildFieldsSinglePage = () => {
    const isNotValidInput = validFields
      .filter((v) => !v)
      .length > 0;

    const header = (
      <Row>
        <div className="w-100 m-1 p-1 border-bottom text-center">
          <HospitalIcon height="20px" />
          {' Krakowski Szpital Specjalistyczny im. Jana Pawła II'}
        </div>
      </Row>
    );

    const fields = form.schema
      // eslint-disable-next-line react/no-array-index-key
      .filter((r) => r.fieldType !== 'HIDDEN')
      .map((s, i) => (<Row key={i}>{createField(s, i)}</Row>));

    const spinner = (
      <>
        <Spinner
          as="span"
          animation="border"
          size="sm"
          role="status"
          aria-hidden="true"
        />
        {' Oczekiwanie na akceptację...'}
      </>
    );

    const footer = (
      <Row>
        <div className="w-100 m-2 p-1 border-top">
          <Button
            className="w-100"
            variant="success"
            type="submit"
            onClick={(e) => {
              e.preventDefault();
              setFormTouched(true);
              if (!isNotValidInput) sendFormResponse();
            }}
            disabled={form.status !== 'NEW' || (formTouched && isNotValidInput)}
          >
            { form.status === 'NEW' ? 'Prześlij' : spinner}
          </Button>
        </div>
      </Row>
    );

    const signatureField = (
      <Row>
        <div className="w-100 mt-1 ml-1 p-1 border rounded" ref={signatureViewRef}>
          <SignatureView
            title={form.patientSignature.title}
            description={form.patientSignature.description}
            sendSignature={sendSignature}
          />
        </div>
      </Row>
    );

    return (
      <Container>
        {header}
        {fields}
        {form.status !== 'ACCEPTED' && footer}
        {form.status === 'ACCEPTED' && signatureField}
      </Container>
    );
  };

  return (
    <>
      {buildFieldsSinglePage()}
    </>
  );
};

export default FormView;
