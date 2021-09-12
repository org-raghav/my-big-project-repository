import axios from "axios";
import React, { useState } from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import "./App.css";
import About from "./components/About";
import CreatePost from "./components/CreatePost";
import FlashMessage from "./components/FlashMessage";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import HomeGuest from "./components/HomeGuest";
import Terms from "./components/Terms";
import ViewSinglePost from "./components/ViewSinglePost";

axios.defaults.baseURL = "http://localhost:8080";

function App() {
  const [loggedIn, setLoggedIn] = useState(Boolean(localStorage.getItem("Authorization")));
  const [flashMessages, setFlashMessages] = useState([]);
  
  function addFlashMessage(message){
    setFlashMessages(prev => prev.concat(message));
  }

  return (
    <BrowserRouter>
      <FlashMessage messages={flashMessages}/>
      <Header loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
      <Switch>
        <Route path="/" exact>
          {loggedIn ? <Home /> : <HomeGuest />}
        </Route>
        <Route path="/posts">
          <CreatePost addFlashMessage={addFlashMessage}/>
        </Route>
        <Route path="/posts/:postId">
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
