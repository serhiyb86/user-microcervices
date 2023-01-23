package com.accountant.accesappapiusers.model.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AlbumResponseModel {
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
