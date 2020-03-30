import React, { useEffect, useState } from 'react';

import { Button, Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';

import patientService from '../../services/PatientService';

import SignView from './fields/SignView';
import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from './utility/LoadingView';
import EndView from './utility/EndView';
import SliderView from './fields/SliderView';
import LoginView from './utility/LoginView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [inputsState, setInputsState] = useState(null);
  const [finished, setFinished] = useState(null);
  const [token, setToken] = useState(null);

  const sendFormResponse = async () => {
    await patientService.postResponse(inputsState);
    setFinished(true);
  };

  const createFieldResponse = (f) => {
    if (f.type === 'choice') return f.choices.map(() => false);
    if (f.type === 'text') return '';
    if (f.type === 'slider') return f.minValue;
    return null;
  };

  useEffect(() => {
    async function fetchData() {
      const rawForm = await schemaService.getSchema(1);

      const formFinished = rawForm.finished;
      setFinished(formFinished);
      if (formFinished) return;

      const { fields } = rawForm;
      const choice = fields.choice.map((c) => ({ ...c, type: 'choice' }));
      const sign = fields.sign.map((s) => ({ ...s, type: 'sign' }));
      const simple = fields.simple.map((s) => ({ ...s, type: 'simple' }));
      const slider = fields.slider.map((s) => ({ ...s, type: 'slider' }));
      const text = fields.text.map((t) => ({ ...t, type: 'text' }));
      const allFields = choice.concat(sign, simple, slider, text);

      allFields.sort((a, b) => a.order - b.order);
      const values = allFields.map((f) => createFieldResponse(f));

      setForm({ fields: allFields });
      setInputsState(values);
    }
    fetchData();
  }, []);

  if (token === null) { return (<LoginView setToken={setToken} />); }
  if (form === null) { return (<LoadingView />); }
  if (finished) { return (<EndView />); }


  const createField = (fieldSchema, index) => {
    const input = inputsState[index];
    const setInput = (newInput) => {
      const newResponses = inputsState.slice();
      newResponses[index] = newInput;
      setInputsState(newResponses);
    };

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          message={fieldSchema.message}
          choices={fieldSchema.choices}
          isMultiple={fieldSchema.isMultiple}
          input={input}
          setInput={setInput}
        />
      );
    }

    if (fieldSchema.type === 'sign') {
      return (
        <SignView
          message={fieldSchema.message}
          input={input}
          setInput={setInput}
        />
      );
    }

    if (fieldSchema.type === 'slider') {
      return (
        <SliderView
          message={fieldSchema.message}
          minValue={fieldSchema.minValue}
          maxValue={fieldSchema.maxValue}
          step={fieldSchema.step}
          input={input}
          setInput={setInput}
        />
      );
    }

    if (fieldSchema.type === 'text') {
      return (
        <TextView
          message={fieldSchema.message}
          isMultiline={fieldSchema.isMultiline}
          input={input}
          setInput={setInput}
        />
      );
    }

    return (
      <SimpleView message={fieldSchema.message} />
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

  const fields = form.fields
    // eslint-disable-next-line react/no-array-index-key
    .map((s, i) => (<Row key={i}>{createField(s, i)}</Row>));

  const footer = (
    <Row>
      <div className="w-100 m-2 p-1 border-top">
        <Button
          className="btn float-right"
          type="submit"
          onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
        >
          Submit
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

export default FormView;
