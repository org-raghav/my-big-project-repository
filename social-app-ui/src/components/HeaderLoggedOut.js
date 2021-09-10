import axios from "axios";
import React, { useState } from "react";

export default function HeaderLoggedOut() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });
      if(response.data){
        console.log(response.data);
      }else{
          console.log("Invalid credentials!")
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
