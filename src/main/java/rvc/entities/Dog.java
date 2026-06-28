package rvc.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

/**A database entity representing a police dog.*/
@Entity
@Data
@AllArgsConstructor
public class Dog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private DogBreed breed;
    private Supplier supplier;
    private int badgeId;
    private Gender gender;
    private Date birthDate;
    private Date dateAcquired;
    private DogStatus currentStatus;
    private Date leavingDate;
    private LeavingReason leavingReason;
    private String kennellingCharacteristic;
}
