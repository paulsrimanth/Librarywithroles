import { useNavigate } from "react-router-dom";
import "./librarypage.css";
import BooksDisplaypage from "./BooksDisplaypage";
import { useRef } from "react";
import { useEffect, useState } from "react";
function Librarypage() {
  const navigate = useNavigate();
  const [bookdata, Setbookdata] = useState();
  const [Catclicked, Setcatclicked] = useState(false);
  const [categorydisplay, Setcategorydisplay] = useState("");
  const [displaybycat, Setdisplaybycat] = useState();
  const [bookid, Setbookid] = useState("");
  useEffect(() => {
    let ignore = false;

    if (!ignore) getBooks();
    return () => {
      ignore = true;
    };
    // getBooks();
  }, []);
  function handlelogout() {
    localStorage.removeItem("token", "roles");
    navigate("/login");
  }

  function getbookid(id) {
    localStorage.setItem("bookid", id);
    console.log("library page starts");
    console.log(id);
    Setbookid(id);
    // console.log(bookdata[0]);
    // console.log(typeof id + "id");
    // console.log(Number(id) + "id");
    console.log(localStorage.getItem("bookid"));
    console.log("library page ends");
    navigate("/assignbook");
  }
  async function getBooks() {
    const gotadmins = await fetch("http://localhost:8080/book/fetchbooks", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "POST",
      },
    });

    const adminres = await gotadmins.json();
    console.log(adminres);
    if (adminres) {
      Setbookdata(adminres);
    }
  }
  async function getcategory(category) {
    console.log(category + "cat");
    Setcatclicked(!Catclicked);
    Setcategorydisplay(category);
    console.log(categorydisplay);
    const categorywise = await fetch(
      `http://localhost:8080/book/category/${category}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
      }
    );

    const response = await categorywise.json();
    console.log(response);
    if (response) {
      Setdisplaybycat(response);
    }
  }
  // if (Catclicked) {
  //   return (
  //     <div>
  //       <div className="outer-div">
  //         {displaybycat?.map((book) => (
  //           <div key={book.bookid} className="books-display">
  //             <div
  //               className="cards"
  //               // onClick={(e) => getbookid(e.currentTarget.id)}
  //               id={book.bookid}
  //             >
  //               <img
  //                 className="book-image"
  //                 src={`data:image/jpeg;base64,${book.imageofbook}`}
  //                 alt="Hot air balloons"
  //               />

  //               <p className="content">
  //                 <i>{book.name}</i>
  //                 by
  //                 <b> {book.author}</b>
  //               </p>
  //             </div>
  //           </div>
  //           // </div>
  //         ))}
  //       </div>
  //     </div>
  //   );
  // }

  return (
    <div className="body">
      <div className="navigation-bar">Welcome To Library 📖</div>
      <div className="side-bar">
        <div className="buttons">
          {localStorage.getItem("roles") === "POWER" ? (
            <button
              className="button-side"
              onClick={() => navigate("/addusers")}
            >
              ADD ADMIN
            </button>
          ) : (
            ""
          )}

          {localStorage.getItem("roles") === "POWER" ||
          localStorage.getItem("roles") === "ADMIN" ? (
            <button
              className="button-side"
              onClick={() => navigate("/adduser")}
            >
              ADD USERS ➕
            </button>
          ) : (
            ""
          )}

          {localStorage.getItem("roles") === "POWER" ? (
            <>
              <button
                className="button-side"
                onClick={() => navigate("/showadmins")}
              >
                SHOW ADMINS
              </button>
              <button
                className="button-side"
                onClick={() => navigate("/allusers")}
              >
                SHOW USERS
              </button>
            </>
          ) : (
            ""
          )}
          {localStorage.getItem("roles") === "ADMIN" ? (
            <button
              className="button-side"
              onClick={() => navigate("/uploadbook")}
            >
              Add Book ➕
            </button>
          ) : (
            ""
          )}
          {localStorage.getItem("roles") === "USER" ? (
            <button
              className="button-side"
              onClick={() => navigate("/getmybook")}
            >
              Get My Books📘
            </button>
          ) : (
            ""
          )}
          <button
            className="button-side"
            value="Fiction"
            onClick={(e) => getcategory(e.currentTarget.value)}
          >
            Fiction 🔮
          </button>
          <button
            className="button-side"
            value="History"
            onClick={(e) => getcategory(e.currentTarget.value)}
          >
            History
          </button>
          <button
            className="button-side"
            value="Sports"
            onClick={(e) => getcategory(e.currentTarget.value)}
          >
            Sports 🏑
          </button>
          <button
            className="button-side"
            value="Comics"
            onClick={(e) => getcategory(e.currentTarget.value)}
          >
            Comics
          </button>
          <button
            className="button-side"
            value="Animation"
            onClick={(e) => getcategory(e.currentTarget.value)}
          >
            Animation
          </button>
          <button className="button-logout" onClick={() => handlelogout()}>
            Logout
          </button>
        </div>
      </div>

      {!Catclicked ? (
        <div className="outer-div">
          {bookdata?.map((book) => (
            <div key={book.bookid} className="books-display">
              <div
                className="cards"
                onClick={(e) => getbookid(e.currentTarget.id)}
                id={book.bookid}
              >
                <img
                  className="book-image"
                  src={`data:image/jpeg;base64,${book.imageofbook}`}
                  alt="Hot air balloons"
                />
                <p className="content">
                  <i>{book.name} </i>
                  by
                  <b> {book.author}</b>
                </p>
              </div>
            </div>
            // </div>
          ))}
        </div>
      ) : (
        <div className="outer-div">
          {displaybycat?.map((book) => (
            <div key={book.bookid} className="books-display">
              <div
                className="cards"
                // onClick={(e) => getbookid(e.currentTarget.id)}
                id={book.bookid}
              >
                <img
                  className="book-image"
                  src={`data:image/jpeg;base64,${book.imageofbook}`}
                  alt="Hot air balloons"
                />

                <p className="content">
                  <i>{book.name}</i>
                  by
                  <b> {book.author}</b>
                </p>
              </div>
            </div>
            // </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Librarypage;
