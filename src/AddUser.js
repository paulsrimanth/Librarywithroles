import { useState } from "react";
import "./adduser.css";
function AddUser() {
  const [email, setemail] = useState("");
  const [password, setPassword] = useState("");
  const [roles, Setroles] = useState("");
  const [added, Setadded] = useState(false);
  const [errors, setErrors] = useState({});
  const createuserbody = {
    email,
    password,
    roles,
  };
  const handleSubmit = (e) => {
    console.log(localStorage.getItem("token"));
    e.preventDefault();
    const errors = {};
    async function createuser() {
      const created = await fetch("http://localhost:8080/user/createuser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
        body: JSON.stringify(createuserbody),
      });
      console.log(localStorage.getItem("token"));
      const response = await created.json();
      if (response.ok) {
        alert("Added user succesfully");
        Setadded(!added);
      }
      console.log(response);
    }
    //   console.log(created);
    if (
      email &&
      !/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/.test(email).valueOf()
    ) {
      errors.email = "*email is invalid";
    }
    if (
      password &&
      !/"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"/
        .test(password)
        .valueOf()
    ) {
      errors.password = "password must contain a number and special case";
    }
    if (!email) {
      errors.email = "email is required";
    }

    if (!password) {
      errors.password = "Password is required";
    }
    if (!roles) {
      errors.roles = "*a role must be assigned";
    }

    if (Object.keys(errors).length === 0) {
      createuser();
    } else {
      setErrors(errors);
    }
  };

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit} className="registration-form">
        <div>
          <h2>create User</h2>
        </div>
        <div className="form-row">
          <label htmlFor="email" className="input-label">
            email:
          </label>
          <input
            className="input-field"
            type="text"
            id="email"
            value={email}
            onChange={(e) => setemail(e.target.value)}
          />
          {errors.email && (
            <small className="error-message">{errors.email}</small>
          )}
        </div>
        <div className="form-row">
          <label htmlFor="password" className="input-label">
            Password:
          </label>
          <input
            className="input-field"
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {errors.password && (
            <small className="error-message">{errors.password}</small>
          )}
        </div>

        <div className="form-row">
          <label htmlFor="roles" className="input-label">
            role:
          </label>
          <input
            className="input-field"
            type="text"
            id="roles"
            value={roles}
            onChange={(e) => Setroles(e.target.value)}
          />
          {errors.roles && (
            <small className="error-message">{errors.roles}</small>
          )}
        </div>

        <button type="submit" className="btn-ddd">
          Create
        </button>
      </form>
      {added ? "REGISTERED SUCCESSFULLY" : " "}
    </div>
  );
}

export default AddUser;
