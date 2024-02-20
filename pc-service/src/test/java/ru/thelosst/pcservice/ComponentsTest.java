package ru.thelosst.pcservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.thelosst.pcservice.controllers.Controller;
import ru.thelosst.pcservice.models.PC;
import ru.thelosst.pcservice.services.PcService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(Controller.class)
@AutoConfigureMockMvc
public class ComponentsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PcService pcService;

    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO from CVYLEV-SERVICE"));
    }

    @Test
    void testShowAllEndpoint() throws Exception {
        // Arrange
        when(pcService.findAll()).thenReturn(Arrays.asList(new PC(1L,"test1", 777), new PC(2L, "test2", 666)));

        // Act & Assert1
        mockMvc.perform(MockMvcRequestBuilders.get("/showAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("test1"))
                .andExpect(jsonPath("$[0].price").value(777))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("test2"))
                .andExpect(jsonPath("$[1].price").value(666));

        // Verify that Service.findAll() was called
        verify(pcService, times(1)).findAll();
    }

    @Test
    void testGetPriceEndpoint() throws Exception {
        // Arrange
        String title = "test1";
        when(pcService.findByTitle(title)).thenReturn("test1 Цена: 777");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/get_price").param("title", title))
                .andExpect(status().isOk())
                .andExpect(content().string("test1 Цена: 777"));

        // Verify that pcService.findByTitle() was called with the correct parameter
        verify(pcService, times(1)).findByTitle(title);
    }
}