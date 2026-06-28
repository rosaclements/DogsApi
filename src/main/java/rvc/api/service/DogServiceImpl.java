package rvc.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import rvc.entities.*;
import rvc.dto.DogDTO;
import rvc.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**This service contains methods for adding and retrieving dogs from the database via REST.*/
@Service
public class DogServiceImpl implements DogService
{
    private final DogRepository dogRepository;
    private final DogBreedRepository dogBreedRepository;
    private final DogSupplierRepository dogSupplierRepository;

    @Autowired
    public DogServiceImpl(DogRepository dogRepository, DogBreedRepository dogBreedRepository, DogSupplierRepository dogSupplierRepository)
    {
        this.dogRepository = dogRepository;
        this.dogBreedRepository = dogBreedRepository;
        this.dogSupplierRepository = dogSupplierRepository;
    }

    /**Returns a DTO representing a dog given the dog's id.*/
    @Override
    public DogDTO getDogById(int id)
    {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isEmpty()) return null;
        else return convertDogToDTO(optionalDog.get());
    }

    /**Adds a dog to the database using the details in the given dto.*/
    @Override
    public void addDog(DogDTO dto) {
        Dog dog = new Dog();
        dog = updateDogFromDTO(dto);
        if(dog != null) dogRepository.save(dog);
    }

    /**Edits a dog in the database to have the details in the given DTO and saves it.*/
    @Override
    public void saveDog(DogDTO dto) {
        Dog dog = updateDogFromDTO(dto);
        if(dog != null) dogRepository.save(dog);
    }

    /**Marks the dog with the given id as deleted in the database.*/
    @Override
    public void deleteDog(int id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isEmpty()) return;

        Dog dog = optionalDog.get();
        dog.setDeleted(true);
        dogRepository.save(dog);
    }

    /**Returns DTOs detailing all dogs in the database, excluding those marked as deleted.*/
    public List<DogDTO> getAllDogs() {
        List<Dog> dogs = dogRepository.findAll();
        //filter out deleted items
        List<Dog> filteredDogs = dogs.stream()
                .filter(dog -> !dog.isDeleted())
                .collect(Collectors.toList());
        return convertDogsToDTOs(filteredDogs);
    }

    /**Returns DTOs detailing all dogs in the database who meet the given filter criteria (excluding those marked as deleted).*/
    public List<DogDTO> getAllDogsWithFilter(String filterField, String filterValue)
    {
        List<Dog> dogs = dogRepository.findAll();
        Predicate<Dog> filterFunction = switch (filterField) {
            case "name" -> (dog -> dog.getName().contains(filterValue));
            case "breed" -> dog -> dog.getBreed().getName().contains(filterValue);
            case "supplier" -> dog -> dog.getSupplier().getName().contains(filterValue);
            case null, default -> dog -> true;
        };
        //filter out deleted items as well as using the given filter
        List<Dog> filteredDogs = dogs.stream()
                .filter(dog -> !dog.isDeleted())
                .filter(filterFunction)
                .collect(Collectors.toList());
        return convertDogsToDTOs(filteredDogs);
    }

    private DogDTO convertDogToDTO(Dog dog)
    {
        DogDTO dto = new DogDTO();

        dto.setId(dog.getId());
        dto.setName(dog.getName());
        dto.setBreed(dog.getBreed().getName());
        dto.setSupplier(dog.getSupplier().getName());
        dto.setGender(dog.getGender().toString());
        dto.setBirthDate(dog.getBirthDate());
        dto.setDateAcquired(dog.getDateAcquired());
        dto.setCurrentStatus(dog.getCurrentStatus().toString());
        dto.setLeavingDate(dog.getLeavingDate());
        dto.setLeavingReason(dog.getLeavingReason().toString());
        dto.setKennellingCharacteristic(dog.getKennellingCharacteristic());

        return dto;
    }


    private List<DogDTO> convertDogsToDTOs(List<Dog> dogs)
    {
        List<DogDTO> dtos = new ArrayList<>();
        for(Dog dog:dogs)
        {
            dtos.add(convertDogToDTO(dog));
        }
        return dtos;
    }

    private Dog updateDogFromDTO(DogDTO dto)
    {
        Optional<Dog> optionalDog = dogRepository.findById(dto.getId());
        if(optionalDog.isEmpty()) return null;
        Dog dog = optionalDog.get();

        dog.setName(dto.getName());
        DogBreed breed = dogBreedRepository.findByName(dto.getBreed());
        dog.setBreed(breed);
        Supplier supplier = dogSupplierRepository.findByName(dto.getSupplier());
        dog.setSupplier(supplier);
        Gender gender = Gender.valueOf(dto.getGender());
        if(gender != null) dog.setGender(gender);
        dog.setBirthDate(dto.getBirthDate());
        dog.setDateAcquired(dto.getDateAcquired());
        DogStatus status = DogStatus.valueOf(dto.getCurrentStatus());
        if(status != null) dog.setCurrentStatus(status);
        dog.setLeavingDate(dto.getLeavingDate());
        LeavingReason reason = LeavingReason.valueOf(dto.getLeavingReason());
        if(reason != null) dog.setLeavingReason(reason);
        dog.setKennellingCharacteristic(dto.getKennellingCharacteristic());

        return dog;
    }
}
