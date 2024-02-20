package ru.thelosst.testservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.thelosst.testservice.controllers.Controller;
import ru.thelosst.testservice.models.Test;
import ru.thelosst.testservice.services.TestService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(Controller.class)
@AutoConfigureMockMvc
public class ComponentsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @org.junit.jupiter.api.Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO from CVYLEV-SERVICE"));
    }

    @org.junit.jupiter.api.Test
    void testShowAllEndpoint() throws Exception {
        // Arrange
        when(testService.findAll()).thenReturn(Arrays.asList(new Test(1L,"test1", 777), new Test(2L, "test2", 666)));

        // Act & Assert1
        mockMvc.perform(MockMvcRequestBuilders.get("/showAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].charfiled").value("test1"))
                .andExpect(jsonPath("$[0].intfield").value(777))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].charfiled").value("test2"))
                .andExpect(jsonPath("$[1].intfield").value(666));

        // Verify that Service.findAll() was called
        verify(testService, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    void testGetPriceEndpoint() throws Exception {
        // Arrange
        String charfiled = "test1";
        when(testService.findByTitle(charfiled)).thenReturn("test1 Intfield: 777");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/get_int").param("charfiled", charfiled))
                .andExpect(status().isOk())
                .andExpect(content().string("test1 Intfield: 777"));

        // Verify that testService.findByTitle() was called with the correct parameter
        verify(testService, times(1)).findByTitle(charfiled);
    }
}