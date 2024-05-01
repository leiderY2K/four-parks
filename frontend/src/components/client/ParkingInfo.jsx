import { useState } from 'react';
import cubiertoIcon from '../../assets/Cubierto.png';

const ParkingInfo = () => {
    const [parkingName, setParkingName] = useState('');
    const [city, setCity] = useState('');
    const [availability, setAvailability] = useState('');
    const [schedule, setSchedule] = useState('');
    const [carRate, setCarRate] = useState('');
    const [motorcycleRate, setMotorcycleRate] = useState('');
    const [bikeRate, setBikeRate] = useState('');
    const [carSpaces, setCarSpaces] = useState();
    const [motorcycleSpaces, setMotorcycleSpaces] = useState();
    const [bikeSpaces, setBikeSpaces] = useState();
    const [parkingType, setParkingType] = useState('');
    const [coveredSpaces, setCoveredSpaces] = useState();
    const [uncoveredSpaces, setUncoveredSpaces] = useState();

    return (
        <section className="flex flex-col items-center w-full mt-12 pl-12">
            <article className="w-1/2 rounded-md shadow-md bg-blue-light">
                <section className="flex items-center m-5">
                    <div className="w-1/3 h-16 flex justify-center rounded-md border-2 border-black">
                        <img src={cubiertoIcon} alt="Imagen que identifica el tipo de parqueadero"/>
                    </div>

                    <section className="flex flex-col justify-evenly items-center w-4/5 h-16 ml-4">
                        <h1 className="-mt-2 mb-2 text-center text-title font-semibold text-lg"> Nombre del parqueadero </h1>
                        <span className="w-11/12 h-0.5 rounded-full bg-black"></span>
                    </section>
                </section>

                <section className="ml-5 mb-5">
                    <h2 className="mb-5 text-title text-md"> <span className="font-semibold"> Ciudad: </span> Bogotá </h2>

                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Disponibilidad: </span> Parcial </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Horario: </span> 9:00 - 15:00 </h2>
                    </section>
                    
                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Tarifa carro: </span> $25.000 / hora </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Tarifa moto: </span> $15.000 / hora </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Tarifa cicla: </span> $8.000 / hora </h2>
                    </section>
                    
                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos carro: </span> 20 </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos moto: </span> 12 </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos cicla: </span> 6 </h2>
                    </section>
                    
                    <section>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos cubierto: </span> 20 </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos descubierto: </span> 12 </h2>
                    </section>
                </section>
            </article>

            <button className="w-1/3 mt-6 px-2 py-3 rounded-2xl bg-blue-dark shadow-md font-title font-semibold text-lg text-white hover:bg-blue-darkest"> 
            Realizar reserva </button>
        </section>
    )
}

export default ParkingInfo