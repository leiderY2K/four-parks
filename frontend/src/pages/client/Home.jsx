import Header from '../../components/client/Header.jsx'
import Map from '../../components/client/Map.jsx'
import ParkingFilters from '../../components/client/ParkingFilters.jsx'
import ParkingInfo from '../../components/client/ParkingInfo.jsx'

const Home = () => {
  return (
    <>
        <Header />
        <div className='flex h-screen px-12 py-40 bg-gray-light'> 
          <section className='w-2/5'>
            <ParkingFilters />
            <ParkingInfo />
          </section>

          <div className="w-1/2 ml-44 rounded-2xl"> 
            <Map />
          </div>
        </div>
    </>
  )
}

export default Home;
