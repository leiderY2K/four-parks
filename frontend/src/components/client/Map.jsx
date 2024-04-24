import { MapContainer, TileLayer, Marker } from 'react-leaflet';
import { Icon } from 'leaflet';
import "leaflet/dist/leaflet.css";
import "../../css/map.css";

const Map = () => {
    const customIcon = new Icon({iconUrl: "../../../public/Cubierto.png", iconSize:[38, 38]})
    const markers = [
        {coords: [4.578948, -74.126637],popUp: 'Hola'},
        {coords: [4.597257, -74.122517],popUp: 'Hola'},
        {coords: [4.574247, -74.102257],popUp: 'Hola'},
        {coords: [4.574451, -74.099909],popUp: 'Hola'}
    ]
    return (
        <MapContainer center={[4.2992, -74.7952]} zoom={15} minZoom={15} maxBounds={[[4.3100, -74.8100], [4.2900, -74.7800]]} className='rounded-2xl shadow-lg '>
            <TileLayer 
                attribution='&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                url='https://tile.openstreetmap.org/{z}/{x}/{y}.png'
            />

            {markers.map(marker => {
                <Marker position={marker.coords}> </Marker>
            })}
        </MapContainer>
    );
}

export default Map;
