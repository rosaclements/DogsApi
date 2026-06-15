package rvc.api.service;

import rvc.entities.Dog;
import rvc.dto.DogDTO;
import rvc.repository.DogRepository;

import java.util.Optional;

@Service
public class DogServiceImpl implements DogService
{
    private final dogRepository dogRepository;

    @Autowired
    public DogServiceImpl(dogRepository DogRepository) {
        this.dogRepository = DogRepository;
    }

    @Override
    public DogDTO getDogById(int id) {
        Dog optionalDog = dogRepository.findById(id);
        if(dog == null) reurn null;
        else return convertDogToDTO(dog);
    }

    @Override
    public void addDog(DogDTO dto) {
        Dog dog = dogRepository.create(Dog);
        saveDog(dto);
    }

    @Override
    public void saveDog(DogDTO dto) {
        dog = updateDogFromDTO(dto);
        if(dog != null) dogRepository.save(Dog);
    }

    @Override
    public void deleteDog(int id) {
        Dog dog = getDogById(id);
        if(dog != null)
        {
            dog.setDeleted(true);
            saveDog();
        }
    }

    public List<DogDTO> getAllDogs() {
        List<Dog> dogs = dogRepository.findAll();
        //filter out deleted items
        List filteredDogs = dogs.stream().filter(dog -> !dog.isDeleted());
        return convertDogsToDTOs(filteredDogs);
    }

    public List<Dog> getAllDogsWithFilter(String filterField, String filterValue)
    {
        List<Dog> dogs = dogRepository.findAll();
        Function<Dog, boolean> filterFunction;
        if("name".equals(filterField))
            filterFunction = dog -> dog.getName().contains(filterValue);
        else if("breed".equals(filterField))
            filterFunction = dog -> dog.getBreed().getName().contains(filterValue);
        else if("supplier".equals(filterField))
            filterFunction = dog.getSupplier..getName().contains(filterValue);
        else filterFunction = dog -> true;
        //filter out deleted items as well as using the given filter
        List filteredDogs = dogs.stream()
                .filter(dog -> !dog.isDeleted())
                .filter(filterFunction);
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
        dto.setCurrentStatus(dog.getCurrentStatus.toString());
        dto.setLeavingDate(dog.getLeavingDate());
        dto.setLeavingReason(dog.getLeavingReason().toString());
        dto.setKennelingCharacteristic(dog.getKenellingCharacteristic());

        return dto;
    }


    private DogDTO convertDogsToDTO(List<Dog> dogs)
    {
        List<DogDTO> dtos = new ArrayList<>();
        for(Dog dog:dogs)
        {
            dtos.add(convertDogToDTO(dog))
        }
        return dtos;
    }

    private Dog updateDogFromDTO(DogDTO dto)
    {
        Dog dog = getDogById(dto.getId());
        if(dog == null) return null;

        dog.setName(dto.getName());
        Breed = dogRepository.findBreedByName(dto.getBreed().getName()));
        dog.setBreed(breed);
        Supplier = dogRepository.findSupplierByName(dto.getSupplier().getName()));
        dog.setSupplier(supplier);
        Gender = Gender.getByName(dto.getGender());
        if(gender != null) dog.setGender(gender);
        dog.setBirthDate(dto.getBirthDate());
        dog.setDateAcquired(dto.getDateAcquired());
        DogStatus status = DogStatus.getByName(dto.getCurrentStatus());
        if(status != null) dog.setCurrentStatus(status);
        dog.setLeavingDate(dto.getLeavingDate());
        LeavingReason reason = LeavingReason.getByName(dto.getLeavingReason());
        if(reason != null) dog.setLeavingReason(reason);
        dog.setKennelingCharacteristic(dto.getKenellingCharacteristic());

        return dog;
    }
}
