function ReservaInfo() {
    return(
        //Preguntar tema sombras 
        <section className="flex flex-col items-center w-full mt-16 pl-12">
            <article className="w-full rounded-md shadow-md bg-blue-light">
                    
                    <section className=" w-full h-16 ml-4 mt-5 mb-10 ">
                        <div className="flex justify-between mr-5">
                            <div className="w-auto font-semibold text-lg"> Nombre del parqueadero 
                             </div>
                             <div className="h-9 w-20 bg-transparent border-black border-2">   
                                <div className="flex justify-center font-semibold text-xl">10:30</div>
                             </div>
                            
                        </div>
                        <div className="mb-2 text-title font-semibold text-lg"> Ciudad </div>    
                             
                    </section>
                

                <section className="ml-5 mb-5">
                    <h2 className="text-title text-md"> <span className="font-semibold"> Tipo de Vehiculo: </span> Carro </h2>
                    <h2 className="mb-5 text-title text-md"> <span className="font-semibold"> Matricula: </span> XYS-564 </h2>

                    <section className="mb-5">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Dia de la reserva: </span> 07/05/24 </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Hora ingreso: </span> 9:00am </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Hora salida: </span> 11:00am </h2>
                    </section>
                    
                    <section className="">
                        <h2 className="text-title text-md"> <span className="font-semibold"> Check-in: </span> __________ </h2>
                        <h2 className="text-title text-md"> <span className="font-semibold"> Check-out: </span> __________ </h2>
                    </section>
                    <section className="ml-10 font-paragraph text-sm text-white">
                        <button className="mt-8 px-20 py-3 rounded-2xl bg-red-dark shadow-md font-title font-semibold text-lg text-white"> Cancelar reserva </button>
                    </section>
                </section>
            </article>

            
        </section>    
    )
}
export default ReservaInfo