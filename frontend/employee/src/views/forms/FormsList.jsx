import React, { useEffect, useState } from 'react';
import {
  Button, Container, Form, Row, Table,
} from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';

import patientService from '../../services/PatientService';
import schemaService from '../../services/SchemaService';

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

const FormsTable = ({ patients }) => {
  const headers = ['#', 'Patient Name', 'Schema', 'Pin']
    .map((h) => <th key={h}>{h}</th>);

  const buildPatientRow = (patient) => (
    <tr key={patient.id} onClick={() => console.log('xd')}>
      <td>{patient.id}</td>
      <td>{patient.name}</td>
      <td>{patient.schema.name}</td>
      <td>{patient.pin}</td>
    </tr>
  );
  const patientForms = patients
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
  const schemaOptions = schemas.map((s) => <option value={s.id} key={s.id}>{s.name}</option>);

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
  const [patients, setPatients] = useState([]);
  const [schemas, setSchemas] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const patientResponse = await patientService.getPatients();
      setPatients(patientResponse);

      const schemaResponse = await schemaService.getSchemas();
      setSchemas(schemaResponse);
    }
    fetchData();
  }, []);

  return (
    <Container>
      <Header setVisible={setModalVisible} />
      <FormsTable patients={patients} />
      <NewFormModal visible={modalVisible} setVisible={setModalVisible} schemas={schemas} />
    </Container>
  );
};

export default FormsList;
