import axios from "axios";

export default function SignUp() {
    return (
        <article className="bg-gradient-to-b from-red-light from-75% to-red-dark pt-12 pb-6 relative rounded-2xl shadow-xl">
            <section className="flex flex-col items-center px-12">
                <div className="w-24 h-24 bg-white rounded-full ml-auto mr-auto"></div>

                <section className="flex flex-col justify-between items-center w-full h-72 mt-12">
                    <div className="flex justify-between w-full">
                        <select id="user" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Usuario o correo electrónico">
                            <option selected value="">Tipo de documento</option>
                            <option value="CC">CC</option>
                            <option value="TI">TI</option>
                            <option value="CE">CE</option>
                            <option value="Pasaporte">Pasaporte</option>
                            
                        </select>

                        <input id="user" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Numero de identificación"></input>
                    </div>
                
                    <div className="flex justify-between w-full">
                        <input id="name" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Nombre"></input>
                        
                        <input id="lastName"className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Apellido"></input>
                    </div>
                
                    <div className="flex justify-between w-full">
                        <input id="phone" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Telefono"></input>
                        
                        <input id="username" className="w-2/5 p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Nombre de usuario"></input>
                    </div>
                
                    <div className="w-full">
                        <input id="email" className="w-full p-3 rounded-md bg-white font-paragraph placeholder:text-gray-dark" placeholder="Correo electrónico"></input>
                    </div>
                    
                </section>  

                <div className="w-full mt-8">
                    <div id="payMethods" className="flex justify-between w-full px-12 py-4 rounded-md bg-white font-paragraph placeholder:text-gray-dark">
                        <input type="radio" id="" value={"nequi"}/> Nequi
                        <input type="radio" id="" value={"tarjeta"}/> Tarjeta de crédito
                    </div>
                </div>
            
                <button className="mt-8 px-20 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold text-xl"> Crear cuenta </button>
            </section>

            <hr className="h-0.5 mt-8 rounded-full bg-white"></hr>

            <div className="flex justify-center mt-2 font-paragraph text-sm text-white"> ¿Ya tiene una cuenta?
                <a href="/inicio-sesion" className="ml-1 font-semibold text-white hover:text-blue-darkest"> Inicia sesión </a>
            </div> 
        </article>
    )
}