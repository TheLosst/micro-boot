package ru.thelosst.pcservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.thelosst.pcservice.models.PC;
import ru.thelosst.pcservice.repositories.PcRepository;
import ru.thelosst.pcservice.services.PcService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServicePcTest {

    @InjectMocks
    private PcService pcService;

    @Mock
    private PcRepository pcRepository;

    @Test
    void testFindAll() {
        // Arrange
        List<PC> expectedPcList = Arrays.asList(new PC(1L,"PC 1", 1000), new PC(2L,"PC 2", 2000));
        when(pcRepository.findAll()).thenReturn(expectedPcList);

        // Act
        List<PC> actualPcList = pcService.findAll();

        // Assert
        assertEquals(expectedPcList, actualPcList);
        verify(pcRepository, times(1)).findAll();
    }

    @Test
    void testFindByTitle() {
        // Arrange
        String title = "PC 1";
        PC pc = new PC(1L,title, 1000);
        Optional<PC> optionalPc = Optional.of(pc);
        when(pcRepository.findByTitle(title)).thenReturn(optionalPc);

        // Act
        String result = pcService.findByTitle(title);

        // Assert
        assertEquals(pc.getTitle() + " Цена: " + pc.getPrice(), result);
        verify(pcRepository, times(1)).findByTitle(title);
    }
}