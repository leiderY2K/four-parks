import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2'

export default function SignUp({url}) {
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

    return (
        <article className="bg-gradient-to-b from-red-light from-75% to-red-dark pt-12 pb-6 relative rounded-2xl shadow-xl">
            <section className="flex flex-col items-center px-12">
                <div className="w-24 h-24 bg-white rounded-full ml-auto mr-auto"></div>

                <section className="flex flex-col justify-evenly items-center w-full h-72 mt-12">
                    <div className="flex justify-between w-full">
                        <select id="idType" className="w-2/5 p-3 rounded-md bg-white font-paragraph" value={idType} onChange={(e) => setIdType(e.target.value)}>
                            <option value="" disabled hidden> Tipo de documento </option>
                            <option value="1">CC</option>
                            <option value="2">TI</option>
                            <option value="3">CE</option>
                            <option value="4">Pasaporte</option>
                        </select>

                        <input id="idNumber" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Numero de identificación"
                        value={idNumber} onChange={(e) => setIdNumber(e.target.value)}></input>
                    </div>
                
                    <div className="flex justify-between w-full">
                        <input id="name" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Nombre"
                        value={name} onChange={(e) => setName(e.target.value)}></input>
                        
                        <input id="lastName"className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Apellido"
                        value={lastName} onChange={(e) => setLastName(e.target.value)}></input>
                    </div>
                
                    <div className="flex justify-between w-full">
                        <input id="phone" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Telefono"
                        value={phone} onChange={(e) => setPhone(e.target.value)}></input>
                        
                        <input id="username" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Nombre de usuario"
                        value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    </div>
                
                    <div className="w-full">
                        <input id="email" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Correo electrónico"
                        value={email} onChange={(e) => setEmail(e.target.value)}></input>
                    </div>
                    
                </section>  

                <div className="w-full mt-8">
                    <div id="payMethods" className="flex justify-between w-full px-12 py-4 rounded-md bg-white font-paragraph placeholder:text-gray-dark" 
                    value={payMethod} onChange={(e) => setPayMethod(e.target.value)}>
                        <input type="radio" id="" value={"nequi"}/> Nequi
                        <input type="radio" id="" value={"tarjeta"}/> Tarjeta de crédito
                    </div>
                </div>
            
                <button className="mt-8 px-20 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold text-xl" onClick={handleSignUp}> 
                Crear cuenta </button>
            </section>

            <hr className="h-0.5 mt-8 rounded-full bg-white"></hr>

            <div className="flex justify-center mt-2 font-paragraph text-sm text-white"> ¿Ya tiene una cuenta?
                <a href="/inicio-sesion" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Inicia sesión </a>
            </div> 
        </article>
    )
}