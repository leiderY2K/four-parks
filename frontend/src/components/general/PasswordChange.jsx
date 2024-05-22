import { useRef, useState } from "react"
import { useNavigate } from "react-router-dom"
import ReCAPTCHA from "react-google-recaptcha"
import axios from "axios"
import Swal from 'sweetalert2'
import { decodeJWT } from "../../javascript/decodeJWT"
import logo from '../../assets/Logo.png'
import '../../css/login.css'

export default function PasswordChange({ url }) {

    const [username, setUsername] = useState("");
    const [actualPass, setActualPass] = useState("");
    const [newPass, setNewPass] = useState("");
    const [newPassConfim, setNewPassConfirm] = useState("");

    const handleChange = (e) => {
        e.preventDefault();

        // Verificar que todos los campos estén llenos
        if (!username || !actualPass || !newPass || !newPassConfim) {
            Swal.fire({
                icon: 'info',
                title: `Por favor llene todos los campos`
            });
            return; // Detener la ejecución si faltan campos
        }

        // Verificar si las contraseñas coinciden
        if (newPass !== newPassConfim) {
            Swal.fire({
                icon: 'info',
                title: `Las contraseñas no coinciden`
            });
            return; // Detener la ejecución si las contraseñas no coinciden
        }

        // Si todas las validaciones pasan, enviar la solicitud Axios
        axios.post(`${url}/auth/change-pass`, { username: username, oldPass: actualPass, newPass: newPass })
            .then(res => {
                console.log(res);
                // Aquí puedes manejar la respuesta exitosa, por ejemplo, mostrar un mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: `Contraseña cambiada exitosamente`
                });
            })
            .catch(err => {
                console.log(err);
                // Manejar errores de la solicitud Axios, por ejemplo, mostrar un mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: `Hubo un error al cambiar la contraseña`
                });
            });
    }





    return (
        <article id="loginCard" className="pt-6 md:pt-10 2xl:pt-6 rounded-2xl shadow-xl bg-gradient-to-b from-red-light from-75% to-red-dark">
            <section className="flex flex-col items-center px-6 md:px-10 xl:px-8">
                <img src={logo} alt="Logo de Four Parks" className="w-24 h-24" />

                <section className="flex flex-col justify-between items-center w-full h-56 mt-6 md:mt-10 2xl:mt-8">
                    <input type="text" id="username" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark"
                        placeholder="Ingrese nombre de usuario" value={username} onChange={(e) => setUsername(e.target.value)} ></input>

                    <input type="password" id="actualPass" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark"
                        placeholder="Ingrese contraseña actual" value={actualPass} onChange={(e) => setActualPass(e.target.value)} ></input>

                    <input type="password" id="newPass" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark"
                        placeholder="Ingrese contraseña nueva" value={newPass} onChange={(e) => setNewPass(e.target.value)} ></input>

                    <input type="password" id="newPassConfim" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark"
                        placeholder="Confirme contraseña nueva" value={newPassConfim} onChange={(e) => setNewPassConfirm(e.target.value)}></input>

                </section>

                <button className="mt-8 2xl:mt-6  md:px-12 px-16 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold text-xl"
                    onClick={handleChange}>Cambiar Contraseña</button>
            </section>

            <hr className="h-0.5 mt-8 2xl:mt-6  rounded-full bg-white"></hr>


            <div className="flex justify-center mt-2 font-paragraph text-sm text-white"> ¿Aún no tiene una cuenta?
                <a href="/registro" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Regístrarse </a>
            </div>

            <div className="flex justify-center mt-2 font-paragraph text-sm text-white"> ¿Recuerda su contraseña?
                <a href="/" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Iniciar sesión </a>
            </div>
        </article>
    )
}