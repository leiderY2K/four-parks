import { useState, useEffect } from "react";
import axios from "axios";
import Header from "../../components/admin/Header"
import BarHours from "../../components/admin/graphics/BarHours";
import PieHours from "../../components/admin/graphics/PieHours";
import LineHours from "../../components/admin/graphics/LineHours";
import { jsPDF } from "jspdf";
import { read, writeFileXLSX } from "xlsx";
import html2canvas from "html2canvas";

const StatisticsAdminPage = ({ url }) => {
  const [infoType, setInfoType] = useState('');
  const [graphicType, setGraphicType] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [actualParking, setActualParking] = useState({
    id: "",
    name: ""
  });

  useEffect(() => {
    const token = sessionStorage.getItem('token').replace(/"/g, '');
    const user = JSON.parse(sessionStorage.getItem('userLogged'));

    axios.get(`${url}/parking/admin/${user.idType}/${user.idNumber}`, { headers: { Authorization: `Bearer ${token}` } })
      .then(res => {
        setActualParking({
          id: res.data.parking.parkingId.idParking,
          name: res.data.parking.namePark
        });
      })
      .catch(err => {
        console.error(err);
      });
  }, []);

    const createHoursGraph = () => {
      if(actualParking && infoType && graphicType && startDate && endDate) {
        switch (graphicType) {
            case 'bars':
                return (
                  <section className="w-10/12 mt-5 mx-auto" id="graph-section">
                      <div className="border bg-white p-6 rounded-md shadow-md overflow-hidden">
                        <BarHours url={url} actualParking={actualParking} startDate={startDate} endDate={endDate} />
                      </div>
                  </section> 
                );
            case 'circle':
                return (
                  <section className="w-10/12 mt-5 mx-auto" id="graph-section">
                      <div className="border bg-white p-6 rounded-md shadow-md overflow-hidden">
                        <PieHours url={url} actualParking={actualParking} startDate={startDate} endDate={endDate} />
                      </div>
                  </section> 
                );
            case 'lines':
                return (
                  <section className="w-10/12 mt-5 mx-auto" id="graph-section">
                      <div className="border bg-white p-6 rounded-md shadow-md overflow-hidden">
                        <LineHours url={url} actualParking={actualParking} startDate={startDate} endDate={endDate} />
                      </div>
                  </section> 
                );
            default:
                return null;
        }
      }
    }


  const GenPDF = () => {
    const doc = new jsPDF();
    const graphSection = document.getElementById('graph-section');
    if (infoType=="hours") {
      setInfoType("Horas pico / valle")      
    }


    html2canvas(graphSection).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      doc.text("Estadisticas:", 95, 20);
      doc.text(`Parqueadero: ${actualParking.name}`, 20, 30);  
      doc.text(`Tipo de Información: ${infoType}`, 20, 40);   
      doc.text(`Fecha de Inicio: ${startDate}`, 20, 50);     
      doc.text(`Fecha de Fin: ${endDate}`, 20, 60);   
      doc.addImage(imgData, 'PNG', 10, 70, 180, 90);
      doc.save("Estadisticas.pdf");
    }
  );
  };

  return (
    <>
      <Header />

      <section className='h-screen px-12 py-36 mb-28 bg-gray-light'>
        <section className="flex justify-between w-full">
          <div id="statistics-parkings" className="w-1/6 h-12 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph"> {actualParking.name} </div>

          <select id="statistics-info" value={infoType} className="w-1/6 h-12 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph"
            onChange={(e) => setInfoType(e.target.value)}>
            <option value="" disabled selected hidden> Tipo de información </option>
            <option value=""></option>
            <option value="hours"> Horas pico / valle </option>
            <option value="sales"> Ventas </option>
          </select>

          <select id="statistics-graphic" value={graphicType} className="w-1/6 h-12 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph"
            onChange={(e) => setGraphicType(e.target.value)}>
            <option value="" disabled selected hidden> Tipo de gráfica </option>
            <option value=""></option>
            <option value="circle"> Circular </option>
            <option value="bars"> Barras </option>
            <option value="lines"> Línea </option>
          </select>

          <input type="date" id="statistics-startDate" value={startDate} className="w-1/6 h-12 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph"
            onChange={(e) => setStartDate(e.target.value)}></input>

          <input type="date" id="statistics-endDate" value={endDate} className="w-1/6 h-12 mr-12 mb-6 p-3 rounded-md bg-white shadow-md font-paragraph"
            onChange={(e) => setEndDate(e.target.value)}></input>
        </section>

        {createHoursGraph()}
        <section className="mt-6 flex justify-end">
          <div className="">
            <button onClick={GenPDF} className='shadow-xl px-16 py-3 mr-6 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'> Generar PDF </button>
            <button className='shadow-xl px-16 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'> Generar Excel </button>

          </div>

        </section>
      </section>

    </>
  )
}

export default StatisticsAdminPage

