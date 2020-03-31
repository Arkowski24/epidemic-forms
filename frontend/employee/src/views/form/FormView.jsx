import React, { useEffect, useState } from 'react';

import { Button, Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { useParams, useHistory } from 'react-router-dom';

import formStreamService from '../../services/FormsStreamService';

import SignView from './fields/SignView';
import ChoiceView from './fields/ChoiceView';
import TextView from './fields/TextView';
import SimpleView from './fields/SimpleView';
import LoadingView from './utility/LoadingView';
import EndView from './utility/EndView';
import SliderView from './fields/SliderView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const [inputsState, setInputsState] = useState(null);
  const [finished, setFinished] = useState(null);

  const history = useHistory();
  const { token } = useParams();

  const sendFormResponse = async () => {
    setFinished(true);
  };

  useEffect(() => {
    const setNewForm = (newForm) => setForm(newForm);
    const setNewFormState = (newState) => setInputsState(newState);

    formStreamService.setToken(token);
    formStreamService.subscribe(setNewForm, setNewFormState);
  }, [token]);

  if (form === null || inputsState === null) { return (<LoadingView />); }
  if (finished) { return (<EndView />); }

  const createField = (fieldSchema, index) => {
    const input = inputsState[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index);

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          message={fieldSchema.description}
          choices={fieldSchema.choices}
          isMultiChoice={fieldSchema.isMultiChoice}
          input={input}
          setInput={setInput}
        />
      );
    }

    if (fieldSchema.type === 'sign') {
      return (
        <SignView
          message={fieldSchema.description}
          input={input}
          setInput={setInput}
        />
      );
    }

    if (fieldSchema.type === 'slider') {
      return (
        <SliderView
          message={fieldSchema.description}
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
          message={fieldSchema.description}
          isMultiline={fieldSchema.isMultiline}
          input={input}
          setInput={setInput}
        />
      );
    }

    return (
      <SimpleView message={fieldSchema.description} />
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

  const fields = form
    // eslint-disable-next-line react/no-array-index-key
    .map((s, i) => (<Row key={i}>{createField(s, i)}</Row>));

  const footer = (
    <Row>
      <div className="w-100 m-2 p-1 border-top">
        <Button
          className="btn float-right"
          type="submit"
          onClick={(e) => {
            e.preventDefault();
            sendFormResponse();
          }}
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
