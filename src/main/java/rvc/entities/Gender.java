package rvc.entities;

import lombok.Getter;

/**An enum representing gender. Currently only used for dogs, but more values can be added if this gets used for human gender in future.*/
public enum Gender
{
    MALE("Male"),
    FEMALE("Female");

    @Getter
    private final String name;

    Gender(String name)
    {
        this.name = name;
    }
}
