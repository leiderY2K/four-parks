import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import ReCAPTCHA from "react-google-recaptcha";
import axios from "axios";

export default function Login(){
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");
    const [captchaState, setCaptchaState] = useState(false);

    const navigate = useNavigate();
    const captcha = useRef(null);

    const handleLogin = (e) => {
        e.preventDefault();

        if(!user || !password) {
            alert("Por favor llene todos los campos")
        } else {
            if(captchaState) {
                navigate("/cliente-inicio", {
                    replace: ("/inicio-sesion", true)
                });
            } else {
                alert("Por favor complete el reCAPTCHA para continuar")
            }
        }
    }

    const validateCaptcha = () =>{
        if(captcha.current.getValue()) {
            setCaptchaState(true)
        }
    }
    
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