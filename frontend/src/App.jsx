import React from 'react';
import { Route, BrowserRouter, Switch } from 'react-router-dom';

import EmployeeAdminPanelView from './employee/views/AdminPanelView';
import EmployeeFormView from './employee/views/FormView';
import EmployeeFormsListView from './employee/views/FormsListView';
import EmployeeLoginView from './employee/views/LoginView';

import PatientFormView from './patient/views/FormView';
import PatientLoginView from './patient/views/LoginView';
import PatientLoginDeviceView from './patient/views/LoginDeviceView';
import PatientEndView from './patient/views/EndView';

const App = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/employee/forms/:formId">
        <EmployeeFormView />
      </Route>
      <Route path="/employee/admin">
        <EmployeeAdminPanelView />
      </Route>
      <Route path="/employee/login">
        <EmployeeLoginView />
      </Route>
      <Route path="/employee/">
        <EmployeeFormsListView />
      </Route>

      <Route path="/device">
        <PatientLoginDeviceView />
      </Route>
      <Route path="/thanks">
        <PatientEndView />
      </Route>
      <Route path="/form">
        <PatientFormView />
      </Route>
      <Route path="/">
        <PatientLoginView />
      </Route>
    </Switch>
  </BrowserRouter>
);
export default App;
