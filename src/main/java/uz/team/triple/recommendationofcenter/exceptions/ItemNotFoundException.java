package uz.team.triple.recommendationofcenter.exceptions;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException {
    private String text;
    public ItemNotFoundException(String message) {
        this.text = message;
    }
}
