package rvc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rvc.api.service.DogService.DogService;
import rvc.entities.Dog;
import rvc.entities.DogDTO;

import java.util.List;

/**A front end controller class to handle http requests for the Dogs Api.*/
@RestController
@RequestMapping("/api/dogs")
public class DogController
{
    private DogService dogService;
    private filterRegex = "(name|breed|supplier)=(.*)";

    @Autowired
    public DogController(DogService dogService)
    {
        this.dogService = dogService;
    }

    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable int id) {
        return DogService.getDogById(id);
    }

    @GetMapping
    public List<DogDTO> getAllDogs() {
        return DogService.getAllDogs();
    }

    @GetMapping("/{filter}")
    public List<DogDTO> getAllDogs(@PathVariable String filterString)
    {
        if(Pattern.matches(filterRegex, filterString))
        {
            Pattern pattern = Pattern.compile(filterRegex);
            Matcher matcher = pattern.matcher(filterString);
            return DogService.getAllDogsWithFilter(matcher.group(1), matcher.group(2));
        }
        else throw new Exception("Filter must start with X= where X is name, breed, or supplier");
    }

    @PostMapping
    public void addDog(@RequestBody DogDTO dto) {
        DogService.addDog(dto);
    }

    @PutMapping("/{id}")
    public void updateDog(@PathVariable int id, @RequestBody DogDTO dto) {
        DogDTO existingDog = DogService.getDogById(id);
        if (existingDog != null)
            DogService.saveDog(dto);
        else throw new Exception("Cannot find dog with id "+id+" to update");
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable int id) {
        DogService.deleteDog(id);
    }
}
