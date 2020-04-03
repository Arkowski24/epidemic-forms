import React from 'react';
import { Route, BrowserRouter, Switch } from 'react-router-dom';

import FormsList from './views/FormsList';
import FormView from './views/form/FormView';
import LoginView from './views/LoginView';

const App = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/employee/forms/:formId">
        <FormView />
      </Route>
      <Route path="/employee/login">
        <LoginView />
      </Route>
      <Route path="/employee/">
        <FormsList />
      </Route>
    </Switch>
  </BrowserRouter>
);
export default App;
