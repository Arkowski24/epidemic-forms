import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  Button, Container, Form, Row, Table, Col, Modal,
} from 'react-bootstrap';
import { FaTrash, FaArrowLeft } from 'react-icons/fa';
import { LoadingView } from '../../common/views';

import authService from '../../common/services/AuthService';
import employeeService from '../services/EmployeeService';


const CreateEmployeeModal = ({
  createModalVisible, setCreateModalVisible,
  createEmployee,
}) => {
  const [username, setUsername] = useState('');
  const [fullName, setFullName] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('EMPLOYEE');

  const [touched, setTouched] = useState(false);
  const isValid = username.length > 0 && password.length > 0;

  const handleClosedCase = (e) => {
    if (e) { e.preventDefault(); }
    setCreateModalVisible(false);
  };
  const handleConfirmedCase = (e) => {
    if (e) { e.preventDefault(); }
    if (!isValid) { setTouched(true); return; }
    createEmployee(username, fullName, password, role);
    setCreateModalVisible(false);
  };

  return (
    <Modal show={createModalVisible} onHide={handleClosedCase}>
      <Form onSubmit={handleConfirmedCase}>
        <Modal.Header closeButton>
          <Modal.Title>Utwórz nowego użytkownika</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="inputFormUsername">
            <Form.Label>Login</Form.Label>
            <Form.Control
              type="text"
              placeholder="user1234"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              isInvalid={touched && username.length === 0}
            />
          </Form.Group>
          <Form.Group controlId="inputFormFullName">
            <Form.Label>Nazwa</Form.Label>
            <Form.Control
              type="text"
              placeholder="Jan Kowalski"
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
            />
          </Form.Group>
          <Form.Group controlId="inputFormPassword">
            <Form.Label>Hasło</Form.Label>
            <Form.Control
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              isInvalid={touched && password.length === 0}
            />
          </Form.Group>
          <Form.Group controlId="inputFormRole">
            <Form.Label>Rola</Form.Label>
            <Form.Control
              as="select"
              value={role}
              onChange={(e) => setRole(e.target.value)}
            >
              <option value="EMPLOYEE">Użytkownik</option>
              <option value="ADMIN">Administrator</option>
              <option value="DEVICE">Urządzenie</option>
            </Form.Control>
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" type="button" onClick={handleClosedCase}>
            Anuluj
          </Button>
          <Button variant="primary" type="submit" disabled={touched && !isValid}>
            Stwórz
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

const Header = ({ createEmployee }) => {
  const [createModalVisible, setCreateModalVisible] = useState(false);
  const history = useHistory();

  const createEmployeeModal = (
    <CreateEmployeeModal
      createModalVisible={createModalVisible}
      setCreateModalVisible={setCreateModalVisible}
      createEmployee={createEmployee}
    />
  );

  return (
    <>
      {createEmployeeModal}
      <Row className="w-100 m-1 pb-1 pt-1 border-bottom">
        <Col xs="auto">
          <Button
            type="button"
            onClick={(e) => {
              e.preventDefault();
              history.push('/employee/');
            }}
          >
            <FaArrowLeft />
          </Button>
        </Col>
        <Col>
          <h1>Użytkownicy</h1>
        </Col>
        <Col xs="auto">
          <Button
            variant="primary"
            type="button"
            onClick={(e) => {
              e.preventDefault();
              setCreateModalVisible(true);
            }}
          >
            Stwórz
          </Button>
        </Col>
      </Row>
    </>
  );
};


const DeleteEmployeeModal = ({
  deleteModalVisible, setDeleteModalVisible,
  selectedEmployee,
  deleteEmployee,
}) => {
  if (!selectedEmployee) return null;
  const handleClosedCase = (e) => {
    e.preventDefault();
    setDeleteModalVisible(false);
  };
  const handleConfirmedCase = (e) => {
    e.preventDefault();
    deleteEmployee(selectedEmployee.id);
    setDeleteModalVisible(false);
  };
  return (
    <Modal show={deleteModalVisible} onHide={handleClosedCase}>
      <Modal.Body>{`Czy na pewno chcesz usunąć użytkownika ${selectedEmployee.fullName}?`}</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClosedCase}>
          Anuluj
        </Button>
        <Button
          variant="danger"
          onClick={handleConfirmedCase}
        >
          Usuń użytkownika
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

const EmployeeTable = ({
  currentUser, employees,
  deleteEmployee,
}) => {
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [deleteModalVisible, setDeleteModalVisible] = useState(false);

  const headers = ['#', 'ID', 'Login', 'Nazwa', 'Rola', '']
    .map((h) => <th key={h}>{h}</th>);

  const translateRole = (role) => {
    if (role === 'ADMIN') { return 'Administrator'; }
    if (role === 'EMPLOYEE') { return 'Użytkownik'; }
    if (role === 'DEVICE') { return 'Urządzenie'; }
    return '';
  };

  const buildEmployeeRow = (employee, index) => (
    <tr key={employee.id}>
      <td>{index}</td>
      <td>{employee.id}</td>
      <td>{employee.username}</td>
      <td>{employee.fullName}</td>
      <td>{translateRole(employee.role)}</td>
      <td style={{ width: '5%' }}>
        <Button
          type="button"
          variant="danger"
          onClick={(e) => {
            e.preventDefault();
            setSelectedEmployee(employee);
            setDeleteModalVisible(true);
          }}
          disabled={currentUser.id === employee.id}
        >
          <FaTrash />
        </Button>
      </td>
    </tr>
  );

  const patientForms = employees
    .map((e, i) => buildEmployeeRow(e, i));

  const deleteEmployeeModal = (
    <DeleteEmployeeModal
      deleteModalVisible={deleteModalVisible}
      setDeleteModalVisible={setDeleteModalVisible}
      selectedEmployee={selectedEmployee}
      deleteEmployee={deleteEmployee}
    />
  );

  return (
    <>
      {deleteEmployeeModal}
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
    </>
  );
};

const FormsListView = () => {
  const [credentials, setCredentials] = useState(null);
  const [employees, setEmployees] = useState([]);
  const history = useHistory();

  const createEmployee = (username, fullName, password, role) => {
    employeeService
      .createEmployee(username, fullName, password, role)
      .then((e) => employees.concat(e))
      .then((e) => setEmployees(e));
  };

  const deleteEmployee = (employeeId) => {
    employeeService
      .deleteEmployee(employeeId)
      .then(() => employees.filter((e) => e.id !== employeeId))
      .then((e) => setEmployees(e));
  };

  useEffect(() => {
    const fetchToken = async () => {
      if (credentials !== null) return;
      const newToken = localStorage.getItem('token');
      if (!newToken) history.push('/employee/login');

      authService.meEmployee(newToken)
        .then((employee) => {
          if (employee.role !== 'ADMIN') {
            history.push('/employee');
            return;
          }
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
      const response = await employeeService.getEmployees();
      setEmployees(response);
    };

    fetchToken()
      .then(() => fetchData());
  }, [history, credentials]);

  if (credentials === null) { return (<LoadingView />); }

  return (
    <Container>
      <Header createEmployee={createEmployee} />
      <EmployeeTable
        currentUser={credentials.employee}
        employees={employees}
        deleteEmployee={deleteEmployee}
      />
    </Container>
  );
};

export default FormsListView;
