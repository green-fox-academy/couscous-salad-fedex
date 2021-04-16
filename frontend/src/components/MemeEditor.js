import React, { useCallback, useState } from 'react';
import { Stage, Layer, Image, Text } from 'react-konva';
import useImage from 'use-image';
import { AddImageModal } from './AddImageModal';
import './MemeEditor.css';
import Cookies from 'universal-cookie';

const URLImage = ({ image }) => {
  const [img] = useImage(image.src);
  return (
    <Image
      image={img}
      x={image.x}
      y={image.y}
      offsetX={img ? img.width / 2 : 0}
      offsetY={img ? img.height / 2 : 0}
    />
  );
};

const MemeEditor = () => {

  const url = process.env.REACT_APP_API_URL;

//  const dragUrl = React.useRef();
  const stageRef = React.useRef();

  const [showModal, setShowModal] = useState(false);
  const [imageUrl, setImageUrl] = useState('');
  const [images, setImages] = React.useState([]);

  /* setImages(
    images.concat([
      {
        ...URLImage(),
      },
    ])
  ); */


  const handleImgDragStart = (e) => {
    const src = e.target.src();
    setImages(
      images.map((image) => {
        console.log(image);
        return {
          ...image,
          isDragging: image.src === src,
        };
      })
    );
  };
  const handleImgDragEnd = (e) => {
    setImages(
      images.map((image) => {
        return {
          ...image,
          isDragging: false,
        };
      })
    );
  };

  function downloadURI(uri, name) {
    const link = document.createElement('a');
    link.download = name;
    link.href = uri;

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  const accessToken = new Cookies().get('Meme-token');

  const handleClick = async e => {
    e.preventDefault();
    console.log('hello');
    const uri = stageRef.current.toDataURL();

    downloadURI(uri, 'stage.png');

    const cloudinaryURL = process.env.REACT_APP_CLOUDINARY_UPLOAD_URL;
    const uploadPreset = process.env.REACT_APP_CLOUDINARY_UNSIGNED_UPLOAD_PRESET;

    const formData = new FormData();
    formData.append('file', uri);
    formData.append('upload_preset', uploadPreset);

    let memeURL;

    try {
      const response = await fetch(cloudinaryURL, {
        method: 'POST',
        body: formData,
      });
      const responseBody = await response.json();

      if (response.status !== 200) {
        throw Error(responseBody.error);
      }
      memeURL = responseBody.secure_url
    } catch (err) {
      console.log('errorCloudinary', err);
    }

    const optionsPostMeme = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${accessToken}` },
      body: JSON.stringify({ 'meme_path': memeURL }),
    };

    try {
      const response = await fetch(`${url}/meme`, optionsPostMeme);
      const responseBody = await response.json();
      console.log(response);
      if (response.status !== 200) {
        throw Error(responseBody.error);
      }
      console.log('successFetchPostMeme', response);
    } catch (err) {
      console.log('fetchPostMeme', err);
    }
  };

  const openModal = () => setShowModal(true);
  const closeModal = () => setShowModal(false);

  const wrapperSetImageUrl = useCallback(
    val => {
      setImageUrl(val);
    },
    [setImageUrl]
  );

  const MemeImage = () => {
    const [image] = useImage(imageUrl);
    return <Image image={image} />;
  };

  return (
    <div>
      <div>
        <button className="memegenerator-btn" type="button" onClick={openModal}>
          Image
        </button>
        <AddImageModal closeModal={closeModal} showModal={showModal} imageUrlSetter={wrapperSetImageUrl} />
      </div>
      <div className="meme-editor">
        <Stage width={800} height={600} ref={stageRef}>
          <Layer>
            <MemeImage />
            {images.map((image) => (
              <URLImage
                image={image}
                key={image.id}
                id={image.id}
                x={image.x}
                y={image.y}
                draggable
                onDragStart={handleImgDragStart}
                onDragEnd={handleImgDragEnd}
              />
            ))}
            <Text
              text="Text 1"
              x={20}
              y={5}
              scaleX={4}
              scaleY={4}
            />
            <Text
              text="Text 2"
              x={20}
              y={555}
              scaleX={4}
              scaleY={4}
            />
          </Layer>
        </Stage>
      </div>
      <button className="memegenerator-btn" type="button" onClick={handleClick}>
        Generate meme
      </button>
    </div>
  );
};

export default MemeEditor;
