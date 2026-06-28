package rvc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rvc.api.service.DogService;
import rvc.dto.DogDTO;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**A front end controller class to handle http requests for the Dogs Api.*/
@RestController
@RequestMapping("/api/dogs")
public class DogController
{
    private final DogService dogService;
    private final String filterRegex = "(name|breed|supplier):(.*)";

    @Autowired
    public DogController(DogService dogService)
    {
        this.dogService = dogService;
    }

    @GetMapping("/{id}")
    public DogDTO getDogById(@PathVariable int id) {
        return dogService.getDogById(id);
    }

    @GetMapping
    public List<DogDTO> getAllDogs() {
        return dogService.getAllDogs();
    }

    /**Takes a filter of the form X:Y where X X is name, breed, or supplier and Y is text of the user's choice*/
    @GetMapping("/{filter}")
    public List<DogDTO> getAllDogs(@PathVariable String filter) throws Exception {
        if(Pattern.matches(filterRegex, filter))
        {
            Pattern pattern = Pattern.compile(filterRegex);
            Matcher matcher = pattern.matcher(filter);
            return dogService.getAllDogsWithFilter(matcher.group(1), matcher.group(2));
        }
        else throw new Exception("Filter must start with X: where X is name, breed, or supplier");
    }

    @PostMapping
    public void addDog(@RequestBody DogDTO dto) {
        dogService.addDog(dto);
    }

    @PutMapping("/{id}")
    public void updateDog(@PathVariable int id, @RequestBody DogDTO dto) throws Exception {
        DogDTO existingDog = dogService.getDogById(id);
        if (existingDog != null)
            dogService.saveDog(dto);
        else throw new Exception("Cannot find dog with id "+id+" to update");
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable int id) {
        dogService.deleteDog(id);
    }
}
