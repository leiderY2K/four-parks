import * as React from "react";

export default function Login(){

    return(
        <div className="bg-gradient-to-b from-red-light from-75% to-red-dark py-20 relative px-10 rounded-2xl ">
            <div>
                <div className=" w-20 h-20 bg-white2 rounded-full ml-auto mr-auto " >
                </div>
                <div>
                    <input className="w-full rounded p-3 bg-white2 mt-20 " placeholder="Usuario o correo electrónico"></input>
                </div>
                <div>
                    <input className="mt-7 w-full rounded p-3 bg-white2" placeholder="Contraseña" type="password"></input>
                </div>
                <div>
                    <input className="" placeholder="CAPCHA" type="password"></input>
                </div >
                <div className="">
                    <button className="hover:bg-blue-light bg-blue-dark rounded-2xl m-5 mt-10 px-20 py-3 text-white2 font-semibold text-2xl font-title">Iniciar sesión</button>
                </div>
                </div>
                <hr className=" bg-white2 border-0 h-0.5 w-full" >

                </hr>
                <div className="text-sm text-white2 flex justify-center ">
                    ¿Olvidó su contraseña?  <a href="/" className="text-white2 hover:text-blue-light font-semibold ml-1"> Recuperar</a>
                </div>

        </div>
    )
}