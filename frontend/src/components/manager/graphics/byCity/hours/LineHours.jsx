import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import { Line } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const LineHours = ({url, actualCity, startDate, endDate}) => {
    const [idCity, setIdCity] = useState('');
    const [hoursAverage, setHoursAverage] = useState([]);

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
            setHoursAverage([]);
    
            axios.get(`${url}/statistics/city-average-hour`, {params: {
                initialDate: startDate,
                finalDate: endDate,
                city: idCity
            }, headers: {Authorization: `Bearer ${token}`}})
            .then((res) => {
                const hoursData = res.data.map(item => ({ hour: item.hour, average: item.average }));
                setHoursAverage(element => [...element, ...hoursData]);
            })
            .catch((err) => {
                console.log(err);
            });
        }
    }, [idCity, startDate, endDate]);

    const labels = hoursAverage.map((item) => item.hour);
    const average = hoursAverage.map((item) => item.average);

    const customPastelColors = [
        '#b1d4e6'
    ];

    const data = {
        labels: labels,
        datasets: [{
            label: 'Reservas',
            data: average,
            backgroundColor: customPastelColors,
            borderColor: customPastelColors,
            borderWidth: 1,
        }]
    };

    const options = {
        plugins: {
            title: {
                display: true,
                text: `PROMEDIO DE RESERVAS POR HORA EN ${actualCity.toUpperCase()}`,
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

export default LineHours;
