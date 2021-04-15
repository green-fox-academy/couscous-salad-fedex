import { useState, useEffect } from 'react';

const useFetch = url => {
      console.log(url);
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);
  const [isPending, setIsPending] = useState(true);

  useEffect(() => {
    const abortCont = new AbortController();
    //const userToken = localStorage.getItem('token');

    fetch(url, {
      signal: abortCont.signal,
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        if (!response.ok) {
          return new Promise((resolve, reject) => {
            response.json().then(resError => {
              reject(resError);
            });
          });
        }
        return response.json();
      })
      .then(response => {
        setData(response);
        setIsPending(false);
        setError(null);
      })
      .catch(err => {
        if (err.name === 'AbortError') {
          console.log('fetch aborted');
        } else {
          console.log(err);
          setIsPending(false);
          setError('Server error');
        }
      });

    return () => abortCont.abort();
  }, [url]);

  return { data, error, isPending };
};

export default useFetch;
