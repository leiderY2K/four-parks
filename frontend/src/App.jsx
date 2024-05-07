import { BrowserRouter, Route, Routes } from "react-router-dom"
import HomeClientPage from "./pages/client/HomeClientPage"
import LoginPage from "./pages/general/LoginPage"
import SignUpClientPage from "./pages/client/SignUpClientPage"
import ReservationPage from "./pages/client/ReservationsPage";

function App() {
  const url = "http://localhost:8080";

  return (
    <BrowserRouter>
      <Routes>
        
          <Route path="/" element={<LoginPage url={url} />} />
          <Route path="/inicio-sesion" element={<LoginPage url={url} />} />
          <Route path="/registro" element={<SignUpClientPage url={url} />} />
          <Route path="cliente-inicio" element={<HomeClientPage url={url} />}/>
          <Route path="mis-reservas" element={<ReservationPage url={url} />}/>
      </Routes>
    </BrowserRouter>
  )
}
export default App
