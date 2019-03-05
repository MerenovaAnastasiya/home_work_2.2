package com.merenaas.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int calc(String a, String b, String operation) {
        int result;
        Integer aInt = Integer.parseInt(a);
        Integer bInt = Integer.parseInt(b);
        switch (operation) {
            case ("+"):
                result = aInt + bInt;
                break;
            case ("-"):
                result = aInt - bInt;
                break;
            case ("*"):
                result = aInt * bInt;
                break;
            case ("/"):
                result = aInt / bInt;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return result;
    }

}
