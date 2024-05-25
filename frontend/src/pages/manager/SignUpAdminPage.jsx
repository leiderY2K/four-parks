import { useEffect } from 'react';
import bgImage from '../../assets/BG-PC.jpeg'
//import SignUp from '../../components/client/SignUp';
import SignUpClient from "../../components/admin/SignUpClient";

const backgroundStyle = {
    backgroundImage: `url(${bgImage})`,
    backgroundSize: 'cover'
};

const SignUpClientAdminPage = ({url}) => {

    return (
        <div className="h-screen" style={backgroundStyle}>
            <div className="flex items-center justify-center h-screen">
                <SignUpClient url={url} />
            </div>
        </div>
    )
}

export default SignUpClientAdminPage