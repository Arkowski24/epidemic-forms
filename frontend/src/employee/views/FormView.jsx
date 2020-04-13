import React, { useEffect, useRef, useState } from 'react';
import { useParams, useHistory } from 'react-router-dom';

import {
  Button, Col, Container, Modal, Spinner, Row,
} from 'react-bootstrap';
import { FaTrash } from 'react-icons/fa';
import {
  ChoiceView,
  DerivedView,
  SimpleView,
  SliderView,
  TextView,
  SignatureView,
  LoadingView,
} from '../../common/views';
import EndView from './EndView';

import authService from '../../common/services/AuthService';
import formService from '../../common/services/FormService';
import formStreamService from '../../common/services/FormsStreamService';
import dataValidator from '../../common/helpers/DataValidator';
import deviceStreamService from '../services/DeviceStreamService';


const FormView = () => {
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [form, setForm] = useState(null);
  const [token, setToken] = useState(null);
  const formId = Number(useParams().formId);
  const history = useHistory();
  const signatureViewRef = useRef();

  const sendFormResponse = () => {
    formStreamService.sendMove('ACCEPTED');
  };

  const sendSignature = (signature) => {
    formService.createSignatureEmployee(form.id, signature)
      .then(() => formStreamService.sendMove('CLOSED'));
  };

  useEffect(() => {
    deviceStreamService.subscribe(history);
  }, [history]);

  useEffect(() => {
    if (token !== null) {
      formService.getForm(formId)
        .catch(() => history.push('/employee/'));
    }
  }, [token, history, formId]);

  useEffect(
    () => {
      if (signatureViewRef.current && form.status === 'SIGNED') {
        window.scrollTo({
          behavior: 'smooth',
          top: signatureViewRef.current.offsetTop,
        });
      }
    },
    [form],
  );

  useEffect(() => {
    const fetchTokenAndData = async () => {
      if (token !== null) return;
      const newToken = localStorage.getItem('token');
      if (!newToken) history.push('/employee/login');

      try {
        await authService.meEmployee(newToken);

        formService.setToken(newToken);
        formStreamService.setCredentials({ token: newToken, formId });
        const setNewForm = (newForm) => setForm(newForm);
        formStreamService.subscribe(setNewForm);
        setToken(newToken);
      } catch (e) {
        localStorage.removeItem('token');
        history.push('/employee/login');
      }
    };
    fetchTokenAndData();
  }, [formId, token, history]);

  if (form === null || token === null) { return (<LoadingView />); }
  if (form.status === 'CLOSED') { return (<EndView history={history} />); }

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

  const createField = (fieldSchema, index) => {
    const input = form.state[index].value;
    const setInput = (newInput) => formStreamService.sendInput(newInput, index, setForm);
    const blocked = !(form.status === 'NEW' || form.status === 'FILLED');
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
          highlighted={isInvalid}
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
          highlighted={isInvalid}
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
          highlighted={isInvalid}
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
          highlighted={isInvalid}
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

  const deleteForm = async (event) => {
    event.preventDefault();
    await deviceStreamService.sendCancelForm(formId);
    await formService.deleteForm(formId);
    history.push('/employee/');
  };

  const handleCloseModal = () => setShowDeleteModal(false);
  const deleteFormModal = (
    <Modal show={showDeleteModal} onHide={handleCloseModal}>
      <Modal.Body>Czy na pewno chcesz usunąć formularz?</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleCloseModal}>
          Anuluj
        </Button>
        <Button variant="danger" onClick={deleteForm}>
          Usuń formularz
        </Button>
      </Modal.Footer>
    </Modal>
  );

  const header = (
    <div className="w-100 ml-2 mr-2 p-1 border-bottom">
      <Row>
        <Col>
          <div>
            <h4>{form.formName}</h4>
            {`Kod jednorazowy: ${form.patient.id}`}
          </div>
        </Col>
        <Col sm="auto">
          <Button type="button" variant="danger" onClick={(e) => { e.preventDefault(); setShowDeleteModal(true); }}>
            <FaTrash />
          </Button>
        </Col>
      </Row>
    </div>
  );

  const fields = form.schema
    // eslint-disable-next-line react/no-array-index-key
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
      {form.status === 'NEW' ? ' Oczekiwanie na pacjenta...' : ' Oczekiwanie na podpis pacjenta...'}
    </>
  );

  const footer = (
    <Row>
      <div className="w-100 m-2 p-1 border-top">
        <Button
          className="w-100"
          variant="success"
          type="submit"
          onClick={(e) => { e.preventDefault(); sendFormResponse(); }}
          disabled={form.status !== 'FILLED'}
        >
          { form.status === 'NEW' || form.status === 'ACCEPTED' ? spinner : 'Akceptuj'}
        </Button>
      </div>
    </Row>
  );

  const signatureField = (
    <Row>
      <div className="w-100 mt-1 ml-1 p-1 border rounded" ref={signatureViewRef}>
        <SignatureView
          title={form.employeeSignature.title}
          description={form.employeeSignature.description}
          sendSignature={sendSignature}
        />
      </div>
    </Row>
  );

  return (
    <Container>
      {deleteFormModal}
      {header}
      {fields}
      {form.status !== 'SIGNED' && footer}
      {form.status === 'SIGNED' && signatureField}
    </Container>
  );
};

export default FormView;
