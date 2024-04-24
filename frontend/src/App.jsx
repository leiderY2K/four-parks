import { BrowserRouter, Route, Routes } from "react-router-dom"
import HomeClientPage from "./pages/client/HomeClientPage"
import LoginPage from "./pages/general/LoginPage"
import SignUpClientPage from "./pages/client/SignUpClientPage"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        
          <Route path="/" element={<LoginPage />} />
          <Route path="/inicio-sesion" element={<LoginPage />} />
          <Route path="/registro" element={<SignUpClientPage />} />
          <Route path="cliente-inicio" element={<HomeClientPage />}/>

      </Routes>
    </BrowserRouter>
  )
}
export default App
