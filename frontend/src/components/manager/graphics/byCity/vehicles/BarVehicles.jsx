import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(ArcElement, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarVehicles = ({url, actualCity}) => {
    const [idCity, setIdCity] = useState('');
    const [vehiclePerc, setVehiclePerc] = useState([]);

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
            setVehiclePerc([]);
    
            axios.get(`${url}/statistics/city-vehicle-percentage`, {params: {
                city: idCity
            }, headers: {Authorization: `Bearer ${token}`}})
            .then((res) => {
                const vehicleData = res.data.map(item => ({ vehicle: item.vehicle, percentage: item.percentage }));
                setVehiclePerc(element => [...element, ...vehicleData]);
            })
            .catch((err) => {
                console.log(err);
            });
        }
    }, [idCity]);

    const labels = vehiclePerc.map((item) => item.vehicle);
    const percentage = vehiclePerc.map((item) => item.percentage);

    const customPastelColors = [
        '#b1d4e6'
    ];

    const data = {
        labels: labels,
        datasets: [{
            label: 'Ocupación',
            data: percentage,
            backgroundColor: customPastelColors,
            borderColor: customPastelColors,
            borderWidth: 1,
        }]
    };

    const options = {
        plugins: {
            title: {
                display: true,
                text: `PORCENTAJE DE OCUPACIÓN POR TIPO DE VEHICULO EN ${actualCity.toUpperCase()}`,
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

export default BarVehicles;
