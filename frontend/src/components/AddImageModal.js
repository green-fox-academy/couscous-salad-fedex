import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import './AddImageModal.css';

export const AddImageModal = ({ showModal, closeModal, imageUrlSetter }) => {
  const [imageUrl, setImageUrl] = useState('');
  const [image, setImage] = useState({ preview: '', raw: ' '});

  const handleSubmit = e => {
    e.preventDefault();
    imageUrlSetter(imageUrl);
  };

  const handleChange = e => {
    if (e.target.files.length) {
     setImage({
        preview: URL.createObjectURL(e.target.files[0]),
        raw: e.target.files[0]
      });
      setImageUrl(URL.createObjectURL(e.target.files[0]));
    }
  };

  const modal = (
    <>
      <div className={showModal ? 'overlay' : 'hide'} onClick={closeModal} />
      <div className={showModal ? 'modal' : 'hide'}>
        <button className="modal-button" onClick={closeModal}>X</button>
        <h1>Hello</h1>
        <form className="imageModal-form" onSubmit={handleSubmit}>

        <label htmlFor="upload-button">
          {image.preview ? (
            <img src={image.preview} alt='' width='400' height='300' />
          ) : (
            <>
            <h5>Upload Image</h5>
            </>
          )}
        </label>
          <input
          type="file"
          id="upload-button"
          onChange={handleChange}
          />
          <br/>
          <input
            type="url"
            placeholder="Paste Image URL"
            value={imageUrl}
            onChange={e => {
              setImageUrl(e.target.value);
            }}
          />
          <br/>
          <button type="submit" onClick={closeModal}>Add Image</button>
        </form>
      </div>
    </>)
  return ReactDOM.createPortal(
    modal, document.getElementById('modal-root')
  );
};
