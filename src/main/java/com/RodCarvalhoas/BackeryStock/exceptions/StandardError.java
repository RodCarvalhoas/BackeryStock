package com.RodCarvalhoas.BackeryStock.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StandardError {

    private Date timestamp;
    private Integer status;
    private String error;
    private String description;

}
