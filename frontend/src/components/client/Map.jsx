import { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet';
import { Icon, divIcon, point } from 'leaflet';
import MarkerClusterGroup from 'react-leaflet-cluster'
import axios from "axios";
import coveredWhiteIcon from '../../assets/Parking Icons/Covered-White.png';
import coveredOrangeIcon from '../../assets/Parking Icons/Covered-Orange.png';
import coveredRedIcon from '../../assets/Parking Icons/Covered-Red.png';
import uncoveredWhiteIcon from '../../assets/Parking Icons/Uncovered-White.png';
import uncoveredOrangeIcon from '../../assets/Parking Icons/Uncovered-Orange.png';
import uncoveredRedIcon from '../../assets/Parking Icons/Uncovered-Red.png';
import semicoveredWhiteIcon from '../../assets/Parking Icons/Semicovered-White.png';
import semicoveredOrangeIcon from '../../assets/Parking Icons/Semicovered-Orange.png';
import semicoveredRedIcon from '../../assets/Parking Icons/Semicovered-Red.png';
import "leaflet/dist/leaflet.css";
import "../../css/map.css";

const Map = ({ url, city, parkingType, availability, vehicleType, date, startTime, endTime, actualCity, setActualCity, setActualParking, setOnReservationForm }) => {
    const [parkings, setParkings] = useState([]);



    useEffect(() => {
        if(city) {
            const token = sessionStorage.getItem('token').replace(/"/g, '');
    
            axios.get(`${url}/client/getCity`,  {params: {city: city}, headers: {Authorization: `Bearer ${token}`}})
            .then(res => {
                const cityObject = {
                    id: res.data.idCity,
                    name: res.data.name,
                    northLim: [res.data.btop, res.data.bleft],
                    southLim: [res.data.bbottom, res.data.bright],
                    centerCoords: [res.data.xcenter, res.data.ycenter]
                }
    
                setActualCity(cityObject);
            })
            .catch(err => {
                console.log(err);
            })
        }
    }, [city]);

    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        const params = {};

        (city ? params.city = city : params.city = 'Bogota');
        if (parkingType) params.type = parkingType;
        if (availability) params.scheduleType = availability;
        if (vehicleType) params.vehicleType = vehicleType;
        if (date) params.date = date;
        if (startTime) params.startTime = startTime;
        if (endTime) params.endTime = endTime;
        
        axios.get(`${url}/client/getParkings`, {
            params: params,
            headers: { Authorization: `Bearer ${token}` }
        })
        .then(res => {
            const parkingArray = res.data.map(parking => ({
                id: parking.idParking,
                name: parking.namePark,
                coords: [parking.addressCoordinatesX, parking.addressCoordinatesY],
                type: parking.parkingType.idParkingType,
                ocupability: parking.ocupability
            }));

            setParkings(parkingArray);
        })
        .catch(err => {
            console.error(err.response || err);
        });
    }, [city, parkingType, availability, vehicleType, date, startTime, endTime]);

    const getIcon = (type, ocupability) => {
        switch (type) {
            case 'COV':
                return new Icon({iconUrl: (ocupability >= 1 ? coveredRedIcon : (ocupability >= 0.7 ? coveredOrangeIcon : coveredWhiteIcon)), iconSize: [45, 45]});
            case 'UNC':
                return new Icon({iconUrl: (ocupability >= 1 ? uncoveredRedIcon : (ocupability >= 0.7 ? uncoveredOrangeIcon : uncoveredWhiteIcon)), iconSize: [45, 45]});
            case 'SEC':
                return new Icon({iconUrl: (ocupability >= 1 ? semicoveredRedIcon : (ocupability >= 0.7 ? semicoveredOrangeIcon : semicoveredWhiteIcon)), iconSize: [45, 45]});
            default:
                return new Icon({iconUrl: (ocupability >= 1 ? coveredRedIcon : (ocupability >= 0.7 ? coveredOrangeIcon : coveredWhiteIcon)), iconSize: [45, 45]});
        }
    };

    const createCustomClusterIcon = (cluster) => {
        return new divIcon({
            html: `<div class="flex justify-center items-center w-12 h-12 rounded-full bg-blue-dark opacity-85 font-semibold text-lg -translate-x-1/4 -translate-y-1/4"> 
            ${cluster.getChildCount()} </div>`,
            className: 'custom-marker-cluster',
            iconSize: point(35, 35, true)
        })
    }

    const handleChangeParking = (parking) => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        
        axios.get(`${url}/client/getParkingByCoordinates`, {params: {coordinateX: parking.coords[0], coordinateY: parking.coords[1]}, 
        headers: {Authorization: `Bearer ${token}`}})
        .then(res => {
            setActualParking([res.data.parking, res.data.capacity]);
            setOnReservationForm(false);

            let newCenterCoords = actualCity;
            newCenterCoords.centerCoords = [parking.coords[0], parking.coords[1]];

            setActualCity(newCenterCoords);
        })
        .catch(err => {
            console.log(err);
        })
    }
    
    const UpdateMap = ({ city, center, bounds }) => {
        const map = useMap();
    
        useEffect(() => {
            map.setView(center, 15); 
            map.setMaxBounds([bounds[0], bounds[1]]);
        }, [city]);
    
        return null;
    }

    return (
        <MapContainer minZoom={12} maxBounds={[actualCity.northLim, actualCity.southLim]} className='rounded-2xl shadow-lg'>
            <TileLayer 
                attribution='&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                url='https://tile.openstreetmap.org/{z}/{x}/{y}.png'
            />

            <UpdateMap city={city} center={actualCity.centerCoords} bounds={[actualCity.northLim, actualCity.southLim]} />


            <MarkerClusterGroup chunkedLoading iconCreateFunction={createCustomClusterIcon}>
                {parkings.map(parking => (
                    <Marker key={parking.id} position={parking.coords} icon={getIcon(parking.type, parking.ocupability)} 
                    eventHandlers={{click: () => handleChangeParking(parking)}}> 
                        <Popup>{parking.name}</Popup>
                    </Marker>
                ))}
            </MarkerClusterGroup>
        </MapContainer>
    );
}

export default Map;
