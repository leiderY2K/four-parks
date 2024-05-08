import React, { useState, useEffect } from 'react';
//import showCountdown from '../../javascript/temporizador';

function ReservationInfo({url, reservation}) {

    /*const [timeRemaining, setTimeRemaining] = useState("");

    useEffect(() => {
        const startTempTime = "2:00";
        const endTempTime = "20:00";
    
        
        showCountdown(startTime, endTime, setTimeRemaining);
    
        
        return () => clearInterval();
      }, []);*/

    const formatDate = (dateString) => {
        const date = new Date(dateString);

        if (!isNaN(date)) {
            return date.toISOString().substr(0, 10);
        }

        return "Fecha inválida";
    }

    const createButton = () => {
        if(reservation.status == "PEN") {
            return (<button className="px-16 py-2 rounded-lg bg-red-dark hover:bg-red-darkest shadow-md font-title font-semibold text-white"> Cancelar reserva </button>);
        } else if(reservation.status == "CON") {
            return (<button className="px-16 py-2 rounded-lg bg-blue-dark hover:bg-blue-darkest shadow-md font-title font-semibold text-white"> Hacer check in </button>);
        } else if(reservation.status == "CUR") {
            return (<button className="px-16 py-2 rounded-lg bg-blue-dark hover:bg-blue-darkest shadow-md font-title font-semibold text-white"> Hacer check out </button>);
        }
    }

    return(
        <article className="flex flex-col items-center w-96 mt-12 px-6 py-5 rounded-md shadow-md bg-blue-light">
            <section className="flex justify-between w-full h-16 mb-5">
                    <div>
                        <div className="w-auto font-semibold text-lg"> Nombre del parqueadero </div>
                        <div className="mb-2 text-title font-semibold"> Ciudad </div> 
                    </div>
                    
                    <div className="flex justify-center w-20 h-8 mt-2 bg-transparent border-black border-2 rounded-sm shadow-md font-semibold text-lg"> 00:00 </div>                     
            </section> 

            <section className="w-full mb-5">
                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Tipo de Vehiculo: </span> Carro </h2>
                    <h2 className="mb-5 text-title text-md"> <span className="font-semibold"> Matricula: </span> {reservation.licensePlate}</h2>
                </section>

                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Dia de la reserva: </span> {formatDate(reservation.dateRes)} </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Hora ingreso: </span> {reservation.startTimeRes} </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Hora salida: </span> {reservation.endTimeRes} </h2>
                </section>
                    
                <section className="mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Check-in: </span> __________ </h2>
                    <h2 className="text-title text-md"> <span className="font-semibold"> Check-out: </span> __________ </h2>
                </section>
            </section>   
            
            {createButton()}
        </article>    
    )
}

export default ReservationInfo