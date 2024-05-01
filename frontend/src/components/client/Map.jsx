import { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import { Icon, divIcon, point } from 'leaflet';
import MarkerClusterGroup from 'react-leaflet-cluster'
import axios from "axios";
import coveredIcon from '../../assets/Cubierto.png';
import uncoveredIcon from '../../assets/Descubierto.png';
import semicoveredIcon from '../../assets/Semidescubierto.png';
import "leaflet/dist/leaflet.css";
import "../../css/map.css";

const Map = ({ url, city }) => {
    const [parkings, setParkings] = useState([]);

    useEffect(() => {
        const token = sessionStorage.getItem('token').replace(/"/g, '');
        axios.get(`${url}/client/getParkings`, {
            params: { city: city },
            headers: { Authorization: `Bearer ${token}` }
        })
        .then(res => {
            const parkingArray = res.data.map(parking => ({
                id: parking.idParking,
                name: parking.namePark,
                coords: [parking.addressCoordinatesX, parking.addressCoordinatesY],
                type: parking.parkingType.descParkingType
            }));
            setParkings(parkingArray);
        })
        .catch(err => {
            console.error(err.response || err);
        });
    }, [city]);

    const getIcon = (type) => {
        switch (type) {
            case 'Covered':
                return new Icon({iconUrl: coveredIcon, iconSize: [35, 35]});
            case 'Uncovered':
                return new Icon({iconUrl: uncoveredIcon, iconSize: [35, 35]});
            case 'Semi-covered':
                return new Icon({iconUrl: semicoveredIcon, iconSize: [35, 35]});
            default:
                return new Icon({iconUrl: coveredIcon, iconSize: [35, 35]});
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

    return (
        <MapContainer center={[4.2992, -74.7952]} zoom={15} minZoom={14} maxBounds={[[4.3145, -74.8185], [4.2839, -74.7744]]} className='rounded-2xl shadow-lg '>
            <TileLayer 
                attribution='&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                url='https://tile.openstreetmap.org/{z}/{x}/{y}.png'
            />

            <MarkerClusterGroup chunkedLoading iconCreateFunction={createCustomClusterIcon}>
                {parkings.map(parking => (
                    <Marker key={parking.id} position={parking.coords} icon={getIcon(parking.type)} > 
                        <Popup>{parking.name}</Popup>
                    </Marker>
                ))}
            </MarkerClusterGroup>
        </MapContainer>
    );
}

export default Map;
