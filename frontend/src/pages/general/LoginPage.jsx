import Login from "../../components/general/Login"
import bgImage from '../../assets/BG-PC.jpeg'

const backgroundStyle = {
    backgroundImage: `url(${bgImage})`,
    backgroundSize: 'cover'
};

const LoginPage = () => {
  return (
    <div className="h-screen" style={backgroundStyle}>
        <div className="flex items-center justify-center h-screen">
            <Login />
        </div>
    </div>
  )
}

export default LoginPage