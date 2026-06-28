package rvc.repository;

import rvc.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**A repository class to allow use of standard JpaRepository methods to interact with the Dogs table in the database.*/
public interface DogSupplierRepository extends JpaRepository<Supplier, Integer>
{

    Supplier findByName(String name);
}
