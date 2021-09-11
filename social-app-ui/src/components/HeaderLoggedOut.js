import axios from "axios";
import React, { useState } from "react";

export default function HeaderLoggedOut(props) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });

      console.log(response);

      if (response.status === 200) {
        console.log("you are successfully logged in !");
        //In LoggedOut component we are setting  here
        //If status code is 200 then  setLoggedIn  is  true
        //Means now The user is successfully loggedIn
        //and When User is successfully loggedIn then  after  rerender this
        //component is automatically not shownby ternary operator defined in
        //Header component which is also a parent of this component.
        props.setLoggedIn(true);
      }
      if (response.data) {
        //Not working in my case as because spring security do not return anything
        //console.log(response.data);
      } else {
        //console.log("Invalid credentials!")
      }
    } catch (e) {
      console.log("There is a problem, Something went wrong!");
    }
  }

  return (
    <form onSubmit={handleSubmit} className="mb-0 pt-2 pt-md-0">
      <div className="row align-items-center">
        <div className="col-md mr-0 pr-md-0 mb-3 mb-md-0">
          <input
            name="email"
            className="form-control form-control-sm input-dark"
            type="text"
            onChange={(e) => setEmail(e.target.value)}
            placeholder="email or mobile"
            autoComplete="off"
          />
        </div>
        <div className="col-md mr-0 pr-md-0 mb-3 mb-md-0">
          <input
            name="password"
            className="form-control form-control-sm input-dark"
            type="password"
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
          />
        </div>
        <div className="col-md-auto">
          <button className="btn btn-success btn-sm">Sign In</button>
        </div>
      </div>
    </form>
  );
}
