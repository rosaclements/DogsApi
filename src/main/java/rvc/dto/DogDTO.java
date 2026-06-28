package rvc.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**A DTO representing a police dog for use in the RESTful api.*/
@Data
@EqualsAndHashCode(callSuper=false)
public class DogDTO
{
    private int id;

    private String name;
    private String breed;
    private String supplier;
    private int badgeId;
    private String gender;
    private Date birthDate;
    private Date dateAcquired;
    private String currentStatus;
    private Date leavingDate;
    private String leavingReason;
    private String kennelingCharacteristic;

    private boolean deleted;
}
