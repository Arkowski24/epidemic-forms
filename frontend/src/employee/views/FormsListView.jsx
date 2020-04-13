import React, { useEffect, useState } from 'react';
import {
  Button, Container, Dropdown, Form, Row, Table,
} from 'react-bootstrap';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import { useHistory } from 'react-router-dom';
import { FaTrash } from 'react-icons/fa';

import authService from '../services/AuthService';
import employeeService from '../services/EmployeeService';
import formService from '../services/FormService';
import schemaService from '../services/SchemaService';
import deviceStreamService from '../services/DeviceStreamService';
import formStreamService from '../services/FormsStreamService';

import LoadingView from '../../common/views/utility/LoadingView';

const Header = ({ setVisible, employee, handleLogout }) => {
  const history = useHistory();
  const handleAdminClick = (e) => {
    e.preventDefault();
    history.push('/employee/admin');
  };

  return (
    <>
      <Row className="w-100 m-1 p-1 border-bottom">
        <Dropdown>
          <Dropdown.Toggle variant="success" id="dropdown-basic">
            {`${employee.fullName}`}
          </Dropdown.Toggle>

          <Dropdown.Menu>
            {employee.role === 'ADMIN' && <Dropdown.Item onClick={handleAdminClick}>Użytkownicy</Dropdown.Item>}
            <Dropdown.Item onClick={handleLogout}>Wyloguj się</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Row>
      <Row className="w-100 m-1 p-1 border-bottom">
        <Col>
          <h1>Lista formularzy</h1>
        </Col>
        <Col md="auto">
          <Button
            variant="primary"
            type="button"
            onClick={(e) => {
              e.preventDefault();
              setVisible(true);
            }}
          >
            Stwórz nowy
          </Button>
        </Col>
      </Row>
    </>
  );
};

const FormsTable = ({ forms, setForms }) => {
  const history = useHistory();

  const headers = ['#', 'Nazwa formularza', 'Schemat', 'Stworzony przez', 'Kod jednorazowy', '']
    .map((h) => <th key={h}>{h}</th>);

  const deleteForm = async (formId) => {
    await formService.deleteForm(formId);
    deviceStreamService.sendCancelForm(formId);
    const newForms = forms.filter((f) => f.id !== formId);
    setForms(newForms);
  };

  const moveToForm = (formId) => {
    history.push(`/employee/forms/${formId}`);
  };

  const buildFormRow = (form, index) => (
    <tr key={form.id} onClick={() => moveToForm(form.id)}>
      <td>{index}</td>
      <td>{form.formName}</td>
      <td>{form.schema.name}</td>
      <td>{form.createdBy.fullName}</td>
      <td>{form.patient.id}</td>
      <td style={{ width: '5%' }}>
        <Button
          type="button"
          onClick={(e) => { e.preventDefault(); e.stopPropagation(); deleteForm(form.id); }}
        >
          <FaTrash />
        </Button>
      </td>
    </tr>
  );
  const patientForms = forms
    .filter((f) => f.status !== 'CLOSED')
    .map((f, i) => buildFormRow(f, i));

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

const NewFormModal = ({
  visible, setVisible,
  schemas,
  devices,
  createForm,
}) => {
  const [formName, setFormName] = useState('');
  const [schemaId, setSchemaId] = useState('');
  const [deviceId, setDeviceId] = useState('');

  const handleClose = () => setVisible(false);
  const schemaOptions = schemas.map((s) => <option value={s.id} key={s.id}>{s.name}</option>);
  const deviceOptions = devices.map((s) => <option value={s.id} key={s.id}>{s.fullName}</option>);

  const createNewForm = (event) => {
    event.preventDefault();
    let formSchemaId = schemaId;

    if (formSchemaId === '') {
      if (schemas.length > 0) formSchemaId = schemas[0].id;
      else return;
    }

    createForm(Number(formSchemaId), formName, Number(deviceId));
    handleClose();
  };

  return (
    <Modal show={visible} onHide={handleClose}>
      <Form onSubmit={createNewForm}>
        <Modal.Header closeButton>
          <Modal.Title>Utwórz nowy formularz</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="inputFormName">
            <Form.Label>Nazwa formularza</Form.Label>
            <Form.Control
              type="text"
              placeholder="Mój formularz"
              value={formName}
              onChange={(e) => setFormName(e.target.value)}
            />
          </Form.Group>
          <Form.Group controlId="inputFormSchema">
            <Form.Label>Schemat</Form.Label>
            <Form.Control
              as="select"
              value={schemaId}
              onChange={(e) => setSchemaId(e.target.value)}
            >
              {schemaOptions}
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="inputFormDevice">
            <Form.Label>Urządzenie</Form.Label>
            <Form.Control
              as="select"
              value={deviceId}
              onChange={(e) => setDeviceId(e.target.value)}
            >
              {deviceOptions}
              <option value={-1}>Urządzenie pacjenta</option>
            </Form.Control>
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" type="button" onClick={handleClose}>
            Zamknij
          </Button>
          <Button variant="primary" type="submit">
            Stwórz
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

const FormsListView = () => {
  const [credentials, setCredentials] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [forms, setForms] = useState([]);
  const [schemas, setSchemas] = useState([]);
  const [devices, setDevices] = useState([]);
  const history = useHistory();

  const createForm = async (schemaId, formName, deviceId) => {
    const form = await formService.createForm(schemaId, formName);

    if (deviceId !== -1) { deviceStreamService.sendNewForm(deviceId, form.patient.id); }
    const newForms = forms.concat(form);
    setForms(newForms);
    history.push(`/employee/forms/${form.id}`);
  };

  const handleLogout = (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    history.push('/employee/login');
    setCredentials(null);
  };

  useEffect(() => {
    deviceStreamService.subscribe(history);
  }, [history]);

  useEffect(() => {
    formStreamService.disconnect();
  }, []);

  useEffect(() => {
    const fetchToken = async () => {
      if (credentials !== null) return;
      const newToken = localStorage.getItem('token');
      if (!newToken) history.push('/employee/login');

      authService.me(newToken)
        .then((employee) => {
          formService.setToken(newToken);
          schemaService.setToken(newToken);
          employeeService.setToken(newToken);
          setCredentials({ employee, token: newToken });
        })
        .catch(() => {
          localStorage.removeItem('token');
          history.push('/employee/login');
        });
    };

    const fetchData = async () => {
      if (credentials === null) return;
      const formsResponse = await formService.getForms();
      setForms(formsResponse);

      const schemaResponse = await schemaService.getSchemas();
      setSchemas(schemaResponse);


      const devicesResponse = await employeeService.getEmployees();
      setDevices(devicesResponse.filter((e) => e.role === 'DEVICE'));
    };

    fetchToken()
      .then(() => fetchData());
  }, [history, credentials]);

  if (credentials === null) { return (<LoadingView />); }

  return (
    <Container>
      <Header
        setVisible={setModalVisible}
        employee={credentials.employee}
        handleLogout={handleLogout}
      />
      <FormsTable forms={forms} setForms={setForms} />
      <NewFormModal
        visible={modalVisible}
        setVisible={setModalVisible}
        schemas={schemas}
        devices={devices}
        createForm={createForm}
      />
    </Container>
  );
};

export default FormsListView;
