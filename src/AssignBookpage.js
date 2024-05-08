import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import "./assignbok.css";
function AssignBookpage() {
  //   const { bookdata } = location.state || {};
  const [book, setbook] = useState(null);
  const [emailtoken, setemailtoken] = useState();
  useEffect(() => {
    let ignore = false;

    if (!ignore) getBook();
    return () => {
      ignore = true;
    };
  }, []);
  async function getBook() {
    console.log("gt book");
    const mail = jwtDecode(localStorage.getItem("token"));
    console.log(mail.sub);
    setemailtoken(mail.sub);
    console.log("emailtokem" + emailtoken);
    const bookdata = await fetch(
      `http://localhost:8080/book/getbookdata/${localStorage.getItem(
        "bookid"
      )}`,
      {
        method: "GET",
        headers: {
          // Authorization: `Bearer ${localStorage.getItem("tosken")}`,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
      }
    );
    const gotbook = await bookdata.json();
    console.log(gotbook);
    setbook(gotbook);
    console.log(book);
    // console.log(book.users.email);
    console.log(emailtoken);
    // if (gotbook) {
    //   console.log("setting book data");
    //   setbook(gotbook);
    //   console.log("book  ...");
    //   console.log(book);
    //   // console.log(book.bookid + "idd");
    // }
  }

  async function assign() {
    const assignbook = await fetch(
      `http://localhost:8080/book/assignbooks/${localStorage.getItem(
        "bookid"
      )}`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
        // body: formDataToSend,
      }
    );

    const response = await assignbook.json();

    console.log(response);
    if (response) {
      alert("Book assigned Succesfully");
    }
  }
  async function releasebook() {
    const releasebook = await fetch(
      `http://localhost:8080/book/releasebook/${localStorage.getItem(
        "bookid"
      )}`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
        // body: formDataToSend,
      }
    );

    const response = await releasebook.json();

    console.log(response);
    console.log(response.users);
    if (response) {
      alert("Book released Succesfully");
    }
    if (response.users.length === 0) {
      alert("you haven't assigned this book");
    }
  }

  return (
    <div className="book-page">
      {book ? (
        <div className="book-container">
          <img
            className="book-image"
            src={`data:image/jpeg;base64,${book.imageofbook}`}
            alt={book.title}
          />
          <div className="book-details">
            <h2>{book.title}</h2>
            <p>Author: {book.author}</p>
            <p>Title: {book.name}</p>
            <p>Published year: {book.publishyear}</p>
            <p>category: {book.category}</p>
          </div>
          <div className="book-buttons">
            {localStorage.getItem("roles") === "USER" &&
            !(book.users && book.users.email === emailtoken) ? (
              <button onClick={() => assign()}>Assign</button>
            ) : (
              ""
            )}
            {localStorage.getItem("roles") === "USER" ? (
              <button onClick={() => releasebook()}>Release</button>
            ) : (
              ""
            )}
          </div>
        </div>
      ) : (
        ""
      )}
    </div>
  );
}

export default AssignBookpage;
