import React, { useEffect, useState } from 'react';

import { Button, Container, Spinner } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { useParams } from 'react-router-dom';

import formService from '../../services/FormService';
import formStreamService from '../../services/FormsStreamService';

import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from './utility/LoadingView';
import EndView from './utility/EndView';
import SliderView from './fields/SliderView';

import SignatureView from './signature/SignatureView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [patientPage, setPatientPage] = useState(0);
  const { token } = useParams();

  const sendFormResponse = () => {
    formStreamService.sendMove('ACCEPTED');
  };

  const sendSignature = (signature) => {
    formService.createSignature(form.id, signature)
      .then(() => formStreamService.sendMove('CLOSED'));
  };

  useEffect(() => {
    const setNewForm = (newForm) => setForm(newForm);

    formStreamService.setToken(token);
    formStreamService.subscribe(setNewForm, setPatientPage);
  }, [token]);

  if (form === null) { return (<LoadingView />); }
  if (form.status === 'ACCEPTED') { return (<LoadingView message="Waiting for patient to sign." />); }
  if (form.status === 'SIGNED') { return (<SignatureView title={form.patientSignature.title} description={form.patientSignature.description} sendSignature={sendSignature} />); }
  if (form.status === 'CLOSED') { return (<EndView />); }

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
        <h1>Patient Form</h1>
        <p>This form is filled by patient.</p>
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
        {' Waiting for the patient...'}
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
            { form.status === 'NEW' ? spinner : 'Accept'}
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
