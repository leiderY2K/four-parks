import { useEffect, useState } from "react";
import axios from "axios";

const ParkingFilters = ({url, city, setCity, parkingType, setParkingType, availability, setAvailability, vehicleType, setVehicleType, date, setDate, startTime, 
setStartTime, endTime, setEndTime}) => {
  const [cities, setCities] = useState([]);

  useEffect(() => {
    const token = sessionStorage.getItem('token').replace(/"/g, '');

    axios.get(`${url}/city/list`,  {headers: {Authorization: `Bearer ${token}`}})
    .then(res=>{
      const cityArray = res.data.map(city => (city))
      setCities(cityArray);
    })
    .catch(err=>{
      console.log(err);
    }) 
  },[]); 

  const handleTimeChange = (setter) => (event) => {
    const time = new Date(event.target.valueAsNumber);
    if (time) {
      time.setMinutes(0);
      time.setSeconds(0);
      setter(time.toISOString().substr(11, 8));
    }
  };

  return (
    <section className="flex flex-col justify-between w-full h-52">
      <style>
        {`
          input[type="time"]::-webkit-datetime-edit-minute,
          input[type="time"]::-webkit-datetime-edit-second,
          input[type="time"]::-webkit-datetime-edit-millisecond,
          input[type="time"]::-webkit-inner-spin-button,
          input[type="time"]::-webkit-clear-button {
            display: none;
          }
        `}
      </style>

      <section className="flex justify-between">
        <select id="parking-city" value={city} className="w-1/2 mr-12 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setCity(e.target.value)}>
          <option value="" disabled selected hidden> Ciudad </option>
          <option value=""></option>
          {cities.map((city, index) => (
            <option key={index} value={city}> {city} </option>
          ))}
        </select>
        
        <select id="parking-type" value={parkingType} className="w-1/2 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setParkingType(e.target.value)}>
          <option value="" disabled selected hidden> Tipo de parqueadero </option>
          <option value=""></option>
          <option value="COV"> Cubierto </option>
          <option value="SEC"> Semi-cubierto </option>
          <option value="UNC"> Descubierto </option>
        </select>
      </section>
      
      <section className="flex justify-between">
        <select id="parking-availability" value={availability} className="w-1/2 mr-12 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setAvailability(e.target.value)}>
          <option value="" disabled selected hidden> Disponibilidad </option>
          <option value=""></option>
          <option value="Dias de semana"> Lunes a viernes </option>
          <option value="Fines de semana"> Fines de semana </option>
          <option value="Todos los dias"> Todos los días </option>
        </select>
        
        <select id="parking-vehicleType" value={vehicleType} className="w-1/2 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setVehicleType(e.target.value)}>
          <option value="" disabled selected hidden> Tipo de vehículo </option>
          <option value=""></option>
          <option value="CAR"> Automóvil </option>
          <option value="MOT"> Motocicleta </option>
          <option value="BIC"> Bicicleta </option>
        </select>
      </section>
      
      <section className="flex justify-between w-3/4">
        <input type="date" id="date" value={date} className="p-4 rounded-md bg-white shadow-md  font-paragraph" onChange={(e) => setDate(e.target.value)}></input>

        <section className="flex justify-between items-center">
          <input type="time" id="parking-startTime" value={startTime} className="ml-12 p-4 rounded-md bg-white shadow-md font-paragraph" 
          onChange={handleTimeChange(setStartTime)}/>

          <span className="w-5 h-0.5 mx-4 rounded-full bg-black"></span>
          
          <input type="time" id="parking-endTime" value={endTime} className="p-4 rounded-md bg-white shadow-md font-paragraph" 
          onChange={handleTimeChange(setEndTime)}/>
        </section>
      </section>
    </section>
  );
}

export default ParkingFilters;