package rvc.api.service;

import java.util.list;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.anyInt;
import org.testng.Assert;
import.org.testng.annotations.AfterMethod;
import.org.testng.annotations.BeforeMethod;
import.org.testng.annotations.Test;
import rvc.entities.Dog;
import rvc.dto.DogDTO;

/**Unit tests for the DogServiceImpl class.*/
public class DogServiceImplTest
{
    @InjectMocks
    private DogServiceImpl classUnderTest;

    @Mock private final dogRepository dogRepository;

    private AutoCloseable closeable;

    @BeforeMethod
    public void initialiseTest()
    {
        classUnderTest = new DogServiceImpl();
        MockitoAnnotations.openMocks(this);

        populateDogList();
    }

    private void populateDogList()
    {
        Breed gsd = mock(DogBreed.class);
        when(gsd.getName()).thenReturn("German Shepherd");
        Breed dobermann = mock(DogBreed.class);
        when(gsd.getName()).thenReturn("Dobermann");

        Supplier supplier1 = mock(Supplier.class);
        when(supplier1.getName()).thenReturn("Riverside Kennels");
        Supplier supplier2 = mock(Supplier.class);
        when(supplier2.getName()).thenReturn("Oaktree Kennels");

        Dog dog1 = mock(Dogs.class);
        when(dog1.getName()).thenReturn("Fido");
        when(dog1.getId()).thenReturn(1);
        when(dog1.getBreed()).thenReturn(gsd);
        when(dog1.getBreed()).thenReturn(supplier1);
        when(dog1.isDeleted()).thenReturn(false);

        Dog dog2 = mock(Dogs.class);
        when(dog2.getName()).thenReturn("Rover");
        when(dog2.getId()).thenReturn(2);
        when(dog2.getBreed()).thenReturn(gsd);
        when(dog2.getBreed()).thenReturn(supplier2);
        when(dog2.isDeleted()).thenReturn(false);

        Dog dog3 = mock(Dogs.class);
        when(dog3.getName()).thenReturn("Rex");
        when(dog3.getId()).thenReturn(3);
        when(dog3.getBreed()).thenReturn(dobermann);
        when(dog3.getBreed()).thenReturn(supplier1);
        when(dog3.isDeleted()).thenReturn(false);

        Dog dog4 = mock(Dogs.class);
        when(dog4.getName()).thenReturn("Oops");
        when(dog4.getId()).thenReturn(4);
        when(dog4.getBreed()).thenReturn(gsd);
        when(dog4.getBreed()).thenReturn(supplier1);
        when(dog4.isDeleted()).thenReturn(true);

        List<Dog> = List.of(dog1, dog2, dog3, dog4);
        when(dogRepository.findAll()).thenReturn(dogs);
        when(dogRepository.findById(anyInt())).thenReturn(dog1);
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
        assertNotNull(dto);
        assertEquals(dto.getName(),"Fido");
    }

    @Test
    public void testGetAllDogs()
    {
        List<DogDTO> dogs = classUnderTest.getAllDogs();
        assertEquals(dogs.size(),3);
    }

    @Test
    public void testGetAllDogsWithFilter()
    {
        List<DogDTO> dogs = classUnderTest.getAllDogs("breed", "Shepherd");
        assertEquals(dogs.size(),2);

        List<DogDTO> dogs = classUnderTest.getAllDogs("name", "Fido");
        assertEquals(dogs.size(),1);

        List<DogDTO> dogs = classUnderTest.getAllDogs("name", "Oops");
        assertEquals(dogs.size(),0;

        List<DogDTO> dogs = classUnderTest.getAllDogs("supplier", "Riverside");
        assertEquals(dogs.size(),2);
    }
}