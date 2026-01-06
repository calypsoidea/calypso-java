package com.calypso.uniswap;

public class BelowThresholdException extends Exception {
    public BelowThresholdException(String message) {
        super(message);
    }
}