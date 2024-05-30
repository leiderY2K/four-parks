import { Chart as ChartJS, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(CategoryScale, LinearScale, Title, Tooltip, Legend);

const PieHours = ({url, actualCity, startDate, endDate}) => {
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
        '#C3E8FA',
        '#B1D4E6',
        '#85D6FF',
        '#B0EBFF',
        '#1A7691', 
        '#95D6F0', 
        '#76AFCC',
        '#AEE0EB', 
        '#99C8E0', 
        '#ADD9E8', 
        '#96E0ED', 
        '#48AADB', 
        '#7EBEDE',
        '#2d6a88', 
        '#77BBDB',
        '#7DBED4',
        '#448EB3',
        '#C0DDEB',
        '#A2CADE',
        '#1A4B63', 
        '#4C839E',
        '#87B3C9',
        '#5D94B0',
        '#205d7b' 
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
        }
    };

    return <Pie data={data} options={options} />;
};

export default PieHours;
