import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'
import cubiertoIcon from '../../assets/Cubierto.png';

export default function ReservationTarjet({url}) {
    const [idType, setIdType] = useState('');
    const [idNumber, setIdNumber] = useState('');
    const [name, setName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phone, setPhone] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [payMethod, setPayMethod] = useState('');

    const navigate = useNavigate();

    const handleSignUp = (e) => {
        e.preventDefault();

        if(!idType || !idNumber || !name || !lastName || !phone || !username || !email) {
            Swal.fire({
                icon: 'info',
                title: `Por favor llene todos los campos`
            });
        } else {
            axios.post(`${url}/auth/register`, {
                idUser: idNumber, 
                idDocTypeFk: idType, 
                firstName: name, 
                role: 'CLIENT',
                lastName: lastName, 
                email: email, 
                phone: phone, 
                username: username, 
                password: 'temporal'
            })
            .then(res => {
                console.log(res);
                
                Swal.fire({
                    icon: 'success',
                    title: `Registro exitoso`
                });

                navigate("/inicio-sesion");
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: `Hubo un error al registrar el usuario` ,
                });

                console.log(err);
            })
        }
    }
    //
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
                        <input type="date" id="name" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark mr-4" placeholder="Calendario(?"
                        value={name} onChange={(e) => setName(e.target.value)}></input>

                        <input type="time" id="name" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark mr-4" placeholder="Nombre"
                        value={name} onChange={(e) => setName(e.target.value)}></input>
                        
                        <input type="time" id="lastName"className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Apellido"
                        value={lastName} onChange={(e) => setLastName(e.target.value)}></input>
                    </div>
                
                    <div className="flex justify-between w-full mb-5">
                        <select id="phone" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Tipo de vehiculo"
                        value={phone} onChange={(e) => setPhone(e.target.value)}>
                            <option value="" disabled hidden> Tipo de vehiculo </option>
                            <option value="1">Moto</option>
                            <option value="2">Carro</option>
                            <option value="3">etc</option>
                            <option value="4">Pasaporte</option>
                        </select>
                        
                        <input id="username" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Matricula del vehiculo"
                        value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    </div>
                
                    <div className="w-full">
                        <select id="email" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Metodo de pago"
                        value={email} onChange={(e) => setEmail(e.target.value)}>
                            <ption value="" disabled hidden> Metodo de pago </ption>
                            <option value="1">Mastercard</option>
                            <option value="2">Nequi</option>
                            <option value="3">CE</option>
                            <option value="4">Pasaporte</option>
                        </select>
                    </div>
                    <div className="flex justify-between w-full mt-5">
                        <div className="text font-semibold text-lg">Total Reserva:$45,000</div>
                        <hr className="h-0.5 rounded-full bg-blue-light"></hr>
                    </div>
                    
                </section>  
                <div className="flex justify-between">
                    <button className="mt-8 px-10 py-3 mr-8 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold " onClick={handleSignUp}> 
                Realizar reserva </button>
                <button className="mt-8 px-10 py-3 bg-red-dark hover:bg-red-darkest rounded-xl text-white font-title font-semibold" onClick={handleSignUp}> 
                Cancelar </button>
                </div>
            </section>
        </article>
    )
}