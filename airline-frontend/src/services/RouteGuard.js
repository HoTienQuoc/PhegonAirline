import { useLocation, Navigate } from 'react-router-dom';
import ApiService from './ApiService';

const RouteGuard = ({ element: Component, allowedRoles }) => {
    const location = useLocation();

    let hasRequiredRole = false;

    if (!allowedRoles || allowedRoles.length === 0) {
        hasRequiredRole = true;
    } else {
        hasRequiredRole = allowedRoles.some(role => {
            if (role === 'ADMIN') return ApiService.isAdmin();
            if (role === 'PILOT') return ApiService.isPilot();
            if (role === 'CUSTOMER') return ApiService.isCustomer();
            return false;
        });
    }

    if (hasRequiredRole) {
        return Component;
    } else {
        return <Navigate to="/login" replace state={{ from: location }} />;
    }
};

export default RouteGuard;
