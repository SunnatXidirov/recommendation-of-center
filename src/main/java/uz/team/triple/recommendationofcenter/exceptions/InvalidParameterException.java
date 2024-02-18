package uz.team.triple.recommendationofcenter.exceptions;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {
    private String text;
    public InvalidParameterException(String message) {
        this.text = message;
    }
}
