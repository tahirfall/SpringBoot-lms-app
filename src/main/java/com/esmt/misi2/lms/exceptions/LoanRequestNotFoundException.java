package com.esmt.misi2.lms.exceptions;

public class LoanRequestNotFoundException extends RuntimeException {

    public LoanRequestNotFoundException(String message) {
        super(message);
    }
}
