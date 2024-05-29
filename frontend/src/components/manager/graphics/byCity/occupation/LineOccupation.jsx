import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import { Line } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const LineOccupation = ({url, actualCity, startDate}) => {
    const [idCity, setIdCity] = useState('');
    const [occupation, setOccupation] = useState([]);
 
    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');

        axios.get(`${url}/city/${actualCity}`, {headers: {Authorization: `Bearer ${token}`}})
        .then((res) => {
            setIdCity(res.data.idCity);
        })
        .catch((err) => {
            console.log(err)
        })
    }, [actualCity]);  

    useEffect(() => {
        if(idCity) {
            const token = sessionStorage.getItem('token').replace(/"/g, '');
            setOccupation([]);
          
            axios.get(`${url}/statistics/city-occupation`, {params: {
                date: startDate,
                city: idCity
            }, headers: {Authorization: `Bearer ${token}`}})
            .then((res) => {
                const occupationData = res.data.map(item => ({ hour: item.hour, occupation: item.occupation }));
                setOccupation(element => [...element, ...occupationData]);
            })
            .catch((err) => {
                console.log(err);
            });
        }
    }, [idCity, startDate]);      
 
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
                text: `PORCENTAJE DE OCUPACIÓN POR HORA  EN ${actualCity.toUpperCase()}`,
            },
        },
        scales: {
            y: {
                beginAtZero: true
            },
        },
    };

    return <Line data={data} options={options} />;
};

export default LineOccupation;
