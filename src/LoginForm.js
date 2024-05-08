import { useEffect, useState } from "react";
import "./loginform.css";
import { useNavigate } from "react-router-dom";
function LoginForm() {
  const [email, setemail] = useState("");
  const [password, setPassword] = useState("");
  const [captcha, setCaptcha] = useState("");
  const [captchaValue, setCaptchaValue] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const loginbody = {
    email,
    password,
  };
  useEffect(() => {
    generateCaptcha();
  }, []);
  const generateCaptcha = () => {
    const chars =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let captcha = "";
    for (let i = 0; i < 6; i++) {
      captcha += chars[Math.floor(Math.random() * chars.length)];
    }
    setCaptchaValue(captcha);
  };
  const RefreshIcon = ({ width = 24, height = 24, fill = "currentColor" }) => (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={width}
      height={height}
      viewBox="0 0 24 24"
      fill="none"
      stroke={fill}
      strokeWidth="1.5"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M23 4v6h-6M1 20v-6h6" />
      <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15" />
    </svg>
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    const errors = {};
    async function userlogin() {
      const loginres = await fetch("http://localhost:8080/roles/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginbody),
      });
      const response = await loginres.json();
      console.log(response.token);
      if (response == null) {
        errors.password = "*Invalid password";
        errors.email = "*Invalid email";
      }

      if (loginres.ok) {
        console.log(response);
        console.log(response.roles);
        localStorage.setItem("token", response.token);
        localStorage.setItem("roles", response.roles);
        navigate("/library");
      }
    }

    if (!email) {
      errors.email = "*email is required";
    }
    if (
      email &&
      !/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/.test(email).valueOf()
    ) {
      errors.email = "*email is invalid";
    }
    if (!password) {
      errors.password = "*Password is required";
    }

    if (captcha !== captchaValue) {
      errors.captcha = "*Invalid captcha";
    }

    if (Object.keys(errors).length === 0) {
      // Perform login logic here
      userlogin();
      console.log("Login successful!");
    } else {
      setErrors(errors);
    }
  };

  return (
    <div className="login-body">
      <div className="login-form">
        <div>
          <h2>Login</h2>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="input-fieldo">
            <label htmlFor="email" className="labels">
              email:
            </label>
            <input
              className="inputs"
              type="text"
              id="email"
              value={email}
              onChange={(e) => setemail(e.target.value)}
            />
          </div>
          {errors.email && <small className="errors">{errors.email}</small>}
          <div className="input-fieldo">
            <label htmlFor="password" className="labels2">
              Password:
            </label>
            <input
              className="inputs"
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          {errors.password && (
            <small className="errors">{errors.password}</small>
          )}
          <div className="captcha-div">
            <div className="captcha-display">{captchaValue}</div>
            <div className="contents-captcha">
              <label htmlFor="captcha" className="captcha-label">
                Captcha:
              </label>
              <input
                className="input-captcha"
                type="text"
                id="captcha"
                value={captcha}
                onChange={(e) => setCaptcha(e.target.value)}
              />
              <button
                type="button"
                className="btn-refresh"
                onClick={generateCaptcha}
              >
                <RefreshIcon width={20} height={20} fill="#333" />
              </button>
            </div>

            {errors.captcha && (
              <small className="errors">{errors.captcha}</small>
            )}
          </div>
          <button type="submit" className="login-btn">
            Login
          </button>
        </form>
      </div>
    </div>
  );
}

export default LoginForm;
