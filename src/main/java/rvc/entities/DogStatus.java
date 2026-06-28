package rvc.entities;

import lombok.Getter;

/**An enum representing possible statuses for police dogs.*/
public enum DogStatus
{
    IN_TRAINING("In training"),
    IN_SERVICE("In service"),
    RETIRED("Retired"),
    LEFT("Left");

    @Getter
    private final String name;

    DogStatus(String name)
    {
        this.name = name;
    }
}
