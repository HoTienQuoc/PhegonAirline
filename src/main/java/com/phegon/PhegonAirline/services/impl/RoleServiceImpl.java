package com.phegon.PhegonAirline.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.dtos.RoleDto;
import com.phegon.PhegonAirline.entities.Role;
import com.phegon.PhegonAirline.exceptions.NotFoundException;
import com.phegon.PhegonAirline.repo.RoleRepo;
import com.phegon.PhegonAirline.services.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper; 

    @Override
    public Response<?> createRole(RoleDto roleDto) {
        log.info("Inside createRole()");
        Role role = modelMapper.map(roleDto, Role.class);
        role.setName(role.getName().toUpperCase());
        roleRepo.save(role);

        return Response
            .builder()
            .statusCode(HttpStatus.OK.value())
            .message("Role created successfully")
            .build();
    }

    @Override
    public Response<?> updateRole(RoleDto roleDto) {
        log.info("Inside updateRole()");
        Long id = roleDto.getId();
        Role existingRole = roleRepo.findById(id)
            .orElseThrow(()-> new NotFoundException("Role not found"));
        existingRole.setName(roleDto.getName().toUpperCase());
        roleRepo.save(existingRole);
        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Role updated Successfully")
            .build();
    }

    @Override
    public Response<List<RoleDto>> getAllRoles() {
        log.info("Inside getAllRoles()");
        List<RoleDto> roles = roleRepo.findAll()
            .stream()
            .map(role -> modelMapper.map(role, RoleDto.class))
            .toList();

        return Response.<List<RoleDto>>builder()
            .statusCode(HttpStatus.OK.value())
            .message(roles.isEmpty() ? "No Roles Found" : "Roles Retreived Successfully")
            .data(roles)
            .build();
    }

    

}
