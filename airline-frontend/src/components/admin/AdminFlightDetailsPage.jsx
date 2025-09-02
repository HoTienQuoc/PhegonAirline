import { useState, useEffect } from "react";
import { useNavigate, Link, useParams } from "react-router-dom"; 
import ApiService from "../../services/ApiService";
import { useMessage } from "../common/MessageDisplay";

const AdminFlightDetailsPage = () => {
    const { id } = useParams();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useMessage();
    const navigate = useNavigate();
    const [flight, setFlight] = useState(null);
    const [loading, setLoading] = useState(true);
    const [selectedStatus, setSelectedStatus] = useState("");
    const [booking, setBooking] = useState([]);

    useEffect(()=>{
        fetchFlightDetails();
    })

    const fetchFlightDetails = async () =>{

    }
}

export default AdminFlightDetailsPage;