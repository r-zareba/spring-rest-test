package com.example.restfulwebservices.hello;

public class Hello {

    public Hello(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Hello [message=%s]", message);
    }

    private String message;
}
