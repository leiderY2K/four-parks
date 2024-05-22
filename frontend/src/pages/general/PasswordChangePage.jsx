import React from 'react';
//import Login from "../../components/general/Login";
import QuotaManager from "../../components/admin/QuotaManager"
import bgImagePC from '../../assets/BG-PC.jpeg';
import bgImageMobile from '../../assets/BG-Mobile.jpeg';
import { useState, useEffect } from 'react';
import PasswordChange from '../../components/general/PasswordChange';

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

const PasswordChangePage = ({ url }) => {
  const [width] = useWindowSize();
  const backgroundImage = width <= 414 ? bgImageMobile : bgImagePC;


  return (
    <div className="h-screen" style={{backgroundImage: `url(${backgroundImage})`, backgroundSize: 'cover', backgroundPosition: 'center'}}>
        <div className="flex items-center justify-center h-screen">
            <PasswordChange url={url} />
        </div>
    </div>
  );
}

export default PasswordChangePage;
