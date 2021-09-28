/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import React, { useContext, useEffect } from "react";
import { Link } from "react-router-dom";
import { useImmer } from "use-immer";
import DispatchContext from "../DispatchContext";

function Search() {
  const appDispatch = useContext(DispatchContext);

  const [state, setState] = useImmer({
    searchTerm: "",
    results: [],
    show: "neither",
    requestCount: 0,
  });

  function searchKeyPressHandler(e) {
    if (e.keyCode === 27) {
      appDispatch({ type: "closeSearch" });
    }
  }

  function handleInput(e) {
    const value = e.target.value;
    setState((draft) => {
      draft.searchTerm = value;
    });
  }

  useEffect(() => {
    document.addEventListener("keyup", searchKeyPressHandler);
    return () => document.removeEventListener("keyup", searchKeyPressHandler);
  }, []);

  useEffect(() => {
    if (state.searchTerm.trim()) {
      setState((draft) => {
        draft.show = "loading";
      });
      const delay = setTimeout(() => {
        setState((draft) => {
          draft.requestCount++;
        });
      }, 750);
      return () => clearInterval(delay);
    } else {
      setState((draft) => {
        draft.show = "neither";
      });
    }
  }, [state.searchTerm]);

  useEffect(() => {
    if (state.requestCount) {
      //send axios request here
      const ourRequest = axios.CancelToken.source();
      console.log("request count is::" + state.requestCount);
      async function fetchResults() {
        try {
          console.log("state search Tearm ::  " + state.searchTerm);
          const response = await axios.post(
            "/search",
            {
              searchTerm: state.searchTerm,
            },
            {
              cancelToken: ourRequest.token,
            }
          );
          console.log(response.data);
          setState((draft) => {
            draft.results = response.data;
            draft.show = "results";
          });
        } catch (ex) {
          console.log("There are some exception during fetchResults ::" + ex);
        }
      }
      fetchResults();
      return () => ourRequest.cancel();
    }
  }, [state.requestCount]);

  return (
    <div>
      <div className="search-overlay">
        <div className="search-overlay-top shadow-sm">
          <div className="container container--narrow">
            <label htmlFor="live-search-field" className="search-overlay-icon">
              <i className="fas fa-search"></i>
            </label>
            <input
              onChange={handleInput}
              autoFocus
              type="text"
              autoComplete="off"
              id="live-search-field"
              className="live-search-field"
              placeholder="What are you interested in?"
            />
            <span
              onClick={() => appDispatch({ type: "closeSearch" })}
              className="close-live-search">
              <i className="fas fa-times-circle"></i>
            </span>
          </div>
        </div>

        <div className="search-overlay-bottom">
          <div className="container container--narrow py-3">
            <div
              className={
                "circle-loader " +
                (state.show === "loading" ? "circle-loader--visible" : "")
              }></div>
            <div
              className={
                "live-search-results " +
                (state.show === "results" ? "live-search-results--visible" : "")
              }>
              {Boolean(state.results.length) && (
                  <div className="list-group shadow-sm">
                  <div className="list-group-item active">
                    <strong>Search Results</strong> ({state.results.length}{" "}
                    {state.results.length > 1 ? "items" : "item"} found)
                  </div>
                  {state.results.map((post) => {
                    const date = new Date(post.createdDate);
                    const dateFormatted = `${date.getDate()}/${date.getMonth() + 1
                      }/${date.getFullYear()}`;
  
                    //date.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
                    //const timeFormatted = `${date.getHours()} : ${date.getMinutes()}`;
                    const timeFormatted = date.toLocaleString("en-US", {
                      hour: "numeric",
                      minute: "numeric",
                      hour12: true,
                    });
  
                    return (
                      <Link onClick={() => appDispatch({ type: "closeSearch" })}
                        key={post.postId}
                        to={`/posts/${post.postId}`}
                        className="list-group-item list-group-item-action"
                      >
                        <img
                          className="avatar-tiny"
                          src="../images/user.png"
                          style={{ width: "3em", height: "3em" }}
                          alt="user-img"
                        />{" "}
                        <strong>{post.title}</strong><br />
                        {" "}created by {post.createdBy} {" "}
                        <span className="text-muted small">
                          on{" "} {dateFormatted} at {timeFormatted}{" "}
                        </span>
                      </Link>
                    );
                  })}
  
                </div>
              )}

              {(!Boolean(state.results.length) && 
              (<p className="alert alert-danger  text-center shadow-sm"> Sorry, No results found!</p>))}

            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Search;
