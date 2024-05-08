import { useEffect, useState } from "react";
import "./Assignedlist.css";
function GetmyBooks() {
  const [Assignedbooks, Setassignedbooks] = useState();
  useEffect(() => {
    let ignore = false;
    if (!ignore) getassignedbooks();
    return () => {
      ignore = true;
    };
  }, []);
  async function getassignedbooks() {
    const assignbook = await fetch(`http://localhost:8080/book/getmybooks`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "POST",
      },
      // body: formDataToSend,
    });

    const response = await assignbook.json();

    console.log(response);
    if (response) {
      console.log(response);
      Setassignedbooks(response);
      //   alert("Book assigned Succesfully");
    }
  }

  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>Book Name</th>
            <th>Author</th>
            <th>Category</th>
            <th>Publish Year</th>
            <th>Image</th>
          </tr>
        </thead>
        <tbody>
          {Assignedbooks?.map((book) => (
            <tr key={book.bookid}>
              <td>{book.name}</td>
              <td>{book.author}</td>
              <td>{book.category}</td>
              <td>{book.publishyear}</td>
              <td>
                <img
                  src={`data:image/jpeg;base64,${book.imageofbook}`}
                  alt={book.name}
                  style={{ maxWidth: "100px" }}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default GetmyBooks;
