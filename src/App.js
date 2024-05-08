import AddUser from "./AddUser";
import Addusers from "./Addusers";
import "./App.css";
import Librarypage from "./Librarypage";
import LoginForm from "./LoginForm";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Showadmins from "./Showadmins";
import UploadBook from "./UploadBook";
import AssignBookpage from "./AssignBookpage";
import GetmyBooks from "./GetmyBooks";
import AllUsersdata from "./AllUsersdata";
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route index element={<LoginForm />} />
          <Route path="library" element={<Librarypage />} />
          <Route path="addusers" element={<Addusers />} />
          <Route path="adduser" element={<AddUser />} />
          <Route path="/showadmins" element={<Showadmins />} />
          <Route path="/uploadbook" element={<UploadBook />} />
          <Route path="/assignbook" element={<AssignBookpage />} />
          <Route path="/getmybook" element={<GetmyBooks />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/allusers" element={<AllUsersdata />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
