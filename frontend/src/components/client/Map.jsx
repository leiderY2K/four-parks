import { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import { Icon, divIcon, point } from 'leaflet';
import MarkerClusterGroup from 'react-leaflet-cluster'
import axios from "axios";
import coveredIcon from '../../assets/CoveredIcon.png';
import uncoveredIcon from '../../assets/UncoveredIcon.png';
import semicoveredIcon from '../../assets/SemicoveredIcon.png';
import "leaflet/dist/leaflet.css";
import "../../css/map.css";

const Map = ({ url, city, parkingType, availability, startTime, endTime, actualCity, setActualCity, setActualParking }) => {
    const [parkings, setParkings] = useState([]);

    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');

        axios.get(`http://localhost:8080/client/getCity`,  {params: {city: city}, headers: {Authorization: `Bearer ${token}`}})
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
    }, [city]);

    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        const params = { city };

        if (parkingType) params.type = parkingType;
        if (availability) params.scheduleType = availability;
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
                type: parking.parkingType.idParkingType
            }));

            setParkings(parkingArray);
        })
        .catch(err => {
            console.error(err.response || err);
        });
    }, [city, parkingType, availability, startTime, endTime]);

    const getIcon = (type) => {
        switch (type) {
            case 'COV':
                return new Icon({iconUrl: coveredIcon, iconSize: [45, 45]});
            case 'UNC':
                return new Icon({iconUrl: uncoveredIcon, iconSize: [45, 45]});
            case 'SEC':
                return new Icon({iconUrl: semicoveredIcon, iconSize: [45, 45]});
            default:
                return new Icon({iconUrl: coveredIcon, iconSize: [45, 45]});
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
            setActualParking(res.data.parking);
        })
        .catch(err => {
            console.log(err);
        })
    }

    return (
        <MapContainer center={actualCity.centerCoords} zoom={15} minZoom={12} maxBounds={[actualCity.northLim, actualCity.southLim]} className='rounded-2xl shadow-lg' 
        whenCreated={mapInstance => {
            console.log("Mapa creado", mapInstance);
        }}>
            <TileLayer 
                attribution='&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                url='https://tile.openstreetmap.org/{z}/{x}/{y}.png'
            />

            <MarkerClusterGroup chunkedLoading iconCreateFunction={createCustomClusterIcon}>
                {parkings.map(parking => (
                    <Marker key={parking.id} position={parking.coords} icon={getIcon(parking.type)} eventHandlers={{click: () => handleChangeParking(parking)}}> 
                        <Popup>{parking.name}</Popup>
                    </Marker>
                ))}
            </MarkerClusterGroup>
        </MapContainer>
    );
}

export default Map;
