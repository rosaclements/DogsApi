package rvc.repository;

import rvc.entities.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;

/**A repository class to allow use of standard JpaRepository methods to interact with the Dogs table in the database.*/
public interface DogBreedRepository extends JpaRepository<DogBreed, Integer>
{

    DogBreed findByName(String name);
}
