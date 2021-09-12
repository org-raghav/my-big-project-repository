import axios from 'axios';
import React, { useContext } from 'react'
import { Link } from 'react-router-dom';
import ApplicationContext from '../ApplicationContext';

export default function HeaderLoggedIn(props) {

  const {setLoggedIn} = useContext(ApplicationContext);

  //here we are setting setLoggedIn function to false
  //so that we can see our LoggedOut component again
  //because it is the logic implemented by ternary operator
  //defined in this parent component Header.js
  function handleLogOut(){
    setLoggedIn(false);
    localStorage.removeItem('userId');
    localStorage.removeItem('Authorization');
    localStorage.removeItem('avatar');
    //removing axios Authorization header for further request
    axios.defaults.headers.common["Authorization"] = '';
  }
    return (
        <div className="flex-row my-3 my-md-0">
          <a href="#" className="text-white mr-2 header-search-icon">
            <i className="fas fa-search"></i>
          </a>
          <span className="mr-2 header-chat-icon text-white">
            <i className="fas fa-comment"></i>
            <span className="chat-count-badge text-white"> </span>
          </span>
          <a href="#" className="mr-2">
            <img className="small-header-avatar" src="https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128" />
          </a>
          <Link to="/posts" className="btn btn-sm btn-success mr-2">
            Create Post
          </Link>
          <button onClick={handleLogOut} className="btn btn-sm btn-secondary">
            Sign Out
          </button>
        </div>
    );
}
