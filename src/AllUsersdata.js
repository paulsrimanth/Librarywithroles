import { useEffect, useState } from "react";

function AllUsersdata() {
  const [userlist, setuserlist] = useState();
  useEffect(() => {
    // let ignore = false;
    // if (!ignore) getAdmins();
    // return () => {
    //   ignore = true;
    // };
    getallusers();
  }, []);

  async function getallusers() {
    const gotadmins = await fetch("http://localhost:8080/user/allusers", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "POST",
      },
    });

    const adminres = await gotadmins.json();
    if (adminres) {
      setuserlist(adminres);
    }
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
            {userlist?.map((item) => (
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

export default AllUsersdata;
