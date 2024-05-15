import React from 'react';
import { useState } from "react";

function ParamsInfo({url, setCanEditSpaces}) {
    const [parkName, setParkName]= useState('');
    const [parkCity, setParkCity]= useState('');
    const [coordX, setCoordX]= useState('');
    const [coordY, setCoordY]= useState('');
    const [email, setEmail]= useState('');
    const [phone, setPhone]= useState('');
    const [parkType, setParkType]= useState('');
    const [car, setCar]= useState(false);
    const [motorcycle, setMotorcycle]= useState(false);
    const [bicycle, setBicycle]= useState(false);
    const [parkAvailability, setParkAvailability]= useState('');
    const [parkStart, setParkStart]= useState('');
    const [parkEnd, setParkEnd]= useState('');
    const [canEdit, setCanEdit] = useState(false);
    
    const handleTimeChange = (setter) => (event) => {
        const time = new Date(event.target.valueAsNumber);
        if (time) {
          time.setMinutes(0);
          time.setSeconds(0);
          setter(time.toISOString().substr(11, 8));
        }
    };

    return(
        <article className="w-full p-4  rounded-2xl shadow-xl bg-blue-light">
            <section className='flex justify-center'>
                <div className='text-2xl font-semibold'> Información del parqueadero </div>
            </section>

            <section className='flex items-center justify-between w-full px-5'>
                <section className='flex flex-col w-72'>
                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Nombre del parqueadero</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='Parqueadero Don José' 
                        value={parkName} disabled={!canEdit} onChange={(e) => setParkName(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Coordenadas X</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='4.567' value={coordX} 
                        disabled={!canEdit}  onChange={(e) => setCoordX(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Correo electrónico</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='donjose@gmail.com' value={email} 
                        disabled={!canEdit} onChange={(e) => setEmail(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Tipo de parqueadero</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph' value={parkType} disabled={!canEdit} 
                        onChange={(e) => setParkType(e.target.value)}>
                            <option value="COV"> Cubierto </option>
                            <option value="SEC"> Semi-cubierto </option>
                            <option value="UNC"> Descubierto </option>
                        </select>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Disponibilidad</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph' value={parkAvailability} disabled={!canEdit}
                        onChange={(e) => setParkAvailability(e.target.value)}>
                            <option value="Dias de semana"> Lunes a viernes </option>
                            <option value="Fines de semana"> Fines de semana </option>
                            <option value="Tiempo completo"> 24/7 </option>
                        </select>
                    </div>
                </section>

                <section className='flex flex-col w-72'>
                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Ciudad</label>
                        <select className='w-full shadow-xl p-3 rounded-md bg-white font-paragraph' value={parkCity} disabled={!canEdit} 
                        onChange={(e) => setParkCity(e.target.value)}></select>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Coordenadas Y</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='-74.4665' value={coordY} 
                        disabled={!canEdit} onChange={(e) => setCoordY(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Teléfono</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph placeholder:text-gray-dark' placeholder='3144737563'value={phone} 
                        disabled={!canEdit} onChange={(e) => setPhone(e.target.value)}></input>        
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Vehículos admitidos</label>
                        <div className='flex justify-between w-full shadow-xl p-3 rounded-md bg-white font-paragraph text-sm'>
                            <input type='checkbox' value={car} disabled={!canEdit} onChange={(e) => setCar(!car)}></input> <label> Automóvil </label>
                            <input type='checkbox' value={motorcycle} disabled={!canEdit} onChange={(e) => setMotorcycle(!motorcycle)}></input> <label> Moto </label>
                            <input type='checkbox' value={bicycle} disabled={!canEdit} onChange={(e) => setBicycle(!bicycle)}></input> <label> Bicicleta </label>
                            </div>        
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Horario</label>
                        <div className="flex justify-between items-center">
                            <input type='time' className='w-2/5 shadow-xl p-2.5 rounded-md bg-white font-paragraph text-sm' value={parkStart} disabled={!canEdit} 
                            onChange={handleTimeChange(setParkStart)}></input>   

                            <span className="w-5 h-0.5 mx-4 rounded-full bg-black"></span>

                            <input type='time' className='w-2/5 shadow-xl p-2.5 rounded-md bg-white font-paragraph text-sm' value={parkEnd} disabled={!canEdit} 
                            onChange={handleTimeChange(setParkEnd)}></input>
                        </div>    
                    </div>
                </section>
            </section>

            <section className='flex items-center justify-between w-full mt-10 mb-2 px-12'>
                {canEdit ? (
                    <button className='mx-auto shadow-xl px-24 py-3 bg-red-dark hover:bg-red-darkest rounded-xl text-white font-title font-semibold'
                    onClick={() => setCanEdit(false)}> Cancelar </button>
                ) : (
                    <>
                        <button className='shadow-xl px-12 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold' 
                        onClick={() => setCanEdit(true)}> Editar parqueadero </button>
                        <button className='shadow-xl px-12 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'
                        onClick={() => {setCanEdit(false), setCanEditSpaces(true)}}> Administrar cupos </button>
                    </>
                )}
                
            </section>

        </article>




    )
}

export default ParamsInfo;