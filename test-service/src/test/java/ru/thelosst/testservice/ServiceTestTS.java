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
public class ServicePcTest {

    @InjectMocks
    private TestService testService;

    @Mock
    private TestRepository testRepository;

    @org.junit.jupiter.api.Test
    void testFindAll() {
        // Arrange
        List<Test> expectedTestList = Arrays.asList(new Test(1L,"PC 1", 1000), new Test(2L,"PC 2", 2000));
        when(testRepository.findAll()).thenReturn(expectedTestList);

        // Act
        List<Test> actualTestList = testService.findAll();

        // Assert
        assertEquals(expectedTestList, actualTestList);
        verify(testRepository, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    void testFindByTitle() {
        // Arrange
        String title = "PC 1";
        Test test = new Test(1L,title, 1000);
        Optional<Test> optionalPc = Optional.of(test);
        when(testRepository.findByTitle(title)).thenReturn(optionalPc);

        // Act
        String result = testService.findByTitle(title);

        // Assert
        assertEquals(test.getTitle() + " Цена: " + test.getPrice(), result);
        verify(testRepository, times(1)).findByTitle(title);
    }
}