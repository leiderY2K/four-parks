import { useEffect } from 'react';
import bgImage from '../../assets/BG-PC.jpeg'
//import SignUp from '../../components/client/SignUp';
import SignUpClient from "../../components/admin/SignUpClient";
import Header from "../../components/admin/Header";




const SignUpClientAdminPage = ({ url }) => {

    return (
        <section>
            <Header />
            <div className="h-screen" >
                <div className="flex items-center justify-center h-screen">
                    <SignUpClient url={url} />
                </div>
            </div>
        </section>
    )
}

export default SignUpClientAdminPage