import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Container, Col, Dropdown, Form, Row, Table, Modal,
} from 'react-bootstrap';
import { FaTrash } from 'react-icons/fa';
import { LoadingView } from '../../common/views';

import authService from '../../common/services/AuthService';
import formService from '../../common/services/FormService';
import formStreamService from '../../common/services/FormsStreamService';
import deviceStreamService from '../services/DeviceStreamService';
import employeeService from '../services/EmployeeService';
import schemaService from '../services/SchemaService';

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

const FormsTable = ({ forms, deleteForm }) => {
  const history = useHistory();

  const headers = ['#', 'Nazwa formularza', 'Schemat', 'Stworzony przez', 'Kod jednorazowy', '']
    .map((h) => <th key={h}>{h}</th>);

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
          onClick={(e) => {
            e.preventDefault();
            e.stopPropagation();
            deleteForm(form.id);
          }}
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
  const extractInitialForm = () => {
    const rawCreationSettings = localStorage.getItem('form-creation-settings');
    let creationSettings = rawCreationSettings ? JSON.parse(rawCreationSettings) : { formName: '', schemaId: null, deviceId: null };

    const isOwnDevice = creationSettings.deviceId === '-1';
    const isCurrentSchema = schemas.filter((s) => s.id === Number(creationSettings.schemaId)).length > 0;
    const isCurrentDevice = devices.filter((d) => d.id === Number(creationSettings.deviceId)).length > 0;

    if (!isCurrentSchema) { creationSettings = { ...creationSettings, schemaId: null }; }
    if (!isOwnDevice && !isCurrentDevice) { creationSettings = { ...creationSettings, deviceId: null }; }
    return creationSettings;
  };
  const creationSettings = extractInitialForm();

  const [formName, setFormName] = useState(creationSettings.formName);
  const [schemaId, setSchemaId] = useState(creationSettings.schemaId);
  const [deviceId, setDeviceId] = useState(creationSettings.deviceId);

  const handleClose = () => setVisible(false);
  const schemaOptions = schemas.map((s) => <option value={s.id} key={s.id}>{s.name}</option>);
  const deviceOptions = devices.map((s) => <option value={s.id} key={s.id}>{s.fullName}</option>);

  const createNewForm = (event) => {
    event.preventDefault();
    let formSchemaId = schemaId;
    let formDeviceId = deviceId;

    if (!formSchemaId) {
      if (schemas.length > 0) formSchemaId = schemas[0].id;
      else return;
    }
    if (!formDeviceId) {
      if (devices.length > 0) formDeviceId = devices[0].id;
      else return;
    }

    createForm(Number(formSchemaId), formName, Number(formDeviceId));
    localStorage.setItem('form-creation-settings', JSON.stringify({ formName, schemaId, deviceId }));
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
  const [modalVisible, setModalVisible] = useState(false);
  const [formsData, setFormsData] = useState(null);

  const history = useHistory();

  const rawStaffCredentials = localStorage.getItem('staff-credentials');
  const staffCredentials = rawStaffCredentials ? JSON.parse(rawStaffCredentials) : null;

  useEffect(() => {
    deviceStreamService.subscribe(history);
  }, [history]);

  useEffect(() => {
    formStreamService.disconnect();
  }, []);

  useEffect(() => {
    const setupServices = () => {
      const newToken = staffCredentials.token;
      formService.setToken(newToken);
      schemaService.setToken(newToken);
      employeeService.setToken(newToken);
    };

    const fetchData = async () => {
      Promise.all([
        formService.getForms(),
        schemaService.getSchemas(),
        employeeService.getDevices(),
      ]).then(
        (res) => setFormsData({
          forms: res[0],
          schemas: res[1],
          devices: res[2],
        }),
      );
    };

    const fetchTokenAndData = () => {
      if (!staffCredentials) {
        history.push('/employee/login');
        return;
      }
      if (staffCredentials.employee.role === 'DEVICE') {
        history.push('/');
        return;
      }

      authService.meEmployee(staffCredentials.token)
        .catch(() => {
          localStorage.removeItem('staff-credentials');
          history.push('/employee/login');
        })
        .then(() => setupServices())
        .then(() => fetchData());
    };

    if (!formsData) { fetchTokenAndData(); }
  }, [history, staffCredentials, formsData]);

  if (formsData === null) { return (<LoadingView />); }

  const createForm = async (schemaId, formName, deviceId) => {
    const form = await formService.createForm(schemaId, formName);

    if (deviceId !== -1) { deviceStreamService.sendNewForm(deviceId, form.patient.id); }
    const newForms = formsData.forms.concat(form);
    setFormsData({ ...formsData, forms: newForms });
    history.push(`/employee/forms/${form.id}`);
  };

  const deleteForm = async (formId) => {
    await formService.deleteForm(formId);
    await deviceStreamService.sendCancelForm(formId);
    const newForms = formsData.forms.filter((f) => f.id !== formId);
    setFormsData({ ...formsData, forms: newForms });
  };

  const handleLogout = (e) => {
    e.preventDefault();
    localStorage.removeItem('staff-credentials');
    history.push('/employee/login');
  };

  return (
    <Container>
      <Header
        setVisible={setModalVisible}
        employee={staffCredentials.employee}
        handleLogout={handleLogout}
      />
      <FormsTable forms={formsData.forms} deleteForm={deleteForm} />
      <NewFormModal
        visible={modalVisible}
        setVisible={setModalVisible}
        schemas={formsData.schemas}
        devices={formsData.devices}
        createForm={createForm}
      />
    </Container>
  );
};

export default FormsListView;
