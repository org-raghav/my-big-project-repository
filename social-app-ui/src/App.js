import axios from "axios";
import React, { useState } from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import "./App.css";
import About from "./components/About";
import CreatePost from "./components/CreatePost";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import HomeGuest from "./components/HomeGuest";
import Terms from "./components/Terms";
import ViewSinglePost from "./components/ViewSinglePost";

axios.defaults.baseURL= "http://localhost:8080";
axios.defaults.headers.common['Authorization'] = localStorage.getItem("Authorization");


function App() {
  const [loggedIn, setLoggedIn] = useState(
    Boolean(localStorage.getItem("Authorization"))
  );

  return (
    <BrowserRouter>
      <Header loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
      <Switch>
        <Route path="/" exact>
          {loggedIn ? <Home /> : <HomeGuest />}
        </Route>
        <Route path="/create-post">
            <CreatePost  />
        </Route>
        <Route path="/post/:postId">
          <ViewSinglePost />
        </Route>
        <Route path="/about-us">
          <About />
        </Route>
        <Route path="/terms">
          <Terms />
        </Route>
      </Switch>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
