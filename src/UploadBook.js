import { useState } from "react";
import "./uploadbook.css";
function UploadBook() {
  const [name, Setname] = useState("");
  const [author, Setauthor] = useState("");
  const [publishyear, Setpublishyear] = useState("");
  const [category, Setcategory] = useState("");
  const [imageofbook, Setimageofbook] = useState();
  const [errors, setErrors] = useState({});
  const formdata = {
    imageofbook,
    name,
    author,
    publishyear,
    category,
  };
  const formDataToSend = new FormData();
  formDataToSend.append("imageofbook", formdata.imageofbook);
  formDataToSend.append("name", formdata.name);
  formDataToSend.append("author", formdata.author);
  formDataToSend.append("publishyear", formdata.publishyear);
  formDataToSend.append("category", formdata.category);

  const handleSubmit = (e) => {
    console.log(localStorage.getItem("token"));
    e.preventDefault();
    const errors = {};
    async function AddBook() {
      const addbook = await fetch("http://localhost:8080/book/upload", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "POST",
        },
        body: formDataToSend,
      });

      const adminres = await addbook.json();
      console.log(adminres);
      if (adminres) {
        console.log("added");
        alert("Added book succesfully");
      } else {
        console.log("error adding book");
      }
    }
    if (!name) {
      errors.name = "*title is required";
    }
    if (!author) {
      errors.author = "*author is required";
    }
    if (!publishyear) {
      errors.publishyear = "*publish year is required";
    }
    if (!category) {
      errors.category = "*category is required";
    }
    if (!imageofbook) {
      errors.imageofbook = "*image is required";
    }
    if (Object.keys(errors).length === 0) {
      AddBook();
    } else {
      setErrors(errors);
    }

    // createuser();
  };
  return (
    <div>
      <div className="up-form-container">
        <form onSubmit={handleSubmit} className="up-registration-form">
          <div>
            <h2>Upload Book 📚</h2>
          </div>
          <div className="up-form-row">
            <label htmlFor="email" className="up-input-label">
              TITLE:
            </label>
            <input
              className="up-input-field"
              type="text"
              id="email"
              value={name}
              onChange={(e) => Setname(e.target.value)}
            />
            {errors.name && (
              <small className="error-message">{errors.name}</small>
            )}
          </div>
          <div className="up-form-row">
            <label htmlFor="author" className="up-input-label">
              author:
            </label>
            <input
              className="up-input-field"
              type="text"
              id="author"
              value={author}
              onChange={(e) => Setauthor(e.target.value)}
            />
            {errors.author && (
              <small className="error-message">{errors.author}</small>
            )}
          </div>
          <div className="up-form-row">
            {/* //make this drop down */}
            <label htmlFor="publishyear" className="up-input-label">
              published year:
            </label>
            <input
              className="up-input-field"
              type="text"
              id="publishyear"
              value={publishyear}
              onChange={(e) => Setpublishyear(e.target.value)}
            />
            {errors.publishyear && (
              <small className="error-message">{errors.publishyear}</small>
            )}
          </div>
          <div className="up-form-row">
            {/* //make this drop down */}
            <label htmlFor="category" className="up-input-label">
              Category
            </label>
            <input
              className="up-input-field"
              type="text"
              id="category"
              value={category}
              onChange={(e) => Setcategory(e.target.value)}
            />
            {errors.category && (
              <small className="error-message">{errors.category}</small>
            )}
          </div>
          <div className="up-form-row">
            {/* //make this drop down */}
            <label htmlFor="imageofbook" className="up-input-label">
              Image Of Book
            </label>
            <input
              className="up-input-field"
              type="file"
              id="imageofbook"
              // value={imageofbook}
              name="imageofbook"
              onChange={(e) => Setimageofbook(e.target.files[0])}
            />
            {errors.imageofbook && (
              <small className="error-message">{errors.imageofbook}</small>
            )}
          </div>
          <button type="submit">Add Book</button>
        </form>
      </div>
    </div>
  );
}

export default UploadBook;
// let formDataToSend = new FormData();
// console.log(formData);
// formDataToSend.append("titleofbook", formData.titleofbook);
// formDataToSend.append("author", formData.author);
// formDataToSend.append("publishyear", formData.publishyear);
// formDataToSend.append("imageofbook", formData.imageofbook);
// console.log(formDataToSend.getAll);
// try {
//   let response = await fetch(
//     `http://localhost:8090/auth/book/upload/${cname}`,
//     {
//       method: "POST",
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem("token")}`,
//       },

//       body: formDataToSend,
//     }
//   );
