import * as React from "react";
import ReCAPTCHA from "react-google-recaptcha";
import axios from "axios";

export default function Registro() {

    return (
        <div className="bg-gradient-to-b from-red-light from-75% to-red-dark py-24 relative px-14 rounded-2xl shadow-2xl shadow-black ">

            <div>

                <div className=" w-20 h-20 bg-white rounded-full ml-auto mr-auto " >
                </div>
                <div className="">
                    <select id="user" className="w-auto rounded py-3 px-7 bg-white mt-20 " placeholder="Usuario o correo electrónico">
                        <option selected value="">Tipo de documento</option>
                        <option value="CC">CC</option>
                        <option value="TI">TI</option>
                        <option value="CE">CE</option>
                        <option value="Pasaporte">Pasaporte</option>
                        
                    </select>

                    <input id="user" className="ml-28 w-auto rounded py-3 px-4 bg-white mt-20 " placeholder="Numero de identificación"></input>
                </div>
                <div className="">
                    <input id="user" className="w-auto rounded py-3 px-4 bg-white mt-10 " placeholder="Nombre"></input>
                    
                    <input id="user" className="ml-28 w-auto rounded py-3 px-4 bg-white mt-10 " placeholder="Apellido"></input>
                </div>
                <div className="">
                    <input id="user" className="w-auto rounded py-3 px-4 bg-white mt-10 " placeholder="Telefono"></input>
                    
                    <input id="user" className="ml-28 w-auto rounded py-3 px-4 bg-white mt-10 " placeholder="Nombre de usuario"></input>
                </div>
                <div className="flex items-center justify-center">
                    <input id="pass" className="mt-7 w-full rounded p-3 bg-white" placeholder="Correo electrónico"></input>
                </div>
                <div className="flex items-center justify-center">
                    <button className="disabled:bg-black active:bg-blue-dark hover:bg-blue-light bg-blue-dark rounded-2xl mt-10 px-20 py-3 text-white font-semibold text-2xl  font-title active:scale-90 " id="btn" >Crear cuenta</button>
                </div>
            </div>
            <hr className=" bg-white border-0 h-0.5 w-full mt-10" >

            </hr>
            <div className="text-sm text-white flex justify-center">
                ¿Ya tiene una cuenta?  <a href="/" className="text-white hover:text-blue-light font-semibold ml-1"> Inicia sesión</a>
            </div>

        </div>
    )
}