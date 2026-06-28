package rvc.api.controller;

import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.*;
import static org.mockito.ArgumentMatchers.anyInt;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rvc.api.service.DogService;
import rvc.dto.DogDTO;

/**Unit tests for the DogController class.*/
public class DogControllerTest
{
    @InjectMocks
    private DogController classUnderTest;

    @Mock private DogService dogService;

    private TestRestTemplate restTemplate = new TestRestTemplate();;

    private AutoCloseable closeable;

    @BeforeMethod
    public void initialiseTest()
    {
        dogService = Mockito.mock(DogService.class);
        classUnderTest = new DogController(dogService);
        MockitoAnnotations.openMocks(this);

        populateDogList();
    }

    private void populateDogList()
    {
        DogDTO dog1 = Mockito.mock(DogDTO.class);
        Mockito.when(dog1.getName()).thenReturn("Fido");
        Mockito.when(dog1.getId()).thenReturn(1);
        Mockito.when(dog1.getBreed()).thenReturn("German Shepherd");
        Mockito.when(dog1.getBreed()).thenReturn("Riverside Kennels");

        DogDTO dog2 = Mockito.mock(DogDTO.class);
        Mockito.when(dog2.getName()).thenReturn("Rover");
        Mockito.when(dog2.getId()).thenReturn(2);
        Mockito.when(dog2.getBreed()).thenReturn("German Shepherd");
        Mockito.when(dog2.getBreed()).thenReturn("Oaktree Kennels");

        DogDTO dog3 = Mockito.mock(DogDTO.class);
        Mockito.when(dog3.getName()).thenReturn("Rex");
        Mockito.when(dog3.getId()).thenReturn(3);
        Mockito.when(dog3.getBreed()).thenReturn("Dobermann");
        Mockito.when(dog3.getBreed()).thenReturn("Riverside Kennels");


        List<DogDTO> dogs = List.of(dog1, dog2, dog3);
        Mockito.when(dogService.getAllDogs()).thenReturn(dogs);
        Mockito.when(dogService.getDogById(anyInt())).thenReturn(dog1);
    }

    @Test
    public void testGetDogs()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class);
        Assert.assertTrue(body.contains("Fido"));
        Assert.assertTrue(body.contains("Rover"));
        Assert.assertTrue(body.contains("Rex"));
    }

    @Test
    public void testGetDogById()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class, "id=1");
        Assert.assertTrue(body.contains("Fido"));
        Assert.assertFalse(body.contains("Rover"));
        Assert.assertFalse(body.contains("Rex"));
    }

    @Test
    public void testGetDogsWithFilter()
    {
        String body = this.restTemplate.getForObject("/api/dogs/dogs", String.class, "filter={Supplier:Riverside}");
        Assert.assertTrue(body.contains("Fido"));
        Assert.assertFalse(body.contains("Rover"));
        Assert.assertTrue(body.contains("Rex"));
    }
}