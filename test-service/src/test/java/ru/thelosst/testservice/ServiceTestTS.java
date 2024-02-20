package ru.thelosst.testservice;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.thelosst.testservice.models.Test;
import ru.thelosst.testservice.repositories.TestRepository;
import ru.thelosst.testservice.services.TestService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTestTS {

    @InjectMocks
    private TestService testService;

    @Mock
    private TestRepository testRepository;

    @org.junit.jupiter.api.Test
    void testFindAll() {
        // Arrange
        List<Test> expectedTestList = Arrays.asList(new Test(1L,"test1", 777), new Test(2L,"test2", 666));
        when(testRepository.findAll()).thenReturn(expectedTestList);

        // Act
        List<Test> actualTestList = testService.findAll();

        // Assert
        assertEquals(expectedTestList, actualTestList);
        verify(testRepository, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    void testFindByCharfiled() {
        // Arrange
        String charfiled = "test1";
        Test test = new Test(1L,charfiled, 777);
        Optional<Test> optional = Optional.of(test);
        when(testRepository.findByCharfiled(charfiled)).thenReturn(optional);

        // Act
        String result = testService.findByTitle(charfiled);

        // Assert
        assertEquals(test.getCharfiled() + " intfield: " + test.getIntfield(), result);
        verify(testRepository, times(1)).findByCharfiled(charfiled);
    }
}