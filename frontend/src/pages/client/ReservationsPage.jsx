import Header from '../../components/client/Header.jsx'
import ReservationInfo from '../../components/client/ReservationInfo.jsx';

const ReservationPage = ({url}) => {

// Tareas: git pull origin feature/reservas 
// Ajustar Filtros de parqueaderos - William
// Ajustar Postear reservaciones - 
// Pruebas de iniciar reservación - = Juan Validar Error que sale en consola / Verificar si está bien 
// res.data.message
// Función de Axios /postreservations pasa tipo de id y trae reservas de ese cliente. status (opcional) concom

  return (
    <>
        <Header />

        <div className='flex justify-between flex-wrap px-12 pt-32 pb-10 bg-gray-light'> 
          <ReservationInfo url={url} />
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
