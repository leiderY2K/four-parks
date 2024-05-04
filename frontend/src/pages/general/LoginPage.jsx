import React from 'react';
import Login from "../../components/general/Login";
import bgImagePC from '../../assets/BG-PC.jpeg';
import bgImageMobile from '../../assets/BG-Mobile.jpeg';
import { useState, useEffect } from 'react';

function useWindowSize() {
  const [size, setSize] = useState([window.innerWidth, window.innerHeight]);

  useEffect(() => {
    const handleResize = () => {
      setSize([window.innerWidth, window.innerHeight]);
    };

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return size;
}

const LoginPage = ({ url }) => {
  const [width] = useWindowSize();
  const backgroundImage = width <= 414 ? bgImageMobile : bgImagePC;

  useEffect(() => {
    sessionStorage.removeItem("userLogged");
    sessionStorage.removeItem("token");
  }, []);

  return (
    <div className="h-screen" style={{backgroundImage: `url(${backgroundImage})`, backgroundSize: 'cover', backgroundPosition: 'center'}}>
        <div className="flex items-center justify-center h-screen w-full">
            <Login url={url} />
        </div>
    </div>
  );
}

export default LoginPage;
