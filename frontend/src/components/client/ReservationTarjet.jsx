import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import cubiertoIcon from '../../assets/Cubierto.png';

export default function ReservationTarjet({url, setOnReservationForm}) {
    const [resDate, setResDate]= useState('');
    const [resStart, setResStart] = useState('');
    const [resEnd, setResEnd] = useState('');
    const [resCreationDate, setResCreationDate] = useState('');
    const [resTotal, setResTotal] = useState('');
    const [licensePlate, setLicensePlate] = useState('');
    const [vehicleType, setVehicleType] = useState('');
    const [resPayMethod, setResPayMethod] = useState('');

    const navigate = useNavigate();
    const idNumber = sessionStorage.getItem('userLogged').idNumber;
    const idType = sessionStorage.getItem('userLogged').idType;
    
    const handleTimeChange = (setter) => (event) => {
        const time = new Date(event.target.valueAsNumber);
        if (time) {
          time.setMinutes(0);
          time.setSeconds(0);
          setter(time.toISOString().substr(11, 8));
        }
      };

    const handleReservation = (e) => {
        e.preventDefault();

        if(!resDate || !resStart || !resEnd || !licensePlate || !vehicleType || !resPayMethod) {
            Swal.fire({
                icon: 'info',
                title: `Por favor llene todos los campos`
            });
        } else {
            axios.post(`${url}/client/startReservation`, {
                dateRes: resDate, 
                startTimeRes: resStart, 
                endTimeRes: resEnd, 
                licensePlate: licensePlate,
                clientId:{idUser:idNumber,idDocType:idType},
                
                //creationDateRes: 'Hoy',
                //totalRes: "0", 
                //vehicleType: vehicleType, 
                //username: resPayMethod, 
                
            })
            .then(res => {
                console.log(res);
                
                Swal.fire({
                    icon: 'success',
                    title: `Reserva exitosa`
                });

                //navigate("/inicio-sesion");
            })
            .catch(err => {
                //console.log(res);
                Swal.fire({
                    icon: 'error',
                    title: `Hubo un error al realizar la reserva` ,
                });

                console.log(err);
            })
        }
    }


    return (
        <article className="bg-blue-light pt-5 pb-6 relative rounded-2xl shadow-xl">
            <section className="flex flex-col items-center px-5">
                <div className="w-full flex justify-between">
                    <div className="w-1/5 flex justify-center rounded-md border-2 border-black h-14 mr-4">
                            <img className="mb-1 "src={cubiertoIcon} alt="Imagen que identifica el tipo de parqueadero"/>
                    </div>
                    <div className="w-4/5 font-semibold text-lg mt-4"> Nombre del parqueadero
                        <hr className="h-0.5 mt-2 rounded-full bg-black"></hr>    
                    </div>
                    
                </div>
                <section className="flex flex-col justify-evenly items-center w-full h-22 mt-8">
                    
                
                    <div className="flex justify-between w-full mb-5">
                        <input type="date" id="resDate" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark mr-4" placeholder="Día"
                        value={resDate} onChange={(e) => setResDate(e.target.value)}></input>
                        {console.log(resDate)}
                        <input type="time" id="resStart" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark mr-4" placeholder="Hora inicio"
                        value={resStart} onChange={handleTimeChange(setResStart)}></input>
                        {console.log(resStart)}
                        <input type="time" id="resEnd"className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Hora fin"
                        value={resEnd} onChange={handleTimeChange(setResEnd)}></input>
                        {console.log(resEnd)}
                    </div>
                
                    <div className="flex justify-between w-full mb-5">
                        <select id="vehicleType" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Tipo de vehiculo"
                        value={vehicleType} onChange={(e) => setVehicleType(e.target.value)}>
                            <option value="" disabled hidden> Tipo de vehiculo </option>
                            <option value="Moto">Moto</option>
                            <option value="Carro">Carro</option>
                            
                        </select>
                        
                        <input id="licensePlate" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Matricula del vehiculo"
                        value={licensePlate} onChange={(e) => setLicensePlate(e.target.value)}></input>
                    </div>
                
                    <div className="w-full">
                        <select id="resPayMethod" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Metodo de pago"
                        value={resPayMethod} onChange={(e) => setResPayMethod(e.target.value)}>
                            <ption value="" disabled hidden> Metodo de pago </ption>
                            <option value="Mastercard">Mastercard</option>
                            <option value="Nequi">Nequi</option>
                        </select>
                    </div>
                    <div className="flex justify-between w-full mt-5">
                        <div className="text font-semibold text-lg">Total Reserva:$45,000</div>
                        <hr className="h-0.5 rounded-full bg-blue-light"></hr>
                    </div>
                    
                </section>  
                <div className="flex justify-between">
                    <button className="mt-8 px-10 py-3 mr-8 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold " onClick={handleReservation} 
                    > Realizar reserva </button>
                <button className="mt-8 px-10 py-3 bg-red-dark hover:bg-red-darkest rounded-xl text-white font-title font-semibold" onClick={() => setOnReservationForm(false)}> 
                Cancelar </button>
                </div>
            </section>
        </article>
    )
}