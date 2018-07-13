import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

const axiosSR = axios.create({
  baseURL: 'http://localhost:8080',
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
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo"/>
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code>
        </p>
        <Jedi jedi={this.state.jedi}/>
      </div>
    );
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
