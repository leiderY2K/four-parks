import { useState } from "react";

const ParkingFilters = () => {
  const [city, setCity] = useState("");
  const [parkingType, setParkingType] = useState("");
  const [availability, setAvailability] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");

  const handleTimeChange = (setter) => (event) => {
    const time = new Date(event.target.valueAsNumber);
    if (time) {
      time.setMinutes(0);
      time.setSeconds(0);
      setter(time.toISOString().substr(11, 5));
    }
  };

  return (
    <section className="flex flex-col justify-between w-full h-36">
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
          <option value="bogota"> Bogotá </option>
          <option value="medellin"> Medellín </option>
          <option value="cali"> Cali </option>
        </select>
        
        <select id="parking-type" value={parkingType} className="w-1/2 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setParkingType(e.target.value)}>
          <option value="" disabled selected hidden> Tipo de parqueadero </option>
          <option value="cubierto"> Cubierto </option>
          <option value="semi-cubierto"> Semi-cubierto </option>
          <option value="descubierto"> Descubierto </option>
        </select>
      </section>
      
      <section className="flex justify-between">
        <select id="parking-availability" value={availability} className="w-1/2 mr-12 p-4 rounded-md bg-white shadow-md font-paragraph" onChange={(e) => setAvailability(e.target.value)}>
          <option value="" disabled selected hidden> Disponibilidad </option>
          <option value="lunes-viernes"> Lunes a viernes </option>
          <option value="fines-semana"> Fines de semana </option>
          <option value="todo-dia"> 24/7 </option>
        </select>

        <section className="flex justify-between items-center w-1/2">
          <input type="time" id="parking-startTime" value={startTime} className="p-4 rounded-md bg-white shadow-md font-paragraph" 
          onChange={handleTimeChange(setStartTime)}/>

          <span className="w-5 h-0.5 rounded-full bg-black"></span>
          
          <input type="time" id="parking-endTime" value={endTime} className="p-4 rounded-md bg-white shadow-md font-paragraph" 
          onChange={handleTimeChange(setEndTime)}/>
        </section>
      </section>
    </section>
  );
}

export default ParkingFilters;