import React, { useContext } from "react";
import StateContext from "../StateContext";
import HeaderLoggedIn from "./HeaderLoggedIn";
import HeaderLoggedOut from "./HeaderLoggedOut";

function Header(props) {

  const {state} = useContext(StateContext);

  return (
    <header className="header-bar bg-primary mb-3">
      <div className="container d-flex flex-column flex-md-row align-items-center p-3">
        <h4 className="my-0 mr-md-auto font-weight-normal">
          <a href="/" className="text-white">
            Our Social Blogs
          </a>
        </h4>
        {state.loggedIn ? <HeaderLoggedIn /> : <HeaderLoggedOut />}
      </div>
    </header>
  );
}
export default Header;
