import { useEffect } from 'react';
import bgImage from '../../assets/BG-PC.jpeg'
import SignUpAdmin from "../../components/manager/SignUpAdmin";
import Header from "../../components/manager/Header";


const SignUpAdminPage = ({ url }) => {

    return (
        <section>
            <Header/>
            <div className="h-screen">
                <div className="flex items-center justify-center h-screen">
                    <SignUpAdmin url={url} />
                </div>
            </div>
        </section>
    )
}

export default SignUpAdminPage