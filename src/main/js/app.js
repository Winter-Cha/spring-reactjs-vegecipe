'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
import { BrowserRouter as Router } from 'react-router-dom';
const when = require('when');
const client = require('./client');

const follow = require('./follow'); // function to hop multiple links by "rel"

const stompClient = require('./websocket-listener');

const root = '/api';

import Header from './Header';
import Footer from './Footer';
import MainRoutes from './main_routes';


class App extends React.Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div className="wrapper">
				<Router>
					<Header />
					<MainRoutes />
					<Footer />
				</Router>
			</div>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

