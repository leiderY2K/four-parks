import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(ArcElement, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarOccupation = ({url, actualParking, startDate}) => {
    const [occupation, setOccupation] = useState([]);
 
    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        setOccupation([]);
      
        axios.get(`${url}/statistics/occupation`, {params: {
            date: startDate,
            idParking: actualParking.id
        }, headers: {Authorization: `Bearer ${token}`}})
        .then((res) => {
            const occupationData = res.data.map(item => ({ hour: item.hour, occupation: item.occupation }));
            setOccupation(element => [...element, ...occupationData]);
        })
        .catch((err) => {
            console.log(err);
        });
    }, [actualParking, startDate]);      
 
    const labels = occupation.map((item) => item.hour);
    const sumOccupation = occupation.map((item) => item.occupation);

    const customPastelColors = [
        '#b1d4e6'
    ];
 
    const data = {
        labels: labels,
        datasets: [{
            label: '% ocupación',
            data: sumOccupation,
            backgroundColor: customPastelColors,
            borderColor: customPastelColors,
            borderWidth: 1,
        }]
    };
 
    const options = {
        plugins: {
            title: {
                display: true,
                text: 'PORCENTAJE DE OCUPACIÓN POR HORA',
            },
        },
        scales: {
            y: {
                beginAtZero: true
            },
        },
    };
 
    return <Bar data={data} options={options} />;
};
 
export default BarOccupation;