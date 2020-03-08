import React, { Component } from 'react'

export default class Dashboard extends Component {
    render() {
        return (
            <div>
                <div className="content-wrapper">
                    <section className='content'>
                        <div className="container main-container">
                            {/* Small boxes (Stat box) */}
                            <div className="row" >
                                <div className="main-img-center-box col-lg-4 col-md-4 col-sm-4 col-12">
                                    {/* small box */}
                                    <img src="../../dist/img/main/main-community.jpg" alt="Community" className="img-fluid img-rounded elevation-3" />
                                </div>
                                {/* ./col */}
                                <div className="main-img-center-box col-lg-4 col-md-4 col-sm-4 col-12">
                                    {/* small box */}
                                    <img src="../../dist/img/main/main-book.jpg" alt="Book" className="img-fluid img-rounded elevation-3" />
                                </div>
                                {/* ./col */}
                                <div className="main-img-center-box col-lg-4 col-md-4 col-sm-4 col-12">
                                    {/* small box */}
                                    <img src="../../dist/img/main/main-vegecipe.jpg" alt="Vegecipe" className="img-fluid img-rounded elevation-3" />
                                </div>
                                {/* ./col */}
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        )
    }
}
