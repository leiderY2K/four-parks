import { useState, useEffect } from 'react';
import Header from '../../components/client/Header.jsx'
import ReservationInfo from '../../components/client/ReservationInfo.jsx';
import axios from "axios";

const ReservationPage = ({ url }) => {
  const [resState, setResState] = useState('');
  const [reservations, setReservations] = useState([]);

  const user = JSON.parse(sessionStorage.getItem('userLogged'));
  const idNumber = user.idNumber;
  const idType = user.idType;
  /*const idNumber = "6543210987654"
  const idType = "TI"
  const params = {};*/
  
  useEffect(() => {
    const token = sessionStorage.getItem('token').replace(/"/g, '');
    axios.post(`http://localhost:8080/client/postReservations`, {
      clientId: {
        idUser: idNumber, idDocType: idType
      },
 
      ...(resState ? {status: resState} : {})


    }, { headers: { Authorization: `Bearer ${token}` } })
      .then(res => {
        const reservationArray = []; 
        res.data.map(reservation => {reservationArray.push(reservation)})

        setReservations(reservationArray);
      })
      .catch(err => {
        console.log(err);
      })
  },[resState]);

  // Pruebas de iniciar reservación - = Juan Validar Error que sale en consola / Verificar si está bien 

  return (
    <>
      <Header />

    <section className='w-full h-24 px-12'>
      <section className="flex justify-between py-40">
        <select id="parking-type" value={resState} className="w-1/4 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setResState(e.target.value)}>
          <option value="" disabled selected hidden> Estado de reserva </option>
          <option value=""></option>
          <option value="PEN"> Pendiente </option>
          <option value="CON"> Confirmada </option>
          <option value="CAN"> Cancelada </option>
          <option value="CUR"> En curso </option>
          <option value="COM"> Completada </option>
          <option value="NPR"> No presentado </option>
        </select>
      </section>
      </section>

      <div className='flex justify-between flex-wrap px-12 pt-32 pb-10 bg-gray-light'>
        {
          reservations.map(reservation => (
            <ReservationInfo key={reservation.idReservation} url={url} reservation={reservation} />
          ))
        }
      </div>
    </>
  )
}

export default ReservationPage;
