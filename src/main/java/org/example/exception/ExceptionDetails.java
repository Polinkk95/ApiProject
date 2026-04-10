package org.example.exception;

import lombok.Data;

import java.util.Date;
@Data
public final class ExceptionDetails {
    private Date date;
    private String message;
    private String details;

    public ExceptionDetails(Date date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }

}
