import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Main  from './pages/Main';
import Ingresos from './pages/Ingresos';
import Perfil from './pages/Perfil';


const MainRoutes = () => {
    return(
            <>
                <Route exact path='/' component={Main}/>
                <Route  path='/community' component={Ingresos}/>
                <Route  path='/book' component={Ingresos}/>
                <Route  path='/vegecipe' component={Perfil}/>
            </>
    )
}

export default MainRoutes;