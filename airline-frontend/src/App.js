import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import Footer from "./components/common/Footer";
import RegisterPage from "./components/auth/RegisterPage";
import LoginPage from "./components/auth/LoginPage";
import HomePage from "./components/pages/HomePage";
import FindFlightsPage from "./components/pages/FindFlightsPage";
import ProfilePage from "./components/profiles/ProfilePage";
import UpdateProfilePage from "./components/profiles/UpdateProfilePage";



function App() {


  return (
    <BrowserRouter>
      <Navbar/>
      <div className="content">
        <Routes>
          {/* AUTH PAGES */}
          <Route path="/register" element={<RegisterPage/>}/>
          <Route path="/login" element={<LoginPage/>}/>
          <Route path="/home" element={<HomePage/>}/>
          <Route path="/flights" element={<FindFlightsPage/>}/>
          <Route path="/profile" element={<ProfilePage/>}/>
          <Route path="/update-profile" element={<UpdateProfilePage />} />
        </Routes>
      </div>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;
