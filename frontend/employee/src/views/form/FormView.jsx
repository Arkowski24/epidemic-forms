import React, { useEffect, useState } from 'react';

import { Button, Container, Spinner } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { useParams, useHistory } from 'react-router-dom';

import formService from '../../services/FormService';
import formStreamService from '../../services/FormsStreamService';

import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from './utility/LoadingView';
import EndView from './utility/EndView';
import SliderView from './fields/SliderView';

import SignatureView from './signature/SignatureView';
import authService from '../../services/AuthService';
import DerivedView from './fields/DerivedView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [token, setToken] = useState(null);
  const [patientPage, setPatientPage] = useState(0);
  const { formId } = useParams();
  const history = useHistory();

  const sendFormResponse = () => {
    formStreamService.sendMove('ACCEPTED');
  };

  const sendSignature = (signature) => {
    formService.createSignature(form.id, signature)
      .then(() => formStreamService.sendMove('CLOSED'));
  };

  useEffect(() => {
    const fetchTokenAndData = async () => {
      if (token !== null) return;
      const newToken = localStorage.getItem('token');
      if (!newToken) history.push('/employee/login');

      try {
        await authService.me(newToken);

        formService.setToken(newToken);
        formStreamService.setCredentials({ token: newToken, formId });
        const setNewForm = (newForm) => setForm(newForm);
        formStreamService.subscribe(setNewForm, setPatientPage);
        setToken(newToken);
      } catch (e) {
        localStorage.removeItem('token');
        history.push('/employee/login');
      }
    };
    fetchTokenAndData();
  }, [formId, token, history]);

  if (form === null || token === null) { return (<LoadingView />); }
  if (form.status === 'ACCEPTED') { return (<LoadingView message="Oczekiwanie na podpis pacjenta." />); }
  if (form.status === 'SIGNED') { return (<SignatureView title={form.employeeSignature.title} description={form.employeeSignature.description} sendSignature={sendSignature} />); }
  if (form.status === 'CLOSED') { return (<EndView history={history} />); }

  const pageIndexMapping = form.schema
    .map((f, i) => ({ type: f.fieldType, index: i }))
    .filter((r) => r.type !== 'HIDDEN');

  const createField = (fieldSchema, index) => {
    const input = form.state[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index);
    const highlighted = patientPage && index === pageIndexMapping[patientPage - 1].index;

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          title={fieldSchema.title}
          description={fieldSchema.description}
          choices={fieldSchema.choices}
          isMultiChoice={fieldSchema.isMultiChoice}
          input={input}
          setInput={setInput}
          highlighted={highlighted}
        />
      );
    }

    if (fieldSchema.type === 'derived') {
      return (
        <DerivedView
          derivedType={fieldSchema.derivedType}
          titles={fieldSchema.titles}
          descriptions={fieldSchema.descriptions}
          input={input}
          setInput={setInput}
          highlighted={highlighted}
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
          input={input}
          setInput={setInput}
          highlighted={highlighted}
        />
      );
    }

    if (fieldSchema.type === 'text') {
      return (
        <TextView
          title={fieldSchema.title}
          description={fieldSchema.description}
          isMultiline={fieldSchema.isMultiline}
          input={input}
          setInput={setInput}
          highlighted={highlighted}
        />
      );
    }

    return (
      <SimpleView
        title={fieldSchema.title}
        description={fieldSchema.description}
        highlighted={highlighted}
      />
    );
  };

  const header = (
    <Row>
      <div className="w-100 m-2 p-1 border-bottom">
        <h1>{form.formName}</h1>
      </div>
    </Row>
  );

  const fields = form.schema
    // eslint-disable-next-line react/no-array-index-key
    .map((s, i) => (<Row key={i}>{createField(s, i)}</Row>));

  const footer = () => {
    const spinner = (
      <>
        <Spinner
          as="span"
          animation="border"
          size="sm"
          role="status"
          aria-hidden="true"
        />
        {' Oczekiwanie na pacjenta...'}
      </>
    );

    return (
      <Row>
        <div className="w-100 m-2 p-1 border-top">
          <Button
            className="btn float-right"
            type="submit"
            onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
            disabled={form.status !== 'FILLED'}
          >
            { form.status === 'NEW' ? spinner : 'Akceptuj'}
          </Button>
        </div>
      </Row>
    );
  };

  return (
    <Container>
      {header}
      {fields}
      {footer()}
    </Container>
  );
};

export default FormView;
