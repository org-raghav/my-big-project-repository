import axios from "axios";
import React, { useEffect } from "react";
import { useImmerReducer } from "use-immer";
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
import DispatchContext from "./DispatchContext";
import StateContext from "./StateContext";

axios.defaults.baseURL = "http://localhost:8080";

function App() {
  const initialState = {
    loggedIn: Boolean(localStorage.getItem("Authorization")),
    flashMessages: [],
    user: {
      token: localStorage.getItem("Authorization"),
      userId: localStorage.getItem("userId"),
      avatar: localStorage.getItem("avatar"),
    },
  };

  // const[state, dispatch] = useReducer(ourReducer, initialState);

  const [state, dispatch] = useImmerReducer(ourReducer, initialState);

  //ourReducer(arg1, arg2) takes two args
  //arg1 is previous value of our state(means last updated value)
  //meaning that we can able to take decision of what current status we want
  //on behalf of our  previous state.
  /* function ourReducer(state, action){
    switch(action.type){
      case "login":
        console.log("i am invoked!");
        return {loggedIn : true, flashMessages : state.flashMessages.concat(action.value)};
      case "logout":
        return {loggedIn : false, flashMessages : state.flashMessages.concat(action.value)};
      case "flashMessage":
        return {loggedIn : state.loggedIn, flashMessages : state.flashMessages.concat(action.value)};
      default :
        return;
    }
  } */

  // Immer draft variable is able to access values of state
  // draft is a copy of actual state so we can modify it directly.

  function ourReducer(draft, action) {
    switch (action.type) {
      case "login":
        draft.loggedIn = true;
        draft.flashMessages.push(action.value); //we can directly modify draft
        draft.user = action.data;
        return; //break
      case "logout":
        draft.loggedIn = false;
        draft.flashMessages.push(action.value);
        return; //break
      case "flashMessage":
        draft.flashMessages.push(action.value);
        return; //break
      default:
        return; //break
    }
  }

  useEffect(() => {
    if (state.loggedIn) {
      localStorage.setItem("userId", state.user.userId);
      localStorage.setItem("Authorization", state.user.token);
      localStorage.setItem("avatar", state.user.avatar);
      axios.defaults.headers.common["Authorization"] = state.user.token;
    } else {
      localStorage.removeItem("userId");
      localStorage.removeItem("Authorization");
      localStorage.removeItem("avatar");
      axios.defaults.headers.common["Authorization"] = '';
    }
  }, [state.loggedIn]);

  //Always remember Route tag works in order
  return (
    <StateContext.Provider value={{ state }}>
      <DispatchContext.Provider value={{ dispatch }}>
        <BrowserRouter>
          <FlashMessage messages={state.flashMessages} />
          <Header />
          <Switch>
            <Route path="/" exact>
              {state.loggedIn ? <Home /> : <HomeGuest />}
            </Route>
            <Route path="/posts/:postId">
              <ViewSinglePost />
            </Route>
            <Route path="/posts">
              <CreatePost />
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
      </DispatchContext.Provider>
    </StateContext.Provider>
  );
}

export default App;
