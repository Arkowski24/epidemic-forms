import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import FormView from './views/FormView';
import LoginDeviceView from './views/utility/LoginDeviceView';
import EndView from './views/utility/EndView';

const App = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/device/login">
        <LoginDeviceView />
      </Route>
      <Route path="/thanks">
        <EndView />
      </Route>
      <Route path="/">
        <FormView />
      </Route>
    </Switch>
  </BrowserRouter>
);

export default App;
