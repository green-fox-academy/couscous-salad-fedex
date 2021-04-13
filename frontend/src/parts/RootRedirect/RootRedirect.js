import React from 'react';
import { Redirect } from 'react-router-dom';
import Cookies from 'universal-cookie';

const RootRedirect = () => {
  const cookie = new Cookies();
  return cookie.get('accessToken') ? (
    <Redirect to="/main" />
  ) : (
    <Redirect to="/login" />
  );
};

export default RootRedirect;
