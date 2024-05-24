import { useState, useEffect } from "react";
import axios from "axios";
import Header from "../../components/admin/Header"
import BarHours from "../../components/admin/graphics/BarHours";
import PieHours from "../../components/admin/graphics/PieHours";
import LineHours from "../../components/admin/graphics/LineHours";
import { jsPDF } from "jspdf";
import { utils, writeFile } from "xlsx";
import * as XLSX from 'xlsx';
import html2canvas from "html2canvas";
import ExcelJS from "exceljs";

const StatisticsAdminPage = ({ url }) => {
  const [infoType, setInfoType] = useState('');
  const [graphicType, setGraphicType] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [imageData, setImageData] = useState('');

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
    if (actualParking && infoType && graphicType && startDate && endDate) {
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



  const applyBorders = (cell) => {
    cell.border = {
      top: { style: 'thin' },
      left: { style: 'thin' },
      bottom: { style: 'thin' },
      right: { style: 'thin' }
    };
  };

  const GenPDF = () => {
    
    const doc = new jsPDF();
    const graphSection = document.getElementById('graph-section');

    let tipoTemporal = '';
    let descripcionGrafica = '';
    if (infoType === "hours") {
      tipoTemporal = "Horas pico / valle";
      descripcionGrafica = `Esta gráfica de ${graphicType === 'bars' ? 'barras' : graphicType === 'circle' ? 'circular' : 'líneas'} muestra la distribución de horas pico y horas valle en el parqueadero ${actualParking.name}. El periodo analizado abarca desde ${startDate} hasta ${endDate}. La gráfica permite identificar los momentos de mayor y menor afluencia, ayudando a optimizar la gestión del espacio y los recursos del parqueadero.`;
    } else if (infoType === "sales") {
      tipoTemporal = "Ventas";
      descripcionGrafica = `Esta gráfica ${graphicType === 'bars' ? 'de barras' : graphicType === 'circle' ? 'circular' : 'de líneas'} ilustra la distribución de ventas en el parqueadero ${actualParking.name} durante el periodo comprendido entre ${startDate} y ${endDate}. La gráfica proporciona una visión clara de las fuentes de ingresos predominantes, facilitando la comprensión de las tendencias de ventas y la toma de decisiones informadas sobre estrategias comerciales.`;
    }

    let tamañoYTemp = 90;
    if (graphicType === "circle") {
      tamañoYTemp = 180;
    }


    html2canvas(graphSection).then(canvas => {
      const imgData = canvas.toDataURL('image/png');

      doc.text("Estadísticas", 95, 20, { align: "center" });
      doc.text(`Nombre del parqueadero: ${actualParking.name}`, 20, 30);
      doc.text(`Tipo de Información: ${tipoTemporal}`, 20, 40);
      doc.text(`Fecha de Inicio: ${startDate}`, 20, 50);
      doc.text(`Fecha de Fin: ${endDate}`, 20, 60);

      doc.addImage(imgData, 'PNG', 10, 70, 180, tamañoYTemp);

      doc.text(descripcionGrafica, 20, tamañoYTemp + 80, { maxWidth: 170 });

      doc.save("Estadisticas.pdf");
    });
  };



  const GenExcel = async () => {
    const graphSection = document.getElementById('graph-section');
    const canvas = await html2canvas(graphSection);
    const imgData = canvas.toDataURL('image/png');
    setImageData(imgData);
    setTimeout(() => {
      console.log("3 Segundos esperado")
    }, 3000);
    let tipoTemporal = '';
    let descripcionGrafica = '';
    if (infoType === "hours") {
      tipoTemporal = "Horas pico / valle";
      descripcionGrafica = `Esta gráfica de ${graphicType === 'bars' ? 'barras' : graphicType === 'circle' ? 'circular' : 'líneas'} muestra la distribución de horas pico y horas valle en el parqueadero ${actualParking.name}. El periodo analizado abarca desde ${startDate} hasta ${endDate}. La gráfica permite identificar los momentos de mayor y menor afluencia, ayudando a optimizar la gestión del espacio y los recursos del parqueadero.`;
    } else if (infoType === "sales") {
      tipoTemporal = "Ventas";
      descripcionGrafica = `Esta gráfica ${graphicType === 'bars' ? 'de barras' : graphicType === 'circle' ? 'circular' : 'de líneas'} ilustra la distribución de ventas en el parqueadero ${actualParking.name} durante el periodo comprendido entre ${startDate} y ${endDate}. La gráfica proporciona una visión clara de las fuentes de ingresos predominantes, facilitando la comprensión de las tendencias de ventas y la toma de decisiones informadas sobre estrategias comerciales.`;
    }

    let tamañoYTemp = 200;
    if (graphicType === "circle") {
      tamañoYTemp = 400;
    }

    try {
      const workbook = new ExcelJS.Workbook();
      const worksheet = workbook.addWorksheet('Estadísticas');

      if (imageData) {
        const imageId = workbook.addImage({
          base64: imageData,
          extension: 'png',
        });

        worksheet.addImage(imageId, {
          tl: { col: 3, row: 1 },
          ext: { width: 400, height: tamañoYTemp },
        });
      }
      

      const titulo = worksheet.getCell('B2');
      const nombrepar = worksheet.getCell('B3');
      const tipodeinformacion = worksheet.getCell('B4');
      const finicio = worksheet.getCell('B5');
      const ffin = worksheet.getCell('B6');
      const desc = worksheet.getCell("B7");

      applyBorders(worksheet.getCell('B2'));
      applyBorders(worksheet.getCell('B3'));
      applyBorders(worksheet.getCell('B4'));
      applyBorders(worksheet.getCell('B5'));
      applyBorders(worksheet.getCell('B6'));
      applyBorders(worksheet.getCell('B7'));


      //const descripcionGraficaParaExcel = descripcionGrafica.split('\n');

      titulo.value = new String("Estadisticas:");
      nombrepar.value = new String(`Nombre del parqueadero: ${actualParking.name}`);
      tipodeinformacion.value = new String(`Tipo de Información: ${tipoTemporal}`);
      finicio.value = new String(`Fecha de Inicio: ${startDate}`);
      ffin.value = new String(`Fecha de Inicio: ${endDate}`);
      desc.value = new String(descripcionGrafica);
      desc.alignment = { wrapText: true };

      worksheet.getColumn('B').width = 85;

      titulo.alignment = { vertical: 'middle', horizontal: 'center' };
      titulo.font = { bold: true };



      workbook.xlsx.writeBuffer().then(buffer => {
        const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Estadísticas.xlsx';
        a.click();
      });
    } catch (error) {
      console.error('Error al generar el archivo Excel:', error);
    }
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
            <button onClick={GenExcel} className='shadow-xl px-16 py-3 bg-blue-dark hover:bg-blue-darkest rounded-xl text-white font-title font-semibold'> Generar Excel </button>
          </div>
        </section>
      </section>
    </>
  )
}

export default StatisticsAdminPage
