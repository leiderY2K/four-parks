import React, { useState, useEffect } from 'react';
//import showCountdown from '../../javascript/temporizador';

function ReservationInfo({url}) {

    /*const [timeRemaining, setTimeRemaining] = useState("");

    useEffect(() => {
        const startTempTime = "2:00";
        const endTempTime = "20:00";
    
        
        showCountdown(startTime, endTime, setTimeRemaining);
    
        
        return () => clearInterval();
      }, []);*/



    return(
        <article className="flex flex-col items-center w-96 mt-12 px-6 py-5 rounded-md shadow-md bg-blue-light">
            <section className="flex justify-between w-full h-16 mb-5">
                    <div>
                        <div className="w-auto font-semibold text-lg"> Nombre del parqueadero </div>
                        <div className="mb-2 text-title font-semibold"> Ciudad </div> 
                    </div>
                    
                    <div className="flex justify-center w-20 h-8 mt-2 bg-transparent border-black border-2 rounded-sm shadow-md font-semibold text-lg"> 02:00 </div>                     
            </section> 

            <section className="w-full mb-5">
                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Tipo de Vehiculo: </span> Carro </h2>
                    <h2 className="mb-5 text-title text-md"> <span className="font-semibold"> Matricula: </span> XYS-564 </h2>
                </section>

                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Dia de la reserva: </span> 07/05/24 </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Hora ingreso: </span> 9:00am </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Hora salida: </span> 11:00am </h2>
                </section>
                    
                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Check-in: </span> __________ </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Check-out: </span> __________ </h2>
                </section>
            </section>   
            
            <button className="px-16 py-2 rounded-lg bg-red-dark hover:bg-red-darkest shadow-md font-title font-semibold text-white"> Cancelar reserva </button>
        </article>    
    )
}

export default ReservationInfo