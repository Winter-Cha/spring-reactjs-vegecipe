import React, { Component } from 'react'
import { Link } from 'react-router-dom';

export default class Menu extends Component {
    render() {
        return (
            <div>
                <aside className="main-sidebar sidebar-dark-primary elevation-4">
                {/* Brand Logo */}
                <a href="index3.html" className="brand-link">
                    <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" className="brand-image img-circle elevation-3" style={{opacity: '.8'}} />
                    <span className="brand-text font-weight-light">AdminLTE</span>
                </a>
                {/* Sidebar */}
                <div className="sidebar">
                    {/* Sidebar user panel (optional) */}
                    <div className="user-panel mt-3 pb-3 mb-3 d-flex">
                    <div className="image">
                        <img src="dist/img/user2-160x160.jpg" className="img-circle elevation-2" alt="User" />
                    </div>
                    <div className="info">
                        <a href="take_url" className="d-block">Alexander Pierce</a>
                    </div>
                    </div>
                    {/* Sidebar Menu */}
                    <nav className="mt-2">
                    <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                        {/* Add icons to the links using the .nav-icon class
                        with font-awesome or any other icon font library */}
                        <li className="nav-item has-treeview">
                        <a href="take_url" className="nav-link">
                            <i className="nav-icon fas fa-tachometer-alt" />
                            <p>
                            Dashboard
                            <i className="right fas fa-angle-left" />
                            </p>
                        </a>
                        <ul className="nav nav-treeview">
                            <li className="nav-item">
                                <Link to="/" className="nav-link">
                                    <i className="far fa-circle nav-icon" />
                                    <p>Dashboard v1</p>
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to="/ingresos" className="nav-link">
                                    <i className="far fa-circle nav-icon" />
                                    <p>Dashboard v2</p>
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to="/perfil" className="nav-link">
                                    <i className="far fa-circle nav-icon" />
                                    <p>Dashboard v3</p>
                                </Link>
                            </li>
                        </ul>
                        </li>
                        <li className="nav-item">
                        <Link to="/ingresos" className="nav-link">
                            <i className="nav-icon fas fa-th" />
                            <p>
                            Ingresos
                            <span className="right badge badge-danger">New</span>
                            </p>
                        </Link>
                        </li>
                        <li className="nav-item has-treeview">
                        <Link to="/perfil" className="nav-link">
                            <i className="nav-icon fas fa-chart-pie" />
                            <p>
                            Perfil
                            <i className="right fas fa-angle-left" />
                            </p>
                        </Link>
                        </li>
                    </ul>
                    </nav>
                    {/* /.sidebar-menu */}
                </div>
                {/* /.sidebar */}
                </aside>
            </div>
        )
    }
}
