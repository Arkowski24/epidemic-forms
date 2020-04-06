import React, { useEffect, useRef, useState } from 'react';

import {
  Button, Container, Row, Spinner,
} from 'react-bootstrap';
import { useHistory } from 'react-router-dom';

import authService from '../services/AuthService';
import deviceStreamService from '../services/DeviceStreamService';
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
  const signatureViewRef = useRef();
  const history = useHistory();

  const sendFormResponse = () => {
    formStreamService.sendMove('FILLED');
  };

  const sendSignature = (signature) => {
    formService.createSignature(form.id, signature)
      .then(() => formStreamService.sendMove('SIGNED'));
  };

  useEffect(() => {
    const handleSendForm = async (pinCode) => {
      const newCredentials = await authService.login(pinCode);
      localStorage.setItem('credentials', JSON.stringify(newCredentials));
      setCredentials(newCredentials);
    };

    const handleCancelForm = async () => {
      localStorage.removeItem('credentials');
      setCredentials(null);
      setForm(null);
      window.location.reload();
    };

    const subscribeForms = async () => {
      const deviceToken = localStorage.getItem('device-token');
      if (!deviceToken) return;
      try {
        await authService.meDevice(deviceToken);
        deviceStreamService.subscribe(deviceToken, handleSendForm, handleCancelForm);
      } catch (e) {
        localStorage.removeItem('device-token');
      }
    };
    subscribeForms();
  }, [setCredentials, setForm, form, credentials]);

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
    if (credentials === null) { deviceStreamService.setFormId(null); return; }
    const setNewForm = (newForm) => setForm(newForm);

    deviceStreamService.setFormId(credentials.formId);
    formStreamService.setCredentials(credentials);
    formService.setCredentials(credentials);
    formStreamService.subscribe(setNewForm);
  }, [credentials]);

  useEffect(() => {
    if (!form) return;
    if (form.status === 'SIGNED' || form.status === 'CLOSED') {
      localStorage.removeItem('credentials');
      setForm(null);
      setCredentials(null);
      history.push('/thanks');
    }
  }, [form]);

  const isContinuous = localStorage.getItem('device-token') !== null;
  if (credentials === null && isContinuous) { return (<LoadingView message="Oczekiwanie na rozpoczęcie." />); }
  if (credentials === null) { return (<LoginView setCredentials={setCredentials} />); }
  if (form === null) { return (<LoadingView />); }
  if (form.status === 'SIGNED' || form.status === 'CLOSED') { return (<EndView setForm={setForm} setCredentials={setCredentials} />); }

  const pageIndexMapping = form.schema
    .map((f, i) => ({ type: f.fieldType, index: i }))
    .filter((r) => r.type !== 'HIDDEN');

  const createField = (fieldSchema, pageIndex) => {
    const { index } = pageIndexMapping[pageIndex];
    const input = form.state[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index);
    const blocked = fieldSchema.fieldType === 'BLOCKED' || !(form.status === 'NEW');

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
    const header = (
      <Row>
        <div className="w-100 m-2 border-bottom" />
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
            type="submit"
            onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
            disabled={form.status !== 'NEW'}
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
