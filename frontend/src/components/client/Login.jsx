import * as React from "react";
import ReCAPTCHA from "react-google-recaptcha";
import axios from "axios";

export default function Login(){
    
    const captcha = React.useRef(null);
    //let state
    //state={isVerified: false};
    let x=true
    const onChange = () =>{
        
        if(captcha.current.getValue()){
           //setState({isVerified;true})
            console.log("No eres un robot")
    }
}
    function hola() {
        if(captcha.current.getValue()==false){
            //setState({isVerified;true})
             alert("Por favor, marcar el Recaptcha para continuar")
     }
    }
    

    return(
        <div className="bg-gradient-to-b from-red-light from-75% to-red-dark py-20 relative px-10 rounded-2xl shadow-2xl shadow-black ">
            
            <div>
                
                <div className=" w-20 h-20 bg-white rounded-full ml-auto mr-auto " >
                </div>
            <div>
                    <input id="user"className="w-full rounded p-3 bg-white mt-20 " placeholder="Usuario o correo electrónico"></input>
                </div>
                <div>
                    <input id="pass" className="mt-7 w-full rounded p-3 bg-white" placeholder="Contraseña" type="password"></input>
                </div>
                <div className="recaptcha mt-10 object-center px-8">
                    <ReCAPTCHA
                        ref={captcha}
                        sitekey="6LfEr8QpAAAAAHkHnuDZebwy-ZRwIYKLoVA5MmyR"
                        onChange={onChange}
                    />,
                </div >
                <div className="">
                    <button onClick={hola} className="disabled:bg-black active:bg-blue-dark hover:bg-blue-light bg-blue-dark rounded-2xl m-5 mt-10 px-20 py-3 text-white font-semibold text-2xl  font-title active:scale-90 " id="btn" >Iniciar sesión</button>
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