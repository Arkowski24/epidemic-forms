import React, { useEffect, useState } from 'react';

import { Button, Container } from 'react-bootstrap';
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

import SignView from './sign/SignView';

const FormView = () => {
  const [form, setForm] = useState(null);
  const { token } = useParams();

  const sendFormResponse = () => {
    formStreamService.sendMove('ACCEPTED');
  };

  const sendSign = (sign) => {
    formService.createSign(form.id, sign)
      .then(() => formStreamService.sendMove('CLOSED'));
  };

  useEffect(() => {
    const setNewForm = (newForm) => setForm(newForm);

    formStreamService.setToken(token);
    formStreamService.subscribe(setNewForm);
  }, [token]);

  if (form === null) { return (<LoadingView />); }
  if (form.status === 'ACCEPTED') { return (<LoadingView message="Waiting for patient to sign." />); }
  if (form.status === 'SIGNED') { return <SignView title="Pendix" description="Appendix" sendSign={sendSign} />; }
  if (form.status === 'CLOSED') { return (<EndView />); }

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
          onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
          disabled={form.status !== 'FILLED'}
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
