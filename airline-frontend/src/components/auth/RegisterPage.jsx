import { useNavigate, Link } from "react-router-dom"; 
import { useMessage } from "../common/MessageDisplay";
import { useState } from "react";
import ApiService from "../../services/ApiService";

const RegisterPage = () => {

    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useMessage();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        phoneNumber: "",
        confirmPassword: ""
    });

    // Handle input change
    const handleChange = (e) => {
        console.log(`Changing field ${e.target.name} to ${e.target.value}`);  // Debugging log
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Debugging: Check current form data before submission
        console.log("Form Data:", formData);

        // Validation checks
        if (!formData.name || !formData.email || !formData.password || !formData.phoneNumber || !formData.confirmPassword) {
            console.log("One or more fields are empty");  // Debugging log
            showError("All fields are required");
            return;
        }

        if (formData.password !== formData.confirmPassword) {
            console.log("Passwords do not match");  // Debugging log
            showError("Passwords do not match");
            return;
        }

        const registrationData = {
            name: formData.name,
            email: formData.email,
            password: formData.password,
            phoneNumber: formData.phoneNumber,
            confirmPassword: formData.confirmPassword,
        };

        try {
            const response = await ApiService.registerUser(registrationData);
            if (response.statusCode === 200) {
                navigate("/login");
            } else {
                showError(response.message);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-card">
                <ErrorDisplay />
                <SuccessDisplay />
                <div className="auth-header">
                    <h2>Create Your Account</h2>
                    <p>Join Phegon Airlines for seamless travel experiences</p>
                </div>
                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Full Name</label>
                        <input
                            type="text"
                            name="name"
                            id="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                            placeholder="Enter your name ..."
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email Address</label>
                        <input
                            type="email"
                            name="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="Enter your email ..."
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password ..."
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password</label>
                        <input
                            type="password"
                            name="confirmPassword"
                            id="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password again ..."
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="phoneNumber">Phone Number</label>
                        <input
                            type="text"
                            name="phoneNumber"
                            id="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            required
                            placeholder="Enter your phone number ..."
                        />
                    </div>
                    <button type="submit" className="auth-button">
                        Create Account
                    </button>
                    <div className="auth-footer">
                        <p>Already have an account? <Link to="/login"> Sign in here </Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;
