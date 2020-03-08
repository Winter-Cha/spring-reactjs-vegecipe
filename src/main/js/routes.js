import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Dashboard  from './pages/Dashboard';
import Ingresos from './pages/Ingresos';
import Perfil from './pages/Perfil';
import Menu from './Menu';


const Routes = () => {
    return(
        <Router>
              <Menu/>
                <Route exact path='/' component={Dashboard}/>
                <Route  path='/ingresos' component={Ingresos}/>
                <Route  path='/perfil' component={Perfil}/>
          </Router>
    )
}

export default Routes;