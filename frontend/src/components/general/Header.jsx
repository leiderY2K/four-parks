import { useState } from "react";
import { Link } from "react-router-dom";
import logo from '../../assets/Logo.png';

const Header = () => {
    const [onToggle, setOnToggle] = useState(false);

    return (
        <header>
            <nav className="flex items-center justify-between flex-wrap fixed top-0 p-6 z-10 w-full bg-red-light">
                <img src={logo} alt="Logo de Four Parks" className="w-14 h-14 ml-8"/>

                <div className={`w-full flex-grow lg:items-center lg:w-auto lg:block pt-6 lg:pt-0 ${onToggle ? "" : "hidden"}`} id="nav-content">
                    <ul className="list-reset lg:flex justify-end flex-1 items-center text-xl">
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>¿Quiénes somos?</Link>
                        </li>
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>Paso a paso</Link>
                        </li>
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>Opiniones</Link>
                        </li>
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>FAQ</Link>
                        </li>
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>Registrarse</Link>
                        </li>
                        <li className="mr-16 relative">
                            <Link to="/inicio-sesion" className="inline-block font-title font-medium text-white hover:scale-105 py-2 px-4"
                                  onClick={() => setOnToggle(false)}>Iniciar sesión</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    );
}

export default Header;
