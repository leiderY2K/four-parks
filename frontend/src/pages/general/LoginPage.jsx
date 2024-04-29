import Login from "../../components/general/Login"
import bgImage from '../../assets/BG-PC.jpeg'

const backgroundStyle = {
    backgroundImage: `url(${bgImage})`,
    backgroundSize: 'cover'
};

const LoginPage = ({url}) => {
  return (
    <div className="h-screen" style={backgroundStyle}>
        <div className="flex items-center justify-center h-screen">
            <Login url={url} />
        </div>
    </div>
  )
}

export default LoginPage