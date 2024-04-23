import Header from '../../components/client/Header.jsx'
import ParkingFilters from '../../components/client/ParkingFilters.jsx'

const Home = () => {
  return (
    <>
        <Header />

        <div className='bg-white border-2 mt-24 border-red-700'>
            <ParkingFilters />
        </div>
    </>
  )
}

export default Home