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
import Profile from "./components/Profile";
import Search from "./components/Search";

function App() {
  const initialState = {
    loggedIn: Boolean(localStorage.getItem("Authorization")),
    flashMessages: [],
    user: {
      token: localStorage.getItem("Authorization"),
      firstName: localStorage.getItem("firstName"),
      lastName: localStorage.getItem("lastName"),
      userId: localStorage.getItem("userId"),
      avatar: localStorage.getItem("avatar"),
    },
    isSearchOpen : false
  };

  // const[state, dispatch] = useReducer(ourReducer, initialState);

  const [appState, appDispatch] = useImmerReducer(ourReducer, initialState);

  //Very important settings of axios
  //axios.defaults.headers.common["Authorization"] = appState.user.token;
  axios.defaults.baseURL = "http://localhost:8080";
  axios.defaults.headers.common = {
    "Content-Type": "application/json",
    "Authorization": appState.user.token
  }

  function ourReducer(draft, action) {
    switch (action.type) {
      case "login":
        draft.loggedIn = true; //draft means state
        draft.flashMessages.push(action.flashMeassage); //we can directly modify draft
        draft.user = action.data;
        return; //or break;
      case "logout":
        draft.loggedIn = false;
        draft.flashMessages.push(action.flashMeassage);
        return; //or break;
      case "flashMessage":
        draft.flashMessages.push(action.value);
        return; //or break;
      case "openSearch":
        draft.isSearchOpen = true;
        return;
      case "closeSearch":
        draft.isSearchOpen = false;
        return;
      default:
        return; //or break;
    }
  }

  useEffect(() => {
    if (appState.loggedIn) {
      localStorage.setItem("userId", appState.user.userId);
      localStorage.setItem("firstName", appState.user.firstName);
      localStorage.setItem("lastName", appState.user.lastName);
      localStorage.setItem("Authorization", appState.user.token);
      localStorage.setItem("avatar", appState.user.avatar);
      axios.defaults.headers.common["Authorization"] = appState.user.token;
    } else {
      localStorage.removeItem("userId");
      localStorage.removeItem("firstName");
      localStorage.removeItem("lastName");
      localStorage.removeItem("Authorization");
      localStorage.removeItem("avatar");
      axios.defaults.headers.common["Authorization"] = "";
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [appState.loggedIn]);

  //Always remember Route tag works in order
  return (
    <StateContext.Provider value={appState}>
      <DispatchContext.Provider value={appDispatch}>
        <BrowserRouter>
          <FlashMessage messages={appState.flashMessages} />
          <Header />
          <Switch>
            <Route path="/" exact>
              {appState.loggedIn ? <Home /> : <HomeGuest />}
            </Route>
            <Route path="/posts/:postId">
              <ViewSinglePost />
            </Route>
            <Route path="/posts">
              <CreatePost />
            </Route>
            <Route path="/profile/:profileId">
              <Profile />
            </Route>
            <Route path="/about-us">
              <About />
            </Route>
            <Route path="/terms">
              <Terms />
            </Route>
          </Switch>
          {appState.isSearchOpen ? <Search />  : ''}
          <Footer />
        </BrowserRouter>
      </DispatchContext.Provider>
    </StateContext.Provider>
  );
}

export default App;
