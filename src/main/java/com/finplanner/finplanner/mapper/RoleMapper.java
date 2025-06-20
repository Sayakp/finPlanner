package com.finplanner.finplanner.mapper;

import com.finplanner.finplanner.dto.role.RoleDto;
import com.finplanner.finplanner.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);
}
