package rvc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

/**A database entity representing suppliers of dogs who join the police force.*/
@Entity
@Data
@AllArgsConstructor
public class Supplier
{
    private String name;
}
