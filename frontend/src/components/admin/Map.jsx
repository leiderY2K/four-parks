import { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet';
import { Icon, divIcon, point } from 'leaflet';
import MarkerClusterGroup from 'react-leaflet-cluster'
import axios from "axios";
import coveredWhiteIcon from '../../assets/Parking Icons/Covered-White.png';
import uncoveredWhiteIcon from '../../assets/Parking Icons/Uncovered-White.png';
import semicoveredWhiteIcon from '../../assets/Parking Icons/Semicovered-White.png';
import "leaflet/dist/leaflet.css";
import "../../css/map.css";

const Map = ({ url, city, actualCity, setActualCity, setActualParking }) => {
    const [parkings, setParkings] = useState([]);

    useEffect(() => {
        if(city) {
            const token = sessionStorage.getItem('token').replace(/"/g, '');
    
            axios.get(`${url}/admin/getCity`,  {params: {city: city}, headers: {Authorization: `Bearer ${token}`}})
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
        const user = JSON.parse(sessionStorage.getItem('userLogged'));
        
        axios.post(`${url}/admin/searchParkings`, {idUser: user.idNumber, idDocType: user.idType}, {headers: { Authorization: `Bearer ${token}` }})
        .then(res => {
            console.log(res)
            const parkingArray = res.data.map(parking => ({
                id: parking.parking.idParking,
                name: parking.parking.namePark,
                idCity: parking.parking.city.idCity,
                city: parking.parking.city.name,
                coords: [parking.parking.addressCoordinatesX, parking.parking.addressCoordinatesY],
                email: parking.parking.email,
                phone: parking.parking.phone,
                idType: parking.parking.parkingType.idParkingType,
                type: parking.parking.parkingType.descParkingType,
                idAvailability: parking.parking.schedule.idSchedule,
                availability: parking.parking.schedule.scheduleType,
                startTime: parking.parking.startTime,
                endTime: parking.parking.endTime
            }));

            setParkings(parkingArray);
        })
        .catch(err => {
            console.error(err.response || err);
        });
    }, [city]);

    const getIcon = (type) => {
        switch (type) {
            case 'COV':
                return new Icon({iconUrl: coveredWhiteIcon, iconSize: [45, 45]});
            case 'UNC':
                return new Icon({iconUrl: uncoveredWhiteIcon, iconSize: [45, 45]});
            case 'SEC':
                return new Icon({iconUrl: semicoveredWhiteIcon, iconSize: [45, 45]});
            default:
                return new Icon({iconUrl: coveredWhiteIcon, iconSize: [45, 45]});
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
        setActualParking(parking);

        let newCenterCoords = actualCity;
        newCenterCoords.centerCoords = [parking.coords[0], parking.coords[1]];

        setActualCity(newCenterCoords);
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
                    <Marker key={parking.id} position={parking.coords} icon={getIcon(parking.idType, parking.ocupability)} 
                    eventHandlers={{click: () => handleChangeParking(parking)}}> 
                        <Popup>{parking.name}</Popup>
                    </Marker>
                ))}
            </MarkerClusterGroup>
        </MapContainer>
    );
}

export default Map;
