import { useState, useEffect } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom"; 
import ApiService from "../../services/ApiService";
import { useMessage } from "../common/MessageDisplay";

const BookingPage = () => {
    const { id: flightId } = useParams();
    const { state } = useLocation();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useMessage();
    const navigate = useNavigate();

    const [flight, setFlight] = useState(null);
    const [loading, setLoading] = useState(true);
    const [availableSeats, setAvailableSeats] = useState([]);
    const [passengers, setPassengers] = useState([
        {
            firstName: "",
            lastName: "",
            passportNumber: "",
            type: "ADULT",
            seatNumber: "",
            specialRequests: "",
        }
    ]);

    useEffect(() => {
        if (state?.flight) {
            console.log("Flight Found from State");
            setFlight(state.flight);
            setLoading(false);
            generateAvailableSeats(state.flight);
        } else {
            console.log("Flight Not Found from State");
            fetchFlightDetails();
        }
    }, [flightId]);

    const fetchFlightDetails = async () => {
        try {
            setLoading(true);
            const response = await ApiService.getFlightById(flightId);
            setFlight(response.data);
            generateAvailableSeats(response.data);
        } catch (error) {
            showError(error.response?.data?.message || "Failed to fetch flight details");
        } finally {
            setLoading(false);
        }
    };

    const generateAvailableSeats = (flightData) => {
        const seats = [];
        for (let i = 1; i <= 20; i++) {
            for (let j = 0; j < 6; j++) { // A-F
                const seatLetter = String.fromCharCode(65 + j);
                seats.push(`${i}${seatLetter}`);
            }
        }
        setAvailableSeats(seats);
    };

    const addPassenger = () => {
        setPassengers([
            ...passengers,
            {
                firstName: "",
                lastName: "",
                passportNumber: "",
                type: "ADULT",
                seatNumber: "",
                specialRequests: "",
            }
        ]);
    };

    const removePassenger = (index) => {
        if (passengers.length <= 1) return;
        const updatedPassengers = [...passengers];
        updatedPassengers.splice(index, 1);
        setPassengers(updatedPassengers);
    };

    const handlePassengerChange = (index, field, value) => {
        const updatedPassengers = [...passengers];
        updatedPassengers[index][field] = value;
        setPassengers(updatedPassengers);
    };

    const calculateTotalPrice = () => {
        if (!flight || !passengers) return 0;

        return passengers.reduce((total, passenger) => {
            let price = flight.basePrice;

            if (passenger.type === "CHILD") {
                price *= 0.75;
            } else if (passenger.type === "INFANT") {
                price *= 0.1;
            }

            return total + price;
        }, 0);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validation
        for (let i = 0; i < passengers.length; i++) {
            const p = passengers[i];
            if (!p.firstName || !p.lastName || !p.passportNumber || !p.seatNumber) {
                showError(`Please fill all required fields for Passenger ${i + 1}`);
                return;
            }
        }

        // Check for duplicate passport numbers
        const passportNumbers = passengers.map(p => p.passportNumber);
        const uniquePassports = new Set(passportNumbers);
        if (passportNumbers.length !== uniquePassports.size) {
            showError("Duplicate passport numbers found among passengers.");
            return;
        }

        try {
            const bookingData = {
                flightId: parseInt(flightId),
                passengers: passengers
            };
            const response = await ApiService.createBooking(bookingData);

            if (response.status === 200) {
                showSuccess("Booking created successfully");
                navigate(`/profile`);
            }
        } catch (error) {
            showError(error.response?.data?.message || "Failed to create booking");
        }
    };

    if (loading) return <div className="booking-loading">Loading Flight details</div>;
    if (!flight) return <div className="booking-error">Flight Not Found</div>;

    return (
        <div className="booking-page">
            <div className="booking-container">
                <SuccessDisplay />
                <ErrorDisplay />
                <h2 className="booking-title">Book Flight {flight.flightNumber}</h2>

                <div className="flight-summary">
                    <div className="route">
                        <span className="departure">
                            {flight.departureAirport.iataCode} ☞ {flight.arrivalAirport.iataCode}
                        </span>
                        <span className="date">
                            {new Date(flight.departureTime).toLocaleDateString([], {
                                weekday: "short",
                                month: "short",
                                day: "numeric",
                                year: "numeric"
                            })}
                        </span>
                    </div>
                    <div className="times">
                        <div className="departure-time">
                            {new Date(flight.departureTime).toLocaleTimeString([], {
                                hour: "2-digit",
                                minute: "2-digit"
                            })}
                        </div>
                        <div className="arrival-time">
                            {new Date(flight.arrivalTime).toLocaleTimeString([], {
                                hour: "2-digit",
                                minute: "2-digit"
                            })}
                        </div>
                        <div className="price">
                            Basic Price: ${flight.basePrice.toFixed(2)}
                        </div>
                    </div>

                    <form onSubmit={handleSubmit} className="booking-form">
                        <div className="passengers-section">
                            <h3 className="passengers-title">Passengers</h3>
                            {passengers.map((passenger, index) => (
                                <div key={index} className="passenger-card">
                                    <div className="passenger-header">
                                        <h4>Passenger {index + 1}</h4>
                                        {passengers.length > 1 && (
                                            <button type="button" className="remove-passenger" onClick={() => removePassenger(index)}>
                                                Remove
                                            </button>
                                        )}
                                    </div>
                                    <div className="passenger-form">
                                        <div className="form-group">
                                            <label>First Name*</label>
                                            <input
                                                type="text"
                                                value={passenger.firstName}
                                                onChange={(e) => handlePassengerChange(index, 'firstName', e.target.value)}
                                                required
                                            />
                                        </div>
                                        <div className="form-group">
                                            <label>Last Name*</label>
                                            <input
                                                type="text"
                                                value={passenger.lastName}
                                                onChange={(e) => handlePassengerChange(index, 'lastName', e.target.value)}
                                                required
                                            />
                                        </div>
                                        <div className="form-group">
                                            <label>Passport Number*</label>
                                            <input
                                                type="text"
                                                value={passenger.passportNumber}
                                                onChange={(e) => handlePassengerChange(index, 'passportNumber', e.target.value)}
                                                required
                                            />
                                        </div>
                                        <div className="form-group">
                                            <label>Type*</label>
                                            <select
                                                value={passenger.type}
                                                onChange={(e) => handlePassengerChange(index, 'type', e.target.value)}
                                                required
                                            >
                                                <option value="ADULT">Adult</option>
                                                <option value="CHILD">Child</option>
                                                <option value="INFANT">Infant</option>
                                            </select>
                                        </div>
                                        <div className="form-group">
                                            <label>Seat Number*</label>
                                            <select
                                                value={passenger.seatNumber}
                                                onChange={(e) => handlePassengerChange(index, 'seatNumber', e.target.value)}
                                                required
                                            >
                                                <option value="">Select Seat</option>
                                                {availableSeats.map(seat => (
                                                    <option
                                                        key={`seat-${index}-${seat}`}
                                                        value={seat}
                                                        disabled={passengers.some((p, i) => i !== index && p.seatNumber === seat)}
                                                    >
                                                        {seat}
                                                    </option>
                                                ))}
                                            </select>
                                        </div>
                                        <div className="form-group">
                                            <label>Special Requests</label>
                                            <input
                                                type="text"
                                                value={passenger.specialRequests}
                                                onChange={(e) => handlePassengerChange(index, 'specialRequests', e.target.value)}
                                                placeholder="Dietary needs, assistance required, etc."
                                            />
                                        </div>
                                    </div>
                                </div>
                            ))}
                            <button type="button" className="add-passenger" onClick={addPassenger}>
                                + Add Another Passenger
                            </button>
                        </div>

                        <div className="summary-section">
                            <h3>Booking Summary</h3>
                            <div className="summary-details">
                                <div className="summary-row">
                                    <span className="label">Passengers:</span>
                                    <span className="value">{passengers.length}</span>
                                </div>
                                {passengers.map((passenger, index) => (
                                    <div key={index} className="summary-row passenger-row">
                                        <span className="label">Passenger {index + 1} ({passenger.type})</span>
                                        <span>
                                            ${passenger.type === "ADULT"
                                                ? flight.basePrice.toFixed(2)
                                                : passenger.type === "CHILD"
                                                    ? (flight.basePrice * 0.75).toFixed(2)
                                                    : (flight.basePrice * 0.1).toFixed(2)
                                            }
                                        </span>
                                    </div>
                                ))}
                                <div className="summary-row total">
                                    <span className="label">Total Price</span>
                                    <span className="value">${calculateTotalPrice().toFixed(2)}</span>
                                </div>
                            </div>
                            <button type="submit" className="submit-booking">
                                Submit Booking
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default BookingPage;
