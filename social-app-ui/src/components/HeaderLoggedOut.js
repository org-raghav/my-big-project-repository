import axios from "axios";
import React, { useContext, useState } from "react";
import DispatchContext from "../DispatchContext";

export default function HeaderLoggedOut(props) {
  //const { setLoggedIn } = useContext(ApplicationContext);

  const appDispatch = useContext(DispatchContext);

  const [email, setEmail] = useState();
  const [password, setPassword] = useState();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await axios.post("/login", {
        email,
        password,
      });

      console.log(response);

      if (response.status === 200) {
        console.log("you are successfully logged in !");
        console.log(response.headers["authorization"]);

        const userData = {
          token: response.headers["authorization"],
          firstName: response.data.firstName ,
          lastName : response.data.lastName,
          userId: response.data.userId,
          avatar: "My-Avatar",
        };
        appDispatch({
          type: "login",
          data: userData,
          flashMeassage: "You have successfully LoggedIn!",
        });
      }
    } catch (ex) {
      console.log("There is a problem, Something went wrong! " + ex);
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
