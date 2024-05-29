import { useState, useEffect } from "react";
import axios from "axios";

function ParamsInfo({url, actualCity, actualParking}) {
    const [parkName, setParkName]= useState('');
    const [coordX, setCoordX]= useState('');
    const [coordY, setCoordY]= useState('');
    const [address, setAddress] = useState('');
    const [email, setEmail]= useState('');
    const [phone, setPhone]= useState('');
    const [parkType, setParkType]= useState('');
    const [parkAvailability, setParkAvailability]= useState('');
    const [parkStart, setParkStart]= useState('');
    const [parkEnd, setParkEnd]= useState('');
    const [fidelization, setFidelization] = useState(false);
    const [minPoints, setMinPoints] = useState('');
    const [minCost, setMinCost] = useState('');
    const [canEdit, setCanEdit] = useState(false);

    useEffect(() => {
        if(actualParking) {
            setParkName(actualParking[0].namePark);
            setCoordX(actualParking[0].address.coordinatesX);
            setCoordY(actualParking[0].address.coordinatesY);
            setAddress(actualParking[0].address.descAddress);
            setEmail(actualParking[0].email);
            setPhone(actualParking[0].phone);
            setParkType(actualParking[0].parkingType.descParkingType);
            setParkAvailability(actualParking[0].schedule.scheduleType);
            setParkStart(actualParking[0].startTime);
            setParkEnd(actualParking[0].endTime);
        }
    }, [actualParking])
    
    const handleTimeChange = (setter) => (event) => {
        const time = new Date(event.target.valueAsNumber);
        if (time) {
          time.setMinutes(0);
          time.setSeconds(0);
          setter(time.toISOString().substr(11, 8));
        }
    };

    const handleUpdateParking = () => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        const params = {};

        if(parkName) params.namePark = parkName;
        if(coordX) params.coordinatesX = coordX;
        if(coordY) params.coordinatesY = coordY;
        if(address) params.descAddress = address;
        if(email) params.email = email;
        if(phone) params.phone = phone;
        if(parkType) params.idParkingType = parkType;
        if(parkAvailability) params.scheduleType = parkAvailability;
        if(parkStart) params.startTime = parkStart;
        if(parkEnd) params.endTime = parkEnd;
        console.log(params)
        
        axios.put(`${url}/parking/${actualParking[0].parkingId.idParking}/${actualParking[0].parkingId.city.idCity}`, {params, headers: {Authorization: `Bearer ${token}`}
        })
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.error(err);
        });
    }

    return(
        <article className="w-full py-5 rounded-2xl shadow-xl bg-blue-light">
            <section className='flex justify-center'>
                <div className='text-2xl font-semibold'> Información del parqueadero </div>
            </section>

            <section className='flex justify-between w-full px-8'>
                <section className='flex flex-col w-72'>
                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Nombre del parqueadero</label>
                        <input className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={parkName} 
                        disabled={!canEdit} 
                        onChange={(e) => setParkName(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Coordenadas X</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white opacity-70 font-paragraph' value={coordX} disabled={true}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Dirección</label>
                        <input className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={address} 
                        disabled={!canEdit} 
                        onChange={(e) => setAddress(e.target.value)}></input>
                    </div> 

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Teléfono</label>
                        <input className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={phone} 
                        disabled={!canEdit} 
                        onChange={(e) => setPhone(e.target.value)}></input>        
                    </div>  

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Disponibilidad</label>
                        <select className={`w-full shadow-xl p-3 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={parkAvailability} 
                        disabled={!canEdit}
                        onChange={(e) => setParkAvailability(e.target.value)}>
                            <option value="Dias de semana"> Días de semana </option>
                            <option value="Fines de semana"> Fines de semana </option>
                            <option value="Todos los días"> Todos los días </option>
                        </select>
                    </div>        

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'> Habilitar fidelización </label>
                        <select className={`w-full shadow-xl p-3 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={fidelization} 
                        disabled={!canEdit} 
                        onChange={(e) => setFidelization(e.target.value)}>
                            <option value="true"> Si </option>
                            <option value="false"> No </option>
                        </select>
                    </div>               
                    
                    {
                        fidelization ? (
                            <div className='flex flex-col mt-5'>
                                <label className='text-lg font-semibold mb-2'> Costo para obtener 1 punto </label>
                                <input type="number" min="0" className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} 
                                value={minCost} 
                                disabled={!canEdit} onChange={(e) => setMinCost(e.target.value)}></input>
                            </div> 
                        ) : null
                    }     
                </section>

                <section className='flex flex-col w-72'>
                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Ciudad</label>
                        <input className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph opacity-70`} value={actualCity !== undefined ? actualCity.name : null} 
                        disabled={true}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Coordenadas Y</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white opacity-70 font-paragraph' value={coordY} disabled={true}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Correo electrónico</label>
                        <input className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} value={email} 
                        disabled={!canEdit} onChange={(e) => setEmail(e.target.value)}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Tipo de parqueadero</label>
                        <input className='w-full shadow-xl p-2.5 rounded-md bg-white opacity-70 font-paragraph' value={parkType} disabled={true}></input>
                    </div>

                    <div className='flex flex-col mt-5'>
                        <label className='text-lg font-semibold mb-2'>Horario</label>
                        <div className="flex justify-between items-center">
                            <input type='time' className={`w-2/5 shadow-xl p-2.5 rounded-md bg-white font-paragraph text-sm ${canEdit ? 'opacity-100' : 'opacity-70'}`}
                            value={parkStart} disabled={!canEdit} onChange={handleTimeChange(setParkStart)}></input>   

                            <span className="w-5 h-0.5 mx-4 rounded-full bg-black"></span>

                            <input type='time' className={`w-2/5 shadow-xl p-2.5 rounded-md bg-white font-paragraph text-sm ${canEdit ? 'opacity-100' : 'opacity-70'}`} 
                            value={parkEnd} disabled={!canEdit}  onChange={handleTimeChange(setParkEnd)}></input>
                        </div>    
                    </div>

                    {
                        fidelization ? (
                            <div className='flex flex-col mt-5'>
                                <label className='text-lg font-semibold mb-2'> Puntos mínimos para 1 hora gratis </label>
                                <input type="number" min="0"  className={`w-full shadow-xl p-2.5 rounded-md bg-white font-paragraph ${canEdit ? 'opacity-100' : 'opacity-70'}`} 
                                value={minPoints} disabled={!canEdit} onChange={(e) => setMinPoints(e.target.value)}></input>
                            </div> 
                        ) : null
                    }
                </section>
            </section>

            <section className='flex items-center justify-between w-full mt-10 mb-2 px-12'>
                {canEdit ? (
                    <>
                        <button className='shadow-xl px-24 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold' 
                        onClick={handleUpdateParking}> Confirmar </button>
                        <button className='shadow-xl px-24 py-3 bg-red-dark hover:bg-red-darkest rounded-xl text-white font-title font-semibold'
                        onClick={() => setCanEdit(false)}> Cancelar </button>
                    </>
                ) : (
                    <>
                        <button className='mx-auto shadow-xl px-12 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold' 
                        onClick={() => setCanEdit(true)}> Editar parqueadero </button>
                    </>
                )}
                
            </section>
        </article>
    );
}

export default ParamsInfo;