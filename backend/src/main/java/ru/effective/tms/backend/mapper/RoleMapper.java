package ru.effective.tms.backend.mapper;

import org.mapstruct.Mapper;
import ru.effective.tms.backend.dto.response.RoleDto;
import ru.effective.tms.backend.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
