package com.accountant.accesappapiusers.model.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UpdateUserRequestModel extends CreateUserRequestModel {
    private String userId;

}
