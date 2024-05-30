import { useEffect, useState } from "react";
import axios from "axios";
import Header from "../../components/manager/Header.jsx"
import Map from '../../components/manager/Map.jsx'
import ParamsInfo from '../../components/manager/ParamsInfo.jsx'
import QuotaManager from "../../components/manager/QuotaManager.jsx";

const HomeManagerPage = ({url, initialCoords}) => {
  const [cities, setCities] = useState([]);
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
    const token = sessionStorage.getItem('token').replace(/"/g, '');

    axios.get(`${url}/city/list`, {headers: {Authorization: `Bearer ${token}`}})
    .then(res=>{
      const cityArray = res.data.map(city => (city))
      setCities(cityArray);
    })
    .catch(err=>{
      console.log(err);
    }) 
  },[]); 

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
    setCanEditSpaces(false);
  }, [city])

  return (
    <>
        <Header />

        <section className='flex h-screen px-12 py-36 bg-gray-light'> 
          <section className="w-1/2 rounded-2xl z-0"> 
            <select id="parking-city" value={city} className="w-1/3 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setCity(e.target.value)}>
              <option value="" disabled selected hidden> Ciudad </option>
              <option value=""></option>
              {cities.map((city, index) => (
                <option key={index} value={city}> {city} </option>
              ))}
            </select>

            <Map url={url} city={city} actualCity={actualCity} setActualCity={setActualCity} setActualParking={setActualParking} />
          </section>

          <section className='w-2/5 ml-48 mt-16 z-0'>
            {
              !canEditSpaces ? (<ParamsInfo url={url} actualCity={actualCity} actualParking={actualParking} setCanEditSpaces={setCanEditSpaces} />) : 
              (<QuotaManager url={url} actualParking={actualParking} actualCity={actualCity} setCanEditSpaces={setCanEditSpaces} />)
            }
          </section>
        </section>
    </>
  )
}

export default HomeManagerPage