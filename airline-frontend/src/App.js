import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import Footer from "./components/common/Footer";
import RegisterPage from "./components/auth/RegisterPage";
import LoginPage from "./components/auth/LoginPage";
import HomePage from "./components/pages/HomePage";
import FindFlightsPage from "./components/pages/FindFlightsPage";
import ProfilePage from "./components/profiles/ProfilePage";
import UpdateProfilePage from "./components/profiles/UpdateProfilePage";
import BookingPage from "./components/pages/BookingPage";
import BookingDetailsPage from "./components/pages/BookingDetailsPage";
import  RouteGuard  from "./services/RouteGuard";
import AdminDashboardPage from "./components/admin/AdminDashboardPage";
import AdminBookingDetailsPage from "./components/admin/AdminBookingDetailsPage";
import AdminFlightDetailsPage from "./components/admin/AdminFlightDetailsPage";
import AddEditAirportPage from "./components/admin/AddEditAirportPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="content">
        <Routes>
          {/* AUTH PAGES */}
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />

          {/* PUBLIC PAGES */}
          <Route path="/home" element={<HomePage />} />
          <Route path="/flights" element={<FindFlightsPage />} />

          {/* CUSTOMER PAGES */}
          <Route
            path="/profile"
            element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<ProfilePage />} />}
          />
          <Route
            path="/update-profile"
            element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<UpdateProfilePage />} />}
          />
          <Route
            path="/booking-flight/:id"
            element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingPage />} />}
          />
          <Route
            path="/booking/:id"
            element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingDetailsPage />} />}
          />

          {/* ADMIN && PILOT PAGES */}
          <Route
            path="/admin"
            element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AdminDashboardPage />} />}
          />
          <Route
            path="/admin/booking/:id"
            element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AdminBookingDetailsPage />} />}
          />
          <Route
            path="/admin/flight/:id"
            element={<RouteGuard allowedRoles={["PILOT"]} element={<AdminFlightDetailsPage />} />}
          />
          <Route
            path="/add-airport"
            element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AddEditAirportPage/>}/>}
          />
          <Route
            path="/edit-airport/:id"
            element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AddEditAirportPage/>}/>}
          />

          {/* Fallback for unmatched routes */}
          <Route path="*" element={<Navigate to="/home" />} />
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
