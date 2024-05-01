import { useRef, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ReCAPTCHA from "react-google-recaptcha";
import axios from "axios";
import Swal from 'sweetalert2'
import { decodeJWT } from "../../javascript/decodeJWT";

export default function Login({url}){
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");
    const [captchaState, setCaptchaState] = useState(false);

    const navigate = useNavigate();
    const captcha = useRef(null);

    const handleLogin = (e) => {
        e.preventDefault();

        if(!user || !password) {
            Swal.fire({
                icon: 'info',
                title: `Por favor llene todos los campos`
            });
        } else {
            if(captchaState) {
                //Alt96
                axios.post(`${url}/auth/login`, {username: user, password: password})
                .then(res => {
                    const userLogged = {
                        "idNumber": res.data.userId.idUser,
                        "idType": res.data.userId.idDocTypeFk,
                        "role": res.data.role
                    }

                    sessionStorage.setItem("userLogged", JSON.stringify(userLogged));
                    sessionStorage.setItem("token", JSON.stringify(res.data.token));

                    if(res.data.role == "CLIENT") {
                        Swal.fire({
                            icon: 'success',
                            title: `Bienvenid@ ${user}`
                        });

                        navigate("/cliente-inicio", {
                            replace: ("/inicio-sesion", true)
                        });
                    }
                })
                .catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: `Hubo un error al iniciar sesión` ,
                    });

                    console.log(err);
                })
            } else {
                Swal.fire({
                    icon: 'info',
                    title: `Por favor complete el reCAPTCHA para continuar`
                });
            }
        }
    }

    const validateCaptcha = () =>{
        if(captcha.current.getValue()) {
            setCaptchaState(true)
        }
    }

    console.log(decodeJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"))

    return(
        <article className="bg-gradient-to-b from-red-light from-75% to-red-dark pt-12 pb-6 relative rounded-2xl shadow-xl">
            <section className="flex flex-col items-center px-12">
                <div className="w-24 h-24 bg-white rounded-full ml-auto mr-auto"></div>
                    
                <section className="flex flex-col justify-between items-center w-full h-56 mt-12">
                    <input type="text" id="user" value={user} className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" 
                    placeholder="Usuario o correo electrónico" onChange={(e) => setUser(e.target.value)} required></input>

                    <input type="password" id="password" value={password} className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" 
                    placeholder="Contraseña" onChange={(e) => setPassword(e.target.value)} required></input>

                    <ReCAPTCHA ref={captcha} sitekey="6LfEr8QpAAAAAHkHnuDZebwy-ZRwIYKLoVA5MmyR" onChange={validateCaptcha} />
                </section>

                <button className="mt-8 px-20 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold text-xl" 
                onClick={handleLogin}>Iniciar sesión</button>   
            </section>

            <hr className="h-0.5 mt-8 rounded-full bg-white"></hr>
                    
            <div className="flex justify-center mt-4 font-paragraph text-sm text-white"> ¿Olvidó su contraseña?  
                <a href="/" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Recuperar </a>
            </div>     
            
            <div className="flex justify-center mt-2 font-paragraph text-sm text-white"> ¿Aún no tiene una cuenta?  
                <a href="/registro" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Regístrate </a>
            </div>     
        </article>
    )
}