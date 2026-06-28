package rvc.api.service;

import rvc.entities.Dog;
import rvc.dto.DogDTO;

/**This interface defines methods for adding and retrieving dogs from the database using the RESTful API.*/
public interface DogService
{
    /**Returns a DTO representing a dog given the dog's id.*/
    public DogDTO getDogById(int id);
    /**Adds a dog to the database using the details in the given dto.*/
    public void addDog(DogDTO dto);
    /**Edits a dog in the database to have the details in the given DTO and saves it.*/
    public void saveDog(DogDTO dto);
    /**Marks the dog with the given id as deleted in the database.*/
    public void deleteDog(int id);
    /**Returns DTOs detailing all dogs in the database, excluding those marked as deleted.*/
    public List<DogDTO> getAllDogs();
    /**Returns DTOs detailing all dogs in the database who meet the given filter criteria (excluding those marked as deleted).*/
    public List<DogDTO> getAllDogsWithFilter(String filterField, String filterValue);
}
