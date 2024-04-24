import * as React from "react";
import ReCAPTCHA from "react-google-recaptcha";

export default function Login(){
    const captcha = React.useRef(null);
    const onChange = () =>{
        if(captcha.current.getValue()){
            console.log("El usuario no es un robot")
        }

    }
    return(
        <div className="bg-gradient-to-b from-red-light from-75% to-red-dark py-20 relative px-10 rounded-2xl ">
            <div>
                <div className=" w-20 h-20 bg-white rounded-full ml-auto mr-auto " >
                </div>
                <div>
                    <input className="w-full rounded p-3 bg-white mt-20 " placeholder="Usuario o correo electrónico"></input>
                </div>
                <div>
                    <input className="mt-7 w-full rounded p-3 bg-white" placeholder="Contraseña" type="password"></input>
                </div>
                <div className="recaptcha mt-10 object-center px-8">
                    <ReCAPTCHA
                        ref={captcha}
                        sitekey="6LfEr8QpAAAAAHkHnuDZebwy-ZRwIYKLoVA5MmyR"
                        onChange={onChange}
                    />,
                </div >
                <div className="">
                    <button className="hover:bg-blue-light bg-blue-dark rounded-2xl m-5 mt-10 px-20 py-3 text-white font-semibold text-2xl font-title">Iniciar sesión</button>
                </div>
                </div>
                <hr className=" bg-white border-0 h-0.5 w-full" >

                </hr>
                <div className="text-sm text-white flex justify-center ">
                    ¿Olvidó su contraseña?  <a href="/" className="text-white hover:text-blue-light font-semibold ml-1"> Recuperar</a>
                </div>

        </div>
    )
}