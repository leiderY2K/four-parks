import Header from '../../components/client/Header.jsx'
import ReservationInfo from '../../components/client/ReservationInfo.jsx';

const ReservationPage = ({url}) => {
  return (
    <>
        <Header />

        <div className='flex justify-between flex-wrap px-12 pt-32 pb-10 bg-gray-light'> 
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
          <ReservationInfo/>
        </div>
    </>
  )
}

export default ReservationPage;
