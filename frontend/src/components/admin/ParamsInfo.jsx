import React from 'react';
import { useState } from "react";

function ParamsInfo({url}) {

    const [parkName, setParkName]= useState('');
    const [coorY, setCoorY]= useState('');
    const [coorX, setCoorX]= useState('');
    const [parkCity, setParkCity]= useState('');
    const [email, setEmail]= useState('');
    const [phone, setPhone]= useState('');
    const [parkType, setParkType]= useState('');
    const [vehicleType, setVehicleType]= useState('');
    const [parkAvailability, setParkAvailability]= useState('');
    const [parkStart, setParkStart]= useState('');
    const [parkEnd, setParkEnd]= useState('');
    const [car, setCar]= useState('');
    const [bicycle, setBicycle]= useState('');
    const [motorcycle, setMotorcycle]= useState('');


    
    const handleTimeChange = (setter) => (event) => {
        const time = new Date(event.target.valueAsNumber);
        if (time) {
          time.setMinutes(0);
          time.setSeconds(0);
          setter(time.toISOString().substr(11, 8));
        }
      };


    return(
        <article className="bg-blue-light mt-12 pt-2 pb-6 w-6/12 relative rounded-2xl shadow-xl">
            <section className='flex justify-center'>
                <div className='text-2xl font-semibold'>
                    Información del parqueadero
                </div>
            </section>
            <section className='flex items-center justify-between w-full px-5'>
                <section className='flex flex-col w-2/5'>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Nombre del parqueadero</label>
                        <input className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Parqueadero Don José' value={parkName} onChange={(e) => setParkName(e.target.value)}></input>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Coordenadas X</label>
                        <input className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='4.567' value={coorX} onChange={(e) => setCoorX(e.target.value)}></input>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Correo electrónico</label>
                        <input className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Parqueadero Don José' value={email} onChange={(e) => setEmail(e.target.value)}></input>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Tipo de parqueadero</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Tipo de parqueadero'
                        value={vehicleType} onChange={(e) => setVehicleType(e.target.value)}></select>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Disponibilidad</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Disponibilidad'
                        value={parkAvailability} onChange={(e) => setParkAvailability(e.target.value)}></select>
                    </div>
                    

                </section>
                <section className='flex flex-col w-2/5 '>
                    <div className='flex flex-col mt-8'>
                        <label className='text-xl font-semibold mb-2'>Ciudad</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Ciudad'
                        value={parkCity} onChange={(e) => setParkCity(e.target.value)}></select>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Coordenadas Y</label>
                        <input className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='-74.4665' value={coorY} onChange={(e) => setCoorY(e.target.value)}></input>
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Telefono</label>
                        <input className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='3144737563'value={phone} onChange={(e) => setPhone(e.target.value)}></input>        
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Vehículos admitidos</label>
                        <div className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder=''>
                            <input type='checkbox' value={car} onChange={(e) => setCar(e.target.value)}></input> <label className='text font-thin'>Automóvil</label>
                            <input type='checkbox' value={motorcycle} onChange={(e) => setMotorcycle(e.target.value)} className='ml-1'></input> <label className='text font-thin'>Moto</label>
                            <input type='checkbox' value={bicycle} onChange={(e) => setBicycle(e.target.value)} className='ml-1'></input> <label className='text font-thin'>Bicicleta</label>
                            </div>        
                    </div>
                    <div className='flex flex-col mt-5'>
                        <label className='text-xl font-semibold mb-2'>Horario</label>
                        <div className="flex justify-between">
                            <input type='time' className='w-2/5 shadow-xl p-2 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder=''
                            value={parkStart} 
                            onChange={handleTimeChange(setParkStart)}></input>    
                            <hr className="h-0.5 rounded-full bg-gray-400"></hr>
                            <input type='time' className='w-2/5 shadow-xl p-2 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder=''
                            value={parkEnd} 
                            onChange={handleTimeChange(setParkEnd)}></input>
                        </div>    
                    </div>
                </section>

            </section>
            <section className='flex items-center justify-between w-full px-10'>
                <button className='shadow-xl mt-8 px-12 py-3 mr-8 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'>Editar parqueadero
                </button>
                <button className='shadow-xl mt-8 px-12 py-3 mr-8 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'>Administrar cupos</button>
                
            </section>

        </article>




    )
}

export default ParamsInfo;