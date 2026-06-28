package rvc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

/**A database entity representing dog breeds.*/
@Data
@AllArgsConstructor
@Entity
public class DogBreed
{
    private String name;
}
