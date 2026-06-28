package rvc.api.controller;

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

/**Unit tests for the DogController class.*/
public class DogControllerTest
{
    @InjectMocks
    private DogController classUnderTest;

    @Mock private final dogService dogService;

    private AutoCloseable closeable;

    @BeforeMethod
    public void initialiseTest()
    {
        classUnderTest = new DogController();
        MockitoAnnotations.openMocks(this);

        populateDogList();
    }

    private void populateDogList()
    {
        DogDTO dog1 = mock(Dogs.class);
        when(dog1.getName()).thenReturn("Fido");
        when(dog1.getId()).thenReturn(1);
        when(dog1.getBreed()).thenReturn("German Shepherd");
        when(dog1.getBreed()).thenReturn("Riverside Kennels");

        DogDTO dog2 = mock(Dogs.class);
        when(dog2.getName()).thenReturn("Rover");
        when(dog2.getId()).thenReturn(2);
        when(dog2.getBreed()).thenReturn("German Shepherd");
        when(dog2.getBreed()).thenReturn("Oaktree Kennels");

        DogDTO dog3 = mock(Dogs.class);
        when(dog3.getName()).thenReturn("Rex");
        when(dog3.getId()).thenReturn(3);
        when(dog3.getBreed()).thenReturn("Dobermann");
        when(dog3.getBreed()).thenReturn("Riverside Kennels"));


        List<DogDTO> = List.of(dog1, dog2, dog3;
        when(dogService.getAllDogs()).thenReturn(dogs);
        when(dogService.getDogById(anyInt())).thenReturn(dog1);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetDogs()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class);
        assertTrue(body.contains("Fido"));
        assertTrue(body.contains("Rover"));
        assertTrue(body.contains("Rex"));
    }

    @Test
    public void testGetDogById()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class, "id=1");
        assertTrue(body.contains("Fido"));
        assertFalse(body.contains("Rover"));
        assertFalse(body.contains("Rex"));
    }

    @Test
    public void testGetDogsWithFilter()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class, "filter={Supplier=Riverside}");
        assertTrue(body.contains("Fido"));
        assertFalse(body.contains("Rover"));
        assertTrue(body.contains("Rex"));
    }
}