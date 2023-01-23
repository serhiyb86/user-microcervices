package com.accountant.accesappapiusers.model.dto;

import com.accountant.accesappapiusers.model.responses.AlbumResponseModel;
import com.accountant.accesappapiusers.model.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable {

    private String userId;
    @NotNull(message = "Can not be blank")
    private String firstName;
    @NotBlank(message = "Can not be blank")
    private String lastName;
    @NotBlank(message = "Can not be blank")
    @Email(message = "Should be email")
    private String email;
    @Size(min = 3)
    private String password;
    @Size(min = 3)
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role == null ? List.of(new SimpleGrantedAuthority(Role.USER.name())) : List.of(new SimpleGrantedAuthority(role.name()));
    }
}
