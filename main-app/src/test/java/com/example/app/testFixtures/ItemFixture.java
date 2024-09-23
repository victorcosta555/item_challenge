package com.example.app.testFixtures;

import com.example.app.entities.ItemEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ItemFixture {

    private static ItemEntity.ItemEntityBuilder itemA() {
        return ItemEntity.builder()
                .category("Electronic")
                .price(BigDecimal.valueOf(12.33))
                .description("Smartphone")
                .name("Smartphone xyz");
    }

    private static ItemEntity.ItemEntityBuilder itemB() {
        return ItemEntity.builder()
                .category("Electronic")
                .price(BigDecimal.valueOf(10.00))
                .name("Smartphone abc");
    }

    public static ItemEntity oneItem() {
        return itemA()
                .id(UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .quantity(2)
                .build();
    }

    public static ItemEntity itemUpdated() {
        return itemA()
                .id(UUID.fromString("4c22732f-f4f7-436d-9620-c8bcf9fdc696"))
                .name("Smartphone dyz")
                .price(BigDecimal.valueOf(100.01))
                .build();
    }


    public static List<ItemEntity> listOfItems() {
        return List.of(oneItem(), itemB().id(UUID.fromString("003a83d8-2db7-4152-a243-95f484ff0c59")).quantity(1).build());
    }

    public static ItemEntity oneItemValidToSave() {
        return itemA().quantity(2).build();
    }

    public static ItemEntity oneItemInvalidToSave() {
        return ItemEntity.builder()
                .category("Electronic")
                .build();
    }

    public static List<ItemEntity> listOfValidItemsToSave() {
        return List.of(itemA().quantity(2).build(), itemB().quantity(1).build());
    }
}
