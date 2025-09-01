import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom"; 
import ApiService from "../../services/ApiService";
import { useMessage } from "../common/MessageDisplay";

const UpdateProfilePage = () => {
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useMessage();
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const [user, setUser] = useState({
        name: "",
        phoneNumber: "",
        password: "",
        confirmPassword: ""
    });

    const [errors, setErrors] = useState({}); // ✅ Added errors state

    useEffect(() => {
        fetchUserProfile();
    }, []);

    const fetchUserProfile = async () => {
        try {
            const response = await ApiService.getAccountDetails();
            setUser(prev => ({
                ...prev,
                name: response.data.name,
                phoneNumber: response.data.phoneNumber || ""
            }));
        } catch (error) {
            showError(error.response?.data?.message || "Failed to fetch profile");
        } finally {
            setLoading(false);
        }
    };

    const validateForm = () => {
        const newErrors = {};

        if (!user.name.trim()) {
            newErrors.name = "Name is required";
        }

        if (user.password && user.password.length < 4) {
            newErrors.password = "Password must be at least 4 characters";
        }

        if (user.password && user.password !== user.confirmPassword) {
            newErrors.confirmPassword = "Passwords do not match";
        }

        setErrors(newErrors);

        return Object.keys(newErrors).length === 0;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        try {
            const requestBody = {
                name: user.name,
                phoneNumber: user.phoneNumber || null,
                password: user.password || undefined,
            };

            const isToUpdate = window.confirm("Are you sure you want to update your account?");
            if (!isToUpdate) return;

            const resp = await ApiService.updateMyAccount(requestBody);
            if (resp.statusCode === 200) {
                showSuccess("Account updated successfully!");
                navigate("/profile");
            }

        } catch (error) {
            showError(error.response?.data?.message || "Failed to update profile");
        }
    };

    if (loading) return <div className="update-profile-loading">Loading Profile...</div>;

    return (
        <div className="update-profile-container">
            <div className="update-profile-card">
                <ErrorDisplay />
                <SuccessDisplay />
                <h2 className="update-profile-title">Update Profile</h2>
                <form onSubmit={handleSubmit} className="update-profile-form">
                    <div className="update-profile-form-group">
                        <label htmlFor="name" className="update-profile-label">Full Name</label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            value={user.name}
                            onChange={handleChange}
                            className={`update-profile-input ${errors.name ? 'error' : ""}`}
                        />
                        {errors.name && <span className="update-profile-error">{errors.name}</span>}
                    </div>

                    <div className="update-profile-form-group">
                        <label htmlFor="phoneNumber" className="update-profile-label">Phone Number</label>
                        <input
                            type="tel"
                            id="phoneNumber"
                            name="phoneNumber"
                            value={user.phoneNumber}
                            onChange={handleChange}
                            className="update-profile-input"
                            placeholder="Optional"
                        />
                    </div>

                    <div className="update-profile-form-group">
                        <label htmlFor="password" className="update-profile-label">New Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={user.password}
                            onChange={handleChange}
                            className={`update-profile-input ${errors.password ? 'error' : ''}`}
                            placeholder="Leave blank to keep current password"
                        />
                        {errors.password && <span className="update-profile-error">{errors.password}</span>}
                    </div>

                    <div className="update-profile-form-group">
                        <label htmlFor="confirmPassword" className="update-profile-label">Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={user.confirmPassword}
                            onChange={handleChange}
                            className={`update-profile-input ${errors.confirmPassword ? 'error' : ''}`}
                            placeholder="Confirm new password"
                        />
                        {errors.confirmPassword && <span className="update-profile-error">{errors.confirmPassword}</span>}
                    </div>

                    <div className="update-profile-actions">
                        <button type="submit" className="update-profile-submit">Update Profile</button>
                        <Link to="/profile" className="update-profile-cancel">Cancel</Link>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default UpdateProfilePage;
