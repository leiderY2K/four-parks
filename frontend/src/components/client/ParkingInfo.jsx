import coveredIcon from '../../assets/Parking Icons/Covered-Transparent.png';
import uncoveredIcon from '../../assets/Parking Icons/Uncovered-Transparent.png';
import semicoveredIcon from '../../assets/Parking Icons/Semicovered-Transparent.png';

const ParkingInfo = ({setOnReservationForm, actualParking}) => {
    return (
        <section className="flex flex-col items-center w-full mt-12 pl-12">
            <article className="w-1/2 rounded-md shadow-md bg-blue-light">
                <section className="flex items-center m-5">
                    <div className="w-1/3 h-16 flex justify-center rounded-md ">
                        <img src={coveredIcon} alt="Imagen que identifica el tipo de parqueadero"/>
                    </div>

                    <section className="flex flex-col justify-evenly items-center w-4/5 h-16 ml-4">
                        <h1 className="-mt-2 mb-2 text-center text-title font-semibold text-lg"> {actualParking.namePark}</h1>
                        <span className="w-11/12 h-0.5 rounded-full bg-black"></span>
                    </section>
                </section>

                <section className="ml-5 mb-5">
                    <h2 className="mb-5 text-title text-md"> <span className="font-semibold"> Ciudad: </span> {actualParking.city.name} </h2>

                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Disponibilidad: </span> {actualParking.schedule.scheduleType}  </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Horario: </span> 
                        {actualParking.schedule.startTime} - {actualParking.schedule.endTime} </h2>
                    </section>
                    
                    {/* <section className="mb-5">
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
                    </section> */}

                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Teléfono: </span> {actualParking.phone} </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Correo electrónico: </span> {actualParking.email} </h2>
                    </section> 

                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Cupos disponibles: </span> {actualParking.disponibility} </h2>
                    </section> 
                </section>
            </article>

            <button className="w-1/3 mt-6 px-2 py-3 rounded-2xl bg-blue-dark shadow-md font-title font-semibold text-lg text-white hover:bg-blue-darkest"
            onClick={() => setOnReservationForm(true)}> Realizar reserva </button>
        </section>
    )
}

export default ParkingInfo