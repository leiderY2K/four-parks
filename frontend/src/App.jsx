import { useState, useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom"
import HomeClientPage from "./pages/client/HomeClientPage"
import LoginPage from "./pages/general/LoginPage"
import SignUpClientPage from "./pages/client/SignUpClientPage"
import ReservationPage from "./pages/client/ReservationsPage";
import HomeAdminPage from "./pages/admin/HomeAdminPage";
import StatisticsAdminPage from "./pages/admin/StatisticsAdminPage";
import HomeManagerPage from "./pages/manager/HomeManagerPage";
import StatisticsManagerPage from "./pages/manager/StatisticsManagerPage";
//import StatisticsPage from "./pages/admin/StatisticsPage";
import PasswordChangePage from "./pages/general/PasswordChangePage";
import SignUpClientAdminPage from "./pages/admin/SignUpClientAdminPage";
import SignUpAdminPage from "./pages/manager/SignUpAdminPage";
import ForgotPassPage from "./pages/client/ForgotPassPage";

function App() {
  const url = "http://localhost:8080";
  const [initialCoords, setInitialCoords] = useState([]);

  const options = { enableHighAccuracy: true, timeout: 20000, maximumAge: 0 };

    function errors(err) {
        console.warn(`ERROR(${err.code}): ${err.message}`);
    }

    function geolocalizate() {
        if (navigator.geolocation) {
            navigator.permissions
                .query({ name: "geolocation" })
                .then(function (result) {
                    if (result.state === "granted" || result.state === "prompt") {
                        let attempts = 0;
                        const maxAttempts = 5;
                        const minAccuracy = 100; 

                        const getLocationWithRetry = () => {
                            navigator.geolocation.getCurrentPosition(
                                (position) => {
                                    if (position.coords.accuracy <= minAccuracy) {
                                        setInitialCoords([position.coords.latitude, position.coords.longitude]);
                                    } else {
                                        attempts++;
                                        if (attempts < maxAttempts) {
                                            console.log(`Intento ${attempts}: La precisión de la ubicación no es suficiente. Reintentando...`);
                                            setTimeout(getLocationWithRetry, 3000); 
                                        } else {
                                            console.log(`Se han agotado los intentos. No se pudo obtener una precisión aceptable.`);
                                        }
                                    }
                                },
                                errors,
                                options
                            );
                        };

                        getLocationWithRetry();
                    } else if (result.state === "denied") {
                        console.log("La geolocalización está desactivada.");
                    }
                });
        } else {
            console.log("La geolocalización no es compatible con este navegador.");
        }
    }

    useEffect(() => {
        geolocalizate();
    }, []);

  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<LoginPage url={url} />} />
          <Route path="/inicio-sesion" element={<LoginPage url={url} />} />
          <Route path="/registro" element={<SignUpClientPage url={url} />} />
          <Route path="/cliente-inicio" element={<HomeClientPage url={url} initialCoords={initialCoords} />}/>
          <Route path="/mis-reservas" element={<ReservationPage url={url} />}/>

          <Route path="/admin-inicio" element={<HomeAdminPage url={url} initialCoords={initialCoords} />}/>
          <Route path="/admin-ver-estadisticas" element={<StatisticsAdminPage url={url} />}/>
          <Route path="/registrar-cliente" element={<SignUpClientAdminPage url={url}/>} />
          
          <Route path="/manager-inicio" element={<HomeManagerPage url={url} initialCoords={initialCoords} />}/>
          <Route path="/manager-ver-estadisticas" element={<StatisticsManagerPage url={url} />}/>
          <Route path="/ver-estadisticas" element={<StatisticsAdminPage url={url} />}/>
          <Route path="/cambio-contraseña" element={<PasswordChangePage url={url}/>} />
          <Route path="/agregar-administrador" element={<SignUpAdminPage url={url}/>} />
          <Route path="/recuperacion-contraseña" element={<ForgotPassPage url={url}/>} />

          


      </Routes>
    </BrowserRouter>
  )
}
export default App
