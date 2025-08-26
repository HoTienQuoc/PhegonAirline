package com.phegon.PhegonAirline.services;

import java.util.List;

import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.dtos.RoleDto;

public interface RoleService {
    Response<?> createRole(RoleDto roleDto);
    Response<?> updateRole(RoleDto roleDto);
    Response<List<RoleDto>> getAllRoles();


}
