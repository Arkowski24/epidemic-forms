import React, { useEffect, useState } from 'react';

import { Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import formsService from '../services/FormsService';

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
  const [inputsState, setInputsState] = useState([{}]);
  const [finished, setFinished] = useState(null);
  const [token, setToken] = useState(null);

  const sendFormResponse = async () => {
    await formsService.postResponse(inputsState);
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
      const rawForm = await formsService.getForm(1);

      const formFinished = rawForm.finished;
      setFinished(formFinished);
      if (formFinished) return;

      const { schema } = rawForm;
      const choice = schema.choice.map((c) => ({ ...c, type: 'choice' }));
      const sign = schema.sign.map((s) => ({ ...s, type: 'sign' }));
      const simple = schema.simple.map((s) => ({ ...s, type: 'simple' }));
      const slider = schema.slider.map((s) => ({ ...s, type: 'slider' }));
      const text = schema.text.map((t) => ({ ...t, type: 'text' }));
      const fields = choice.concat(sign, simple, slider, text);

      fields.sort((a, b) => a.order - b.order);
      const values = fields.map((f) => createFieldResponse(f));

      setForm({ schema: fields });
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

  const fields = form.schema
    .map((s, i) => createField(s, i));

  return (
    <Container>
      {fields}
    </Container>
  );
};

export default FormView;
