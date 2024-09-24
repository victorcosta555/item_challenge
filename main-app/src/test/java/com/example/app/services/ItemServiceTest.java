package com.example.app.services;

import com.example.app.dto.ItemUpdateDto;
import com.example.app.exceptions.ItemNotFoundException;
import com.example.app.repositories.ItemRepository;
import com.example.app.testFixtures.ItemFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Test
    void getItemById_itemExist_returnItem() {
        var uuid = UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696");

        when(itemRepository.findById(uuid))
                .thenReturn(Optional.of(ItemFixture.oneItem()));

        var item = itemService.getItemById(uuid);

        assertThat(item)
                .isNotNull();

        assertThat(item.getId())
                .isEqualTo(uuid);

        assertThat(item.getName())
                .isEqualTo("Smartphone xyz");
    }

    @Test
    void getItemById_itemNotExist_throwException() {
        var uuid = UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696");

        when(itemRepository.findById(uuid))
                .thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> {
            itemService.getItemById(uuid);
        });
    }

    @Test
    void getAlLItems_listAllItems_listOfItems() {
        when(itemRepository.findAll())
                .thenReturn(ItemFixture.listOfItems());

        var items = itemService.getAll();

        assertThat(items)
                .contains(ItemFixture.oneItem());
    }

    @Test
    void saveOneItem_validItem_successSave() {
        when(itemRepository.save(ItemFixture.oneItemValidToSave()))
                .thenReturn(ItemFixture.oneItem());

        doNothing().when(kafkaProducerService).sendMessage(any());

        var savedItem = itemService.saveOneItem(ItemFixture.oneItemValidToSave());

        assertThat(savedItem)
                .isNotNull();

        assertThat(savedItem)
                .isEqualTo(ItemFixture.oneItem());
    }

    @Test
    void saveOneItem_invalidItem_throwError() {
        when(itemRepository.save(ItemFixture.oneItemInvalidToSave()))
                .thenThrow(new DataIntegrityViolationException("Constraint violation"));


        assertThrows(DataIntegrityViolationException.class, () -> {
            itemService.saveOneItem(ItemFixture.oneItemInvalidToSave());
        });
    }

    @Test
    void saveListOfItem_validItems_successSaveAll() {
        when(itemRepository.saveAll(ItemFixture.listOfValidItemsToSave()))
                .thenReturn(ItemFixture.listOfItems());

        var savedItems = itemService.saveAllItems(ItemFixture.listOfValidItemsToSave());

        assertThat(savedItems)
                .isNotNull();

        assertThat(savedItems)
                .containsAll(ItemFixture.listOfItems());
    }

    @Test
    void updateItem_validItem_successfulUpdateItem() {
        var itemUpdateDto = ItemUpdateDto
                .builder()
                .name("Smartphone dyz")
                .price(BigDecimal.valueOf(100.01))
                .build();

        var uuid = UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696");

        when(itemRepository.findById(uuid))
                .thenReturn(Optional.of(ItemFixture.oneItem()));

        when(itemRepository.save(any()))
                .thenReturn(ItemFixture.itemUpdated());


        var updatedItem = itemService.updateItem(uuid, itemUpdateDto);

        assertThat(updatedItem.getId())
                .isEqualTo(uuid);

        assertThat(updatedItem.getName())
                .isEqualTo("Smartphone dyz");

        assertThat(updatedItem.getPrice())
                .isEqualTo(BigDecimal.valueOf(100.01));
    }

    @Test
    void deleteItem_itemExist_successfullyDeleteItem() {
        var uuid = UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696");

        when(itemRepository.findById(uuid))
                .thenReturn(Optional.of(ItemFixture.oneItem()));

        doNothing().when(itemRepository).deleteById(uuid);

        itemService.deleteById(uuid);

        when(itemRepository.findById(uuid))
                .thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> {
            itemService.deleteById(uuid);
        });
    }
}