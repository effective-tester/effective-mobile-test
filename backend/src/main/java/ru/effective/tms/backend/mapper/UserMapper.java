package ru.effective.tms.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective.tms.backend.dto.response.UserDto;
import ru.effective.tms.backend.model.User;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    UserDto toDto(User user);
}
