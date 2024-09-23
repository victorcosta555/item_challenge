package com.example.app.controllers;

import com.example.app.entities.ItemEntity;
import com.example.app.exceptions.ItemNotFoundException;
import com.example.app.services.ItemService;
import com.example.app.testFixtures.ItemFixture;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() throws ServletException, IOException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser
    void getItemById_shouldReturnItem_whenItExists() throws Exception {

        when(itemService.getItemById(UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696")))
                .thenReturn(ItemFixture.oneItem());

        mockMvc.perform(get("/api/v1/item/4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("4c22732f-f4f7-436d-9620-c8bcf9fdc696"));
    }

    @Test
    @WithMockUser
    void getItemById_shouldReturn404_whenItemNotFound() throws Exception {

        when(itemService.getItemById(UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696")))
                .thenThrow(ItemNotFoundException.class);

        mockMvc.perform(get("/api/v1/item/4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void saveItem_shouldReturnItem_whenItemHasValidBody() throws Exception {
        var body = """
                {
                    "name": "Smartphone xyz",
                    "description": "Smartphone",
                    "price": 12.33,
                    "quantity": 2,
                    "category": "Electronic"
                }
                """;

        when(itemService.saveOneItem(ItemFixture.oneItemValidToSave()))
                .thenReturn(ItemFixture.oneItem());

        mockMvc.perform(post("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("4c22732f-f4f7-436d-9620-c8bcf9fdc696"));
    }

    @Test
    @WithMockUser
    void saveItem_shouldReturnError_whenItemHasInvalidBody() throws Exception {
        var body = """
                {
                    "name": "Smartphone xyz",
                    "description": "Smartphone"
                }
                """;

        when(itemService.saveOneItem(ItemFixture.oneItemValidToSave()))
                .thenReturn(ItemFixture.oneItem());

        mockMvc.perform(post("/api/v1/item")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void updateItem_shouldReturnUpdatedItem_whenUpdate() throws Exception {
        var body = """
                {
                    "name": "test",
                    "description": "test",
                    "price": 12.00,
                    "quantity": 1
                }
                """;

        var uuid = UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696");

        var itemUpdated = ItemEntity.builder()
                .id(UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .name("test")
                .price(BigDecimal.valueOf(12.00))
                .quantity(1)
                .description("test")
                .build();

        when(itemService.updateItem(eq(uuid), any()))
                .thenReturn(itemUpdated);

        mockMvc.perform(put("/api/v1/item/4c22732f-f4f7-436d-9620-c8bcf9fdc696")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    @WithMockUser
    void saveAllItems_shouldReturnListOfItems_whenItemHasValidBody() throws Exception {
        var body = """
                [
                    {
                        "name": "test",
                        "description": "test",
                        "price": 10.91,
                        "quantity": 2,
                        "category": "test"
                    },
                    {
                        "name": "test",
                        "description": "test",
                        "price": 10.91,
                        "quantity": 2,
                        "category": "test"
                    }
                ]
                """;

        when(itemService.saveAllItems(anyList()))
                .thenReturn(ItemFixture.listOfItems());

        mockMvc.perform(post("/api/v1/item/saveAll")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(status().isCreated());
    }

}