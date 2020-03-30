import React, { useState } from 'react';
import {
  Button, Container, Form, Row, Table,
} from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';

const Header = ({ setVisible }) => (
  <Row className="w-100 m-1 p-1 border-bottom">
    <Col>
      <h1>List of filled forms</h1>
    </Col>
    <Col md="auto">
      <Button
        variant="primary"
        onClick={(e) => { e.preventDefault(); setVisible(true); }}
      >
        Create
      </Button>
    </Col>
  </Row>
);

const FormsTable = ({ forms }) => {
  const headers = ['#', 'Patient Name', 'Schema', 'Pin']
    .map((h) => <th key={h}>{h}</th>);

  const buildPatientRow = (patientForm) => (
    <tr key={patientForm.id}>
      <td>{patientForm.id}</td>
      <td>{patientForm.name}</td>
      <td>{patientForm.schema}</td>
      <td>{patientForm.pin}</td>
    </tr>
  );
  const patientForms = forms
    .map((p) => buildPatientRow(p));

  return (
    <Row className="w-100 m-1 p-1">
      <Table striped bordered hover>
        <thead>
          <tr>
            {headers}
          </tr>
        </thead>
        <tbody>
          {patientForms}
        </tbody>
      </Table>
    </Row>
  );
};

const NewFormModal = ({ visible, setVisible, schemas }) => {
  const handleClose = () => setVisible(false);
  const schemaOptions = schemas.map((s) => <option value={s.id}>{s.name}</option>);

  return (
    <>
      <Modal show={visible} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Create new patient form</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formPatientName">
              <Form.Label>Patient Name</Form.Label>
              <Form.Control type="email" placeholder="John Doe" />
            </Form.Group>
            <Form.Group controlId="forSchema">
              <Form.Label>Schema</Form.Label>
              <Form.Control as="select">
                {schemaOptions}
              </Form.Control>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleClose}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

const FormsList = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [forms, setForms] = useState([]);
  const [schemas, setSchemas] = useState([]);

  return (
    <Container>
      <Header setVisible={setModalVisible} />
      <FormsTable forms={forms} />
      <NewFormModal visible={modalVisible} setVisible={setModalVisible} schemas={schemas} />
    </Container>
  );
};

export default FormsList;
