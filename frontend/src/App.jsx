import { BrowserRouter, Route, Routes } from "react-router-dom"
import Home from "./pages/client/Home"
import Login from "./components/client/Login.jsx"
import Registro from "./components/client/Registro.jsx"

function App() {
  return (
    <BrowserRouter>
    <Routes>
      
        <Route  path="/*" element={<div className="w-full flex items-center justify-center h-screen"> <Login /> </div>} />
        <Route path="inicio-cliente" element={<Home />}/>

    </Routes>
    </BrowserRouter>
  )
}
export default App
