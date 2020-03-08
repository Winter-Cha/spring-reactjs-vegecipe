import React, { Component } from 'react'
import { Link } from 'react-router-dom';

export default class Header extends Component {
    render() {
        return (
            <div>
                {/* Navbar */}
                <nav className="main-header navbar navbar-expand-md navbar-light navbar-orange">
                    <div className="container">
                        <Link to="/" className="navbar-brand brand-link">
                            <img src="../../dist/img/AdminLTELogo.png" alt="AdminLTE Logo" className="brand-image img-circle elevation-3" />
                            <span className="brand-text font-weight-bold">Vegecipe</span>
                        </Link>
                        <button className="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>

                        <div className="collapse navbar-collapse order-3" id="navbarCollapse">
                            {/* Left navbar links */}
                            <ul className="navbar-nav">
                                {/*
                                <li className="nav-item">
                                    <a className="nav-link" data-widget="pushmenu" href="#"><i className="fas fa-bars"></i></a>
                                </li>
                                */}
                                <li className="nav-item">
                                    <Link to="/community" className="nav-link">
                                        <p>Community</p>
                                    </Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/book" className="nav-link">
                                        <p>Book</p>
                                    </Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/vegecipe" className="nav-link">
                                        <p>Vegecipe</p>
                                    </Link>
                                </li>
                            </ul>

                            {/* SEARCH FORM */}
                            <form className="form-inline ml-0 ml-md-3">
                                <div className="input-group input-group-sm">
                                    <input className="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search" />
                                    <div className="input-group-append">
                                        <button className="btn btn-navbar" type="submit">
                                            <i className="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        {/* Right navbar links */}
                        <ul className="order-1 order-md-3 navbar-nav navbar-no-expand ml-auto">
                            {/* Messages Dropdown Menu */}
                            <li className="nav-item dropdown">
                                <a className="nav-link" data-toggle="dropdown" href="#">
                                    <i className="fas fa-sign-in-alt"></i>
                                </a>
                                <div className="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                                    <a href="#" className="dropdown-item">
                                        {/* Message Start */}
                                        <div className="media">
                                            <img src="../../dist/img/user1-128x128.jpg" alt="User Avatar" className="img-size-50 mr-3 img-circle" />>
                                                        <div className="media-body">
                                                <h3 className="dropdown-item-title">
                                                    Brad Diesel
                                                    <span className="float-right text-sm text-danger"><i className="fas fa-star"></i></span>
                                                </h3>
                                                <p className="text-sm">Call me whenever you can...</p>
                                                <p className="text-sm text-muted"><i className="far fa-clock mr-1"></i> 4 Hours Ago</p>
                                            </div>
                                        </div>
                                        {/* Message End */}
                                    </a>
                                    <div className="dropdown-divider"></div>
                                    <a href="#" className="dropdown-item">
                                        {/* Message Start */}
                                        <div className="media">
                                            <img src="../../dist/img/user8-128x128.jpg" alt="User Avatar" className="img-size-50 img-circle mr-3" />>
                                                            <div className="media-body">
                                                <h3 className="dropdown-item-title">
                                                    John Pierce
                                                    <span className="float-right text-sm text-muted"><i className="fas fa-star"></i></span>
                                                </h3>
                                                <p className="text-sm">I got your message bro</p>
                                                <p className="text-sm text-muted"><i className="far fa-clock mr-1"></i> 4 Hours Ago</p>
                                            </div>
                                        </div>
                                        {/* Message End */}
                                    </a>
                                    <div className="dropdown-divider"></div>
                                    <a href="#" className="dropdown-item">
                                        {/* Message Start */}
                                        <div className="media">
                                            <img src="../../dist/img/user3-128x128.jpg" alt="User Avatar" className="img-size-50 img-circle mr-3" />>
                                                                <div className="media-body">
                                                <h3 className="dropdown-item-title">
                                                    Nora Silvester
                                                    <span className="float-right text-sm text-warning"><i className="fas fa-star"></i></span>
                                                </h3>
                                                <p className="text-sm">The subject goes here</p>
                                                <p className="text-sm text-muted"><i className="far fa-clock mr-1"></i> 4 Hours Ago</p>
                                            </div>
                                        </div>
                                        {/* Message End */}
                                    </a>
                                    <div className="dropdown-divider"></div>
                                    <a href="#" className="dropdown-item dropdown-footer">See All Messages</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
                {/* /.navbar */}
            </div>
        )
    }
}
