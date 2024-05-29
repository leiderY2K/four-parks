import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import axios from "axios";

ChartJS.register(ArcElement, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarSales = ({url, actualParking, startDate, endDate}) => {
    const [sales, setSales] = useState([]);
 
    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        setSales([]);
      
        axios.get(`${url}/statistics/sales`, {params: {
            initialDate: startDate,
            finalDate: endDate,
            idParking: actualParking.id
        }, headers: {Authorization: `Bearer ${token}`}})
        .then((res) => {
            const salesData = res.data.map(item => ({ date: item.date, sum: item.sum }));
            setSales(element => [...element, ...salesData]);
        })
        .catch((err) => {
            console.log(err);
        });
    }, [actualParking, startDate, endDate]);      
 
    const labels = sales.map((item) => item.date);
    const sum = sales.map((item) => item.sum);

    const customPastelColors = [
        '#b1d4e6'
    ];
 
    const data = {
        labels: labels,
        datasets: [{
            label: 'Ventas',
            data: sum,
            backgroundColor: customPastelColors,
            borderColor: customPastelColors,
            borderWidth: 1,
        }]
    };
 
    const options = {
        plugins: {
            title: {
                display: true,
                text: 'CANTIDAD DE VENTAS POR DÍA',
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
 
export default BarSales;