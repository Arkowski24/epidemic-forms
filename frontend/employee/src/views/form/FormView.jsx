import React, { useEffect, useState } from 'react';

import { Button, Container } from 'react-bootstrap';
import Row from 'react-bootstrap/Row';
import { useParams } from 'react-router-dom';

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
  const [finished, setFinished] = useState(null);

  const { token } = useParams();

  const sendFormResponse = async () => {
    setFinished(true);
  };

  useEffect(() => {
    const setNewForm = (newForm) => setForm(newForm);

    formStreamService.setToken(token);
    formStreamService.subscribe(setNewForm);
  }, [token]);

  if (form === null) { return (<LoadingView />); }
  if (finished) { return (<EndView />); }

  const createField = (fieldSchema, index) => {
    const input = form.state[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index);

    if (fieldSchema.type === 'choice') {
      return (
        <ChoiceView
          title={fieldSchema.title}
          description={fieldSchema.description}
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
          title={fieldSchema.title}
          description={fieldSchema.description}
          input={input}
          setInput={setInput}
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
        />
      );
    }

    return (
      <SimpleView
        title={fieldSchema.title}
        description={fieldSchema.description}
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
