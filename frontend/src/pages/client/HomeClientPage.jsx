import { useState } from "react";
import Header from '../../components/client/Header.jsx'
import Map from '../../components/client/Map.jsx'
import ParkingFilters from '../../components/client/ParkingFilters.jsx'
import ParkingInfo from '../../components/client/ParkingInfo.jsx'
import ReservationTarjet from "../../components/client/ReservationTarjet.jsx";

const Home = ({url}) => {
  const [city, setCity] = useState("");
  const [parkingType, setParkingType] = useState("");
  const [availability, setAvailability] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
//PASAR USESTATE AL MAPA, Recibirlos, añadirlos al usefect y validarlos  API: Detectar ubicación 
  return (
    <>
        <Header />
        <div className='flex h-screen px-12 py-40 bg-gray-light'> 
          <section className='w-2/5'>
            <ParkingFilters city={city} setCity={setCity} parkingType={parkingType} setParkingType={setParkingType} availability={availability} setAvailability={setAvailability}
            startTime={startTime} setStartTime={setStartTime} endTime={endTime} setEndTime={setEndTime} />
            <ParkingInfo />
          </section>

          <div className="w-1/2 ml-44 rounded-2xl z-0"> 
            <Map url={url} city={city} parkingType={parkingType} availability={availability} startTime={startTime} endTime={endTime}/>
          </div>
        </div>
    </>
  )
}

export default Home;
