import axios from "axios";
import React from "react";
import Page from "./Page";

function HomeGuest() {
  async function handleSubmit(e) {
    e.preventDefault();
    //alert("handle submit");
    try {
      await axios.post("http://localhost:8080/users/sign-up", {
        firstName: "raghav",
        lastName: "Tiger",
        email: "test@gmail.com",
        password: "123",
      });
      console.log("User is successfully created.");
    } catch (e) {
      console.log("There is an error ! Something went wrong...");
    }
  }

  return (
    <Page title="Guest">
      <div className="row align-items-center">
        <div className="col-lg-7 py-3 py-md-5">
          <h1 className="display-3">Remember Writing...</h1>
          <p className="lead text-muted">
            Are you sick of short tweets and impersonal &ldquo;shared&rdquo;
            posts that are reminiscent of the late 90&rsquo;s email forwards? We
            believe getting back to actually writing is the key to enjoying the
            internet again.
          </p>
        </div>
        <div className="col-lg-5 pl-lg-5 pb-3 py-lg-5">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="firstName" className="text-muted mb-1">
                <small>First Name</small>
              </label>
              <input
                id="firstName"
                name="firstName"
                className="form-control"
                type="text"
                placeholder="Enter Your first name"
                autoComplete="off"
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastName" className="text-muted mb-1">
                <small>LastName</small>
              </label>
              <input
                id="lastName"
                name="lastName"
                className="form-control"
                type="text"
                placeholder="Enter your last name"
                autoComplete="off"
              />
            </div>
            <div className="form-group">
              <label htmlFor="email-register" className="text-muted mb-1">
                <small>Email</small>
              </label>
              <input
                id="email-register"
                name="email"
                className="form-control"
                type="text"
                placeholder="you@example.com"
                autoComplete="off"
              />
            </div>
            <div className="form-group">
              <label htmlFor="password-register" className="text-muted mb-1">
                <small>Password</small>
              </label>
              <input
                id="password-register"
                name="password"
                className="form-control"
                type="password"
                placeholder="Create a password"
              />
            </div>
            <button
              type="submit"
              className="py-3 mt-4 btn btn-lg btn-success btn-block"
            >
              Sign up for ComplexApp
            </button>
          </form>
        </div>
      </div>
    </Page>
  );
}
export default HomeGuest;
