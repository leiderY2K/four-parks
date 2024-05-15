import { useEffect, useState } from "react";
import Header from "../../components/admin/Header"
import Map from '../../components/admin/Map.jsx'
import ParamsInfo from '../../components/admin/ParamsInfo.jsx'
import QuotaManager from "../../components/admin/QuotaManager.jsx";

const HomeAdminPage = ({url, initialCoords}) => {
  const [city, setCity] = useState("");
  const [actualCity, setActualCity] = useState({
    id: 'BGT',
    name: 'Bogota',
    northLim: [4.7694, -74.2034],
    southLim: [4.4861, -74.0232],
    centerCoords: [4.6596, -74.0915]
  });
  const [actualParking, setActualParking] = useState();
  const [canEditSpaces, setCanEditSpaces] = useState(false);

  useEffect(() => {
    if(initialCoords.length > 0) {
      setActualCity({
        id: 'BGT',
        name: 'Bogota',
        northLim: [4.7694, -74.2034],
        southLim: [4.4861, -74.0232],
        centerCoords: initialCoords
      });
    }
  }, [initialCoords]);

  useEffect(() => {
    setActualParking();
  }, [city])

  return (
    <>
        <Header />

        <div className='flex h-screen px-12 py-40 bg-gray-light'> 
          
          <div className="w-1/2 rounded-2xl z-0"> 
            <Map url={url} city={city} actualCity={actualCity} setActualCity={setActualCity} setActualParking={setActualParking} />
          </div>

          <section className='w-2/5 ml-48'>
            {
              !canEditSpaces ? (<ParamsInfo url={url} actualParking={actualParking} setCanEditSpaces={setCanEditSpaces} />) : 
              (<QuotaManager setCanEditSpaces={setCanEditSpaces} />)
            }
          </section>
        </div>
    </>
  )
}

export default HomeAdminPage