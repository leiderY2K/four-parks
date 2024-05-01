import Header from '../../components/client/Header.jsx'
import Map from '../../components/client/Map.jsx'
import ParkingFilters from '../../components/client/ParkingFilters.jsx'
import ParkingInfo from '../../components/client/ParkingInfo.jsx'
import ReservationInfo from "../../components/client/ReservationInfo.jsx"

const Home = ({url}) => {
  return (
    <>
        <Header />
        <div className='flex h-screen px-12 py-40 bg-gray-light'> 
          <section className='w-2/5'>
            <ParkingFilters />
            <ReservationInfo />
          </section>

          <div className="w-1/2 ml-44 rounded-2xl z-0"> 
            <Map />
          </div>
        </div>
    </>
  )
}

export default Home;
