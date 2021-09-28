/* eslint-disable jsx-a11y/alt-text */
import React, { useContext } from 'react'
import { Link } from 'react-router-dom';
import DispatchContext from '../DispatchContext';
import StateContext from '../StateContext';

export default function HeaderLoggedIn(props) {

  const appDispatch = useContext(DispatchContext);
  const appState  = useContext(StateContext); 

  function handleLogout(){
    appDispatch({type : "logout", flashMeassage : "You have successfully LoggedOut!"});
  }

  function handleSearchIcon(e){
    e.preventDefault();
    appDispatch({type : "openSearch"})
  }

    return (
        <div className="flex-row my-3 my-md-0">
          <Link onClick={handleSearchIcon} to="#" className="text-white mr-2 header-search-icon">
            <i className="fas fa-search"></i>
          </Link>
          <span className="mr-2 header-chat-icon text-white">
            <i className="fas fa-comment"></i>
            <span className="chat-count-badge text-white"> </span>
          </span>
          <Link to={`/profile/${appState.user.userId}`} className="mr-2">
            <span className="text-white">Hello! {appState.user.firstName}</span>{" "}
            <img className="small-header-avatar" src="https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128" />
          </Link>
          <Link to="/posts" className="btn btn-sm btn-success mr-2">
            Create Post
          </Link>
          <button onClick={handleLogout} className="btn btn-sm btn-secondary">
            Sign Out
          </button>
        </div>
    );
}
