package com.phegon.PhegonAirline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.dtos.RoleDto;
import com.phegon.PhegonAirline.services.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> createRole(@Valid @RequestBody RoleDto roleDTO){        
        return ResponseEntity.ok(roleService.createRole(roleDTO));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateRole(@Valid @RequestBody RoleDto roleDTO){        
        return ResponseEntity.ok(roleService.updateRole(roleDTO));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority( 'ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }
    
    
}
