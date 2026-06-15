package rvc.api.service;

import rvc.entities.Dog;
import rvc.dto.DogDTO;

public interface DogService
{
    public DogDTO getDogById(int id);
    public void addDog(DogDTO dto);
    public void saveDog(DogDTO dto);
    public void deleteDog(int id);
    public List<DogDTO> getAllDogs();
    public List<DogDTO> getAllDogsWithFilter(String filterField, String filterValue);
}
