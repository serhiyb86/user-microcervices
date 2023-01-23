package com.accountant.accesappapiusers.model.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateUserRequestModel {

    @NotNull(message = "Can not be blank")
    private String firstName;
    @NotBlank(message = "Can not be blank")
    private String lastName;
    @NotBlank(message = "Can not be blank")
    @Email(message = "Should be email")
    private String email;
    @Min(value = 3)
    private String password;
}
