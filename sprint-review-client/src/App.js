import React, {Component} from 'react';
import './App.css';
import axios from 'axios';
import runtimeEnv from '@mars/heroku-js-runtime-env';

const env = runtimeEnv();

const axiosSR = axios.create({
  baseURL: env.HEROKU_URL ? env.HEROKU_URL : 'http://localhost:8080',
});

const GET_ADULT = `
  {
    jedi(phase:"adult"){
      name
    }
  }
`;


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {jedi: {}};
  }

  render() {
    return (<div>
      <meta charSet="utf-8" />
      <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png" />
      <link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png" />
      <meta httpEquiv="X-UA-Compatible" content="IE=edge,chrome=1" />
      <title>Gaia - Bootstrap Template | Free Demo</title>
      <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
      <link href="assets/css/bootstrap.css" rel="stylesheet" />
      <link href="assets/css/gaia.css" rel="stylesheet" />
      {/*     Fonts and icons     */}
      <link href="https://fonts.googleapis.com/css?family=Cambo|Poppins:400,600" rel="stylesheet" type="text/css" />
      <link href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet" />
      <link href="assets/css/fonts/pe-icon-7-stroke.css" rel="stylesheet" />
      <nav className="navbar navbar-default navbar-transparent navbar-fixed-top" color-on-scroll={200}>
        {/* if you want to keep the navbar hidden you can add this class to the navbar "navbar-burger"*/}
        <div className="container">
          <div className="navbar-header">
            <button id="menu-toggle" type="button" className="navbar-toggle" data-toggle="collapse" data-target="#example">
              <span className="sr-only">Toggle navigation</span>
              <span className="icon-bar bar1" />
              <span className="icon-bar bar2" />
              <span className="icon-bar bar3" />
            </button>
            <a href="http://www.creative-tim.com" className="navbar-brand">
              Gaia - Free Demo
            </a>
          </div>
          <div className="collapse navbar-collapse">
            <ul className="nav navbar-nav navbar-right navbar-uppercase">
              <li>
                <a href="http://www.creative-tim.com/product/gaia-bootstrap-template-pro" target="_blank">Get PRO Version</a>
              </li>
              <li className="dropdown">
                <a href="#gaia" className="dropdown-toggle" data-toggle="dropdown">
                  <i className="fa fa-share-alt" /> Share
                </a>
                <ul className="dropdown-menu dropdown-danger">
                  <li>
                    <a href="#"><i className="fa fa-facebook-square" /> Facebook</a>
                  </li>
                  <li>
                    <a href="#"><i className="fa fa-twitter" /> Twitter</a>
                  </li>
                  <li>
                    <a href="#"><i className="fa fa-instagram" /> Instagram</a>
                  </li>
                </ul>
              </li>
              <li>
                <a href="http://www.creative-tim.com/product/gaia-bootstrap-template" className="btn btn-danger btn-fill">Free Download</a>
              </li>
            </ul>
          </div>
          {/* /.navbar-collapse */}
        </div>
      </nav>
      <div className="section section-header">
        <div className="parallax filter filter-color-red">
          <div className="image" style={{backgroundImage: 'url("assets/img/header-1.jpeg")'}}>
          </div>
          <div className="container">
            <div className="content">
              <div className="title-area">
                <p>Free Demo</p>
                <h1 className="title-modern">Gaia</h1>
                <h3>Probably the most stylish bootstrap template in the world!</h3>
                <div className="separator line-separator">♦</div>
              </div>
              <div className="button-get-started">
                <a href="http://www.creative-tim.com/product/gaia-bootstrap-template" target="_blank" className="btn btn-white btn-fill btn-lg ">
                  Download Demo
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="section">
        <div className="container">
          <div className="row">
            <div className="title-area">
              <h2>Our Services</h2>
              <div className="separator separator-danger">✻</div>
              <p className="description">We promise you a new look and more importantly, a new attitude. We build that by getting to know you, your needs and creating the best looking clothes.</p>
            </div>
          </div>
          <div className="row">
            <div className="col-md-4">
              <div className="info-icon">
                <div className="icon text-danger">
                  <i className="pe-7s-graph1" />
                </div>
                <h3>Sales</h3>
                <p className="description">We make our design perfect for you. Our adjustment turn our clothes into your clothes.</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="info-icon">
                <div className="icon text-danger">
                  <i className="pe-7s-note2" />
                </div>
                <h3>Content</h3>
                <p className="description">We create a persona regarding the multiple wardrobe accessories that we provide..</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="info-icon">
                <div className="icon text-danger">
                  <i className="pe-7s-music" />
                </div>
                <h3>Music</h3>
                <p className="description">We like to present the world with our work, so we make sure we spread the word regarding our clothes.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="section section-our-team-freebie">
        <div className="parallax filter filter-color-black">
          <div className="image" style={{backgroundImage: 'url("assets/img/header-2.jpeg")'}}>
          </div>
          <div className="container">
            <div className="content">
              <div className="row">
                <div className="title-area">
                  <h2>Who We Are</h2>
                  <div className="separator separator-danger">✻</div>
                  <p className="description">We promise you a new look and more importantly, a new attitude. We build that by getting to know you, your needs and creating the best looking clothes.</p>
                </div>
              </div>
              <div className="team">
                <div className="row">
                  <div className="col-md-10 col-md-offset-1">
                    <div className="row">
                      <div className="col-md-4">
                        <div className="card card-member">
                          <div className="content">
                            <div className="avatar avatar-danger">
                              <img alt="..." className="img-circle" src="assets/img/faces/face_1.jpg" />
                            </div>
                            <div className="description">
                              <h3 className="title">Tina</h3>
                              <p className="small-text">CEO / Co-Founder</p>
                              <p className="description">I miss the old Kanye I gotta say at that time I’d like to meet Kanye And I promise the power is in the people and I will use the power given by the people to bring everything I have back to the people.</p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="col-md-4">
                        <div className="card card-member">
                          <div className="content">
                            <div className="avatar avatar-danger">
                              <img alt="..." className="img-circle" src="assets/img/faces/face_4.jpg" />
                            </div>
                            <div className="description">
                              <h3 className="title">Andrew</h3>
                              <p className="small-text">Product Designer</p>
                              <p className="description">I miss the old Kanye I gotta say at that time I’d like to meet Kanye And I promise the power is in the people and I will use the power given by the people to bring everything I have back to the people.</p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="col-md-4">
                        <div className="card card-member">
                          <div className="content">
                            <div className="avatar avatar-danger">
                              <img alt="..." className="img-circle" src="assets/img/faces/face_3.jpg" />
                            </div>
                            <div className="description">
                              <h3 className="title">Michelle</h3>
                              <p className="small-text">Marketing Hacker</p>
                              <p className="description">I miss the old Kanye I gotta say at that time I’d like to meet Kanye And I promise the power is in the people and I will use the power given by the people to bring everything I have back to the people.</p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="section section-our-clients-freebie">
        <div className="container">
          <div className="title-area">
            <h5 className="subtitle text-gray">Here are some</h5>
            <h2>Clients Testimonials</h2>
            <div className="separator separator-danger">∎</div>
          </div>
          <ul className="nav nav-text" role="tablist">
            <li className="active">
              <a href="#testimonial1" role="tab" data-toggle="tab">
                <div className="image-clients">
                  <img alt="..." className="img-circle" src="assets/img/faces/face_5.jpg" />
                </div>
              </a>
            </li>
            <li>
              <a href="#testimonial2" role="tab" data-toggle="tab">
                <div className="image-clients">
                  <img alt="..." className="img-circle" src="assets/img/faces/face_6.jpg" />
                </div>
              </a>
            </li>
            <li>
              <a href="#testimonial3" role="tab" data-toggle="tab">
                <div className="image-clients">
                  <img alt="..." className="img-circle" src="assets/img/faces/face_2.jpg" />
                </div>
              </a>
            </li>
          </ul>
          <div className="tab-content">
            <div className="tab-pane active" id="testimonial1">
              <p className="description">
                And I used a period because contrary to popular belief I strongly dislike exclamation points! We no longer have to be scared of the truth feels good to be home In Roman times the artist would contemplate proportions and colors. Now there is only one important color... Green I even had the pink polo I thought I was Kanye I promise I will never let the people down. I want a better life for all!
              </p>
            </div>
            <div className="tab-pane" id="testimonial2">
              <p className="description">Green I even had the pink polo I thought I was Kanye I promise I will never let the people down. I want a better life for all! And I used a period because contrary to popular belief I strongly dislike exclamation points! We no longer have to be scared of the truth feels good to be home In Roman times the artist would contemplate proportions and colors. Now there is only one important color...
              </p>
            </div>
            <div className="tab-pane" id="testimonial3">
              <p className="description"> I used a period because contrary to popular belief I strongly dislike exclamation points! We no longer have to be scared of the truth feels good to be home In Roman times the artist would contemplate proportions and colors. The 'Gaia' team did a great work while we were collaborating. They provided a vision that was in deep connection with our needs and helped us achieve our goals.
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className="section section-small section-get-started">
        <div className="parallax filter">
          <div className="image" style={{backgroundImage: 'url("assets/img/office-1.jpeg")'}}>
          </div>
          <div className="container">
            <div className="title-area">
              <h2 className="text-white">Do you want to work with us?</h2>
              <div className="separator line-separator">♦</div>
              <p className="description"> We are keen on creating a second skin for anyone with a sense of style! We design our clothes having our customers in mind and we never disappoint!</p>
            </div>
            <div className="button-get-started">
              <a href="#gaia" className="btn btn-danger btn-fill btn-lg">Contact Us</a>
            </div>
          </div>
        </div>
      </div>
      <footer className="footer footer-big footer-color-black" data-color="black">
        <div className="container">
          <div className="row">
            <div className="col-md-2 col-sm-3">
              <div className="info">
                <h5 className="title">Company</h5>
                <nav>
                  <ul>
                    <li>
                      <a href="#">Home</a></li>
                    <li>
                      <a href="#">Find offers</a>
                    </li>
                    <li>
                      <a href="#">Discover Projects</a>
                    </li>
                    <li>
                      <a href="#">Our Portfolio</a>
                    </li>
                    <li>
                      <a href="#">About Us</a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
            <div className="col-md-3 col-md-offset-1 col-sm-3">
              <div className="info">
                <h5 className="title"> Help and Support</h5>
                <nav>
                  <ul>
                    <li>
                      <a href="#">Contact Us</a>
                    </li>
                    <li>
                      <a href="#">How it works</a>
                    </li>
                    <li>
                      <a href="#">Terms &amp; Conditions</a>
                    </li>
                    <li>
                      <a href="#">Company Policy</a>
                    </li>
                    <li>
                      <a href="#">Money Back</a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
            <div className="col-md-3 col-sm-3">
              <div className="info">
                <h5 className="title">Latest News</h5>
                <nav>
                  <ul>
                    <li>
                      <a href="#">
                        <i className="fa fa-twitter" /> <b>Get Shit Done</b> The best kit in the market is here, just give it a try and let us...
                        <hr className="hr-small" />
                      </a>
                    </li>
                    <li>
                      <a href="#">
                        <i className="fa fa-twitter" /> We've just been featured on <b> Awwwards Website</b>! Thank you everybody for...
                      </a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
            <div className="col-md-2 col-md-offset-1 col-sm-3">
              <div className="info">
                <h5 className="title">Follow us on</h5>
                <nav>
                  <ul>
                    <li>
                      <a href="#" className="btn btn-social btn-facebook btn-simple">
                        <i className="fa fa-facebook-square" /> Facebook
                      </a>
                    </li>
                    <li>
                      <a href="#" className="btn btn-social btn-dribbble btn-simple">
                        <i className="fa fa-dribbble" /> Dribbble
                      </a>
                    </li>
                    <li>
                      <a href="#" className="btn btn-social btn-twitter btn-simple">
                        <i className="fa fa-twitter" /> Twitter
                      </a>
                    </li>
                    <li>
                      <a href="#" className="btn btn-social btn-reddit btn-simple">
                        <i className="fa fa-google-plus-square" /> Google+
                      </a>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
          <hr />
          <div className="copyright">
            ©  Creative Tim, made with love
          </div>
        </div>
      </footer>
      {/*   core js files    */}
      {/*  js library for devices recognition */}
      {/*  script for google maps   */}
      {/*   file where we handle all the script from the Gaia - Bootstrap Template   */}
    </div>);
  }

  componentDidMount() {
    this.onFetch();
  }

  onFetch = () => {
    axiosSR
      .post('/query', {query: GET_ADULT})
      .then(result =>
        this.setState(() => ({
          jedi: result.data.data.jedi,
          errors: result.data.errors,
        })),
      );
  };
}

const Jedi = ({jedi}) => (
  <div>
    <p>
      <strong>Jedi:</strong>
      {jedi.name}
    </p>
  </div>
);

export default App;
