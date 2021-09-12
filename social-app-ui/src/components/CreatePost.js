import axios from "axios";
import React, { useState } from "react";
import { withRouter } from "react-router";
import Page from "./Page";

function CreatePost(props) {
  const [title, setTitle] = useState();
  const [body, setBody] = useState();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await axios.post("/posts", { title, body });
      console.log(response);
      if (response.status === 201) {
        console.log(response.data.postId);
        console.log(response.data.title);
        console.log(response.data.body);
        //Redirect to new post(blog) url(Which is created by this post request)
        props.history.push(`/posts/${response.data.postId}`);
      }else
       console.log("no data available to preview !")
    } catch (ex) {
      console.log("An Exception occured during create Post request");
    }
  }

  return (
    <Page title="Create Post">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="post-title" className="text-muted mb-1">
            <small>Title</small>
          </label>
          <input
            autoFocus
            name="title"
            onChange={(e) => setTitle(e.target.value)}
            id="post-title"
            className="form-control form-control-lg form-control-title"
            type="text"
            placeholder=""
            autoComplete="off"
          />
        </div>

        <div className="form-group">
          <label htmlFor="post-body" className="text-muted mb-1 d-block">
            <small>Body Content</small>
          </label>
          <textarea
            name="body"
            onChange={(e) => setBody(e.target.value)}
            id="post-body"
            className="body-content tall-textarea form-control"
            type="text"
          ></textarea>
        </div>

        <button className="btn btn-primary">Save New Post</button>
      </form>
    </Page>
  );
}
//withRouter is important to use history object
//All things like BrowserRouter, Switch, Router, withRouter comes in
//'react-router-dom' package.
export default withRouter(CreatePost);
