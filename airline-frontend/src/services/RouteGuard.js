import { useLocation, Navigate } from 'react-router'
import ApiService from './ApiService';
import { all } from 'axios';

export default RouteGuard = ({element: Component, allowedRoles})=>{
    const location = useLocation();
    let hasRequiredRole = false;
    if(!allowedRoles||allowedRoles.length === 0){
        hasRequiredRole = true;
    }
    else{
        hasRequiredRole = allowedRoles.some(role=>{
            if(role === 'ADMIN'){
                return ApiService.isAdmin();
            }
            else if(role === 'PILOT'){
                return ApiService.isPilot();
            }
            else if(role ==='CUSTOMER'){
                return ApiService.isCustomer();
            }
            return false;
        })
    }

    if(hasRequiredRole){
        return Component;
    }
    else{
        return <Navigate to="/login" replace state={{from: location}}/>
    }
}