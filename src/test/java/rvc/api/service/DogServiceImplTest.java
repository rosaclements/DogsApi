package rvc.api.service;

import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyInt;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rvc.entities.*;
import rvc.dto.DogDTO;
import rvc.repository.*;

/**Unit tests for the DogServiceImpl class.*/
public class DogServiceImplTest
{
    @InjectMocks
    private DogServiceImpl classUnderTest;

    @Mock private DogRepository dogRepository;
    @Mock private DogBreedRepository dogBreedRepository;
    @Mock private DogSupplierRepository dogSupplierRepository;

    private AutoCloseable closeable;

    @BeforeMethod
    public void initialiseTest()
    {
        dogRepository = Mockito.mock(DogRepository.class);
        dogBreedRepository = Mockito.mock(DogBreedRepository.class);
        dogSupplierRepository = Mockito.mock(DogSupplierRepository.class);
        classUnderTest = new DogServiceImpl(dogRepository, dogBreedRepository, dogSupplierRepository);
        MockitoAnnotations.openMocks(this);

        populateDogList();
    }

    private void populateDogList()
    {
        DogBreed gsd = Mockito.mock(DogBreed.class);
        Mockito.when(gsd.getName()).thenReturn("German Shepherd");
        DogBreed dobermann = Mockito.mock(DogBreed.class);
        Mockito.when(gsd.getName()).thenReturn("Dobermann");

        Supplier supplier1 = Mockito.mock(Supplier.class);
        Mockito.when(supplier1.getName()).thenReturn("Riverside Kennels");
        Supplier supplier2 = Mockito.mock(Supplier.class);
        Mockito.when(supplier2.getName()).thenReturn("Oaktree Kennels");

        Dog dog1 = Mockito.mock(Dog.class);
        Mockito.when(dog1.getName()).thenReturn("Fido");
        Mockito.when(dog1.getId()).thenReturn(1);
        Mockito.when(dog1.getBreed()).thenReturn(gsd);
        Mockito.when(dog1.getSupplier()).thenReturn(supplier1);
        Mockito.when(dog1.isDeleted()).thenReturn(false);

        Dog dog2 = Mockito.mock(Dog.class);
        Mockito.when(dog2.getName()).thenReturn("Rover");
        Mockito.when(dog2.getId()).thenReturn(2);
        Mockito.when(dog2.getBreed()).thenReturn(gsd);
        Mockito.when(dog2.getSupplier()).thenReturn(supplier2);
        Mockito.when(dog2.isDeleted()).thenReturn(false);

        Dog dog3 = Mockito.mock(Dog.class);
        Mockito.when(dog3.getName()).thenReturn("Rex");
        Mockito.when(dog3.getId()).thenReturn(3);
        Mockito.when(dog3.getBreed()).thenReturn(dobermann);
        Mockito.when(dog3.getSupplier()).thenReturn(supplier1);
        Mockito.when(dog3.isDeleted()).thenReturn(false);

        Dog dog4 = Mockito.mock(Dog.class);
        Mockito.when(dog4.getName()).thenReturn("Oops");
        Mockito.when(dog4.getId()).thenReturn(4);
        Mockito.when(dog4.getBreed()).thenReturn(gsd);
        Mockito.when(dog4.getSupplier()).thenReturn(supplier1);
        Mockito.when(dog4.isDeleted()).thenReturn(true);

        List<Dog> dogs = List.of(dog1, dog2, dog3, dog4);
        Mockito.when(dogRepository.findAll()).thenReturn(dogs);
        Mockito.when(dogRepository.findById(anyInt())).thenReturn(Optional.of(dog1));
    }

    @AfterMethod
    public void cleanUpTest() throws Exception
    {
        closeable.close();
    }

    @Test
    public void testGetDogById()
    {
        DogDTO dto = classUnderTest.getDogById(1);
        Assert.assertNotNull(dto);
        Assert.assertEquals(dto.getName(),"Fido");
    }

    @Test
    public void testGetAllDogs()
    {
        List<DogDTO> dogs = classUnderTest.getAllDogs();
        Assert.assertEquals(dogs.size(),3);
    }

    @Test
    public void testGetAllDogsWithFilter()
    {
        List<DogDTO> dogs = classUnderTest.getAllDogsWithFilter("breed", "Shepherd");
        Assert.assertEquals(dogs.size(),2);

        dogs = classUnderTest.getAllDogsWithFilter("name", "Fido");
        Assert.assertEquals(dogs.size(),1);

        dogs = classUnderTest.getAllDogsWithFilter("name", "Oops");
        Assert.assertEquals(dogs.size(),0);

        dogs = classUnderTest.getAllDogsWithFilter("supplier", "Riverside");
        Assert.assertEquals(dogs.size(),2);
    }
}