package com.accountant.accesappapiusers.model.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateUserResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
