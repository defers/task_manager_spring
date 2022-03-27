package com.defers.taskmanager.dto;

import com.defers.taskmanager.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    @Email
    private String email;
    private Set<Role> roles;
    @JsonProperty("isNewPassword")
    private boolean isNewPassword;

}
