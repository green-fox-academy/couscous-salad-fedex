import React, { useCallback, useState } from 'react';
import { Stage, Layer, Image, Star, Text } from 'react-konva';
import useImage from 'use-image';
import { AddImageModal } from './AddImageModal';
import './MemeEditor.css';
import Cookies from 'universal-cookie';

function generateShapes() {
  return [...Array(10)].map((_, i) => ({
    id: i.toString(),
    x: Math.random() * window.innerWidth,
    y: Math.random() * window.innerHeight,
    rotation: Math.random() * 180,
    isDragging: false,
  }));
}

const INITIAL_STATE = generateShapes();



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

  const dragUrl = React.useRef();
  const stageRef = React.useRef();
// const stageRef = React.useRef(null);


  const [showModal, setShowModal] = useState(false);
  const [imageUrl, setImageUrl] = useState('');
  const [images, setImages] = React.useState([]);
  const [stars, setStars] = React.useState(INITIAL_STATE);

  const handleDragStart = (e) => {
    const id = e.target.id();
    setStars(
      stars.map((star) => {
        return {
          ...star,
          isDragging: star.id === id,
        };
      })
    );
  };
  const handleDragEnd = (e) => {
    setStars(
      stars.map((star) => {
        return {
          ...star,
          isDragging: false,
        };
      })
    );
  };


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


console.log(imageUrl);



  return (
    <div>
      <div>
        <button className="memegenerator-btn" type="button" onClick={openModal}>
          Image
        </button>
        <AddImageModal closeModal={closeModal} showModal={showModal} imageUrlSetter={wrapperSetImageUrl} />
      </div>

      <div>
      <img
        alt="lion"
        src={imageUrl}
        draggable="true"
        onDragStart={(e) => {
          dragUrl.current = e.target.src;
        }}
      />
        <Stage width={800} height={600} ref={stageRef}>
          <Layer>
            <Text text="Try to drag a star" />
            {stars.map((star) => (
              <Star
                key={star.id}
                id={star.id}
                x={star.x}
                y={star.y}
                numPoints={5}
                innerRadius={20}
                outerRadius={40}
                fill="#f8f409"
                opacity={0.8}
                draggable
                rotation={star.rotation}
                shadowColor="black"
                shadowBlur={10}
                shadowOpacity={0.6}
                shadowOffsetX={star.isDragging ? 10 : 5}
                shadowOffsetY={star.isDragging ? 10 : 5}
                scaleX={star.isDragging ? 1.2 : 1}
                scaleY={star.isDragging ? 1.2 : 1}
                onDragStart={handleDragStart}
                onDragEnd={handleDragEnd}
              />
            ))}

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
