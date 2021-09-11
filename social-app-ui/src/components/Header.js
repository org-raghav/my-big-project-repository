import React, { useState } from "react";
import HeaderLoggedIn from "./HeaderLoggedIn";
import HeaderLoggedOut from "./HeaderLoggedOut";

function Header() {

  const[loggedIn, setLoggedIn] = useState(Boolean(localStorage.getItem('Authorization')));

  return (
    <header className="header-bar bg-primary mb-3">
      <div className="container d-flex flex-column flex-md-row align-items-center p-3">
        <h4 className="my-0 mr-md-auto font-weight-normal">
          <a href="/" className="text-white">
            Our Social Blogs
          </a>
        </h4>
        { loggedIn ? <HeaderLoggedIn setLoggedIn={setLoggedIn}/> : <HeaderLoggedOut setLoggedIn={setLoggedIn} /> }
      </div>
    </header>
  );
}
export default Header;
