package com.RodCarvalhoas.BackeryStock.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    private String name;
    private String password;

}
