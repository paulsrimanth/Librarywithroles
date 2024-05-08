import { useEffect, useState } from "react";
import "./showadmins.css";
function Showadmins() {
  //shows admins to poweruser\
  const [admindata, Setadmindata] = useState([]);

  const createuserbody = {};
  useEffect(() => {
    // let ignore = false;

    // if (!ignore) getAdmins();
    // return () => {
    //   ignore = true;
    // };
    getAdmins();
  }, []);
  async function getAdmins() {
    const gotadmins = await fetch("http://localhost:8080/roles/usersbypower", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "POST",
      },
    });

    const adminres = await gotadmins.json();
    Setadmindata(adminres);
  }
  return (
    <div className="showadmin-body">
      <div className="admins-table">
        <table>
          <thead>
            <tr>
              <th>id</th>
              <th>EMAIL</th>

              <th>ROLE</th>
            </tr>
          </thead>
          <tbody>
            {admindata.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.email}</td>
                <td>{item.roles}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Showadmins;
