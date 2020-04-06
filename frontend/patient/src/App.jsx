import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import FormView from './views/FormView';
import LoginDeviceView from './views/utility/LoginDeviceView';
import EndView from './views/utility/EndView';
import LoginView from './views/utility/LoginView';

const App = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/device">
        <LoginDeviceView />
      </Route>
      <Route path="/thanks">
        <EndView />
      </Route>
      <Route path="/form">
        <FormView />
      </Route>
      <Route path="/">
        <LoginView />
      </Route>
    </Switch>
  </BrowserRouter>
);

export default App;
