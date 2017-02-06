/*
 * Copyright (c) 2017. Created by bfleyshe for the purpose of CMPUT 301. May not be redistributed or used without permission.
 */

package com.example.bfleyshe.bfleyshe_sizebook;

/**
 * Created by bfleyshe on 2/1/17.
 */

public class NameTooLongException extends Exception {

    public NameTooLongException() {
    }

    public NameTooLongException(String detailMessage) {
        super(detailMessage);
    }
}
