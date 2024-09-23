package com.example.app.services;

import com.example.app.dto.ItemUpdateDto;
import com.example.app.entities.ItemEntity;
import com.example.app.exceptions.ItemNotFoundException;
import com.example.app.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Cacheable(value = "item", keyGenerator = "customKeyGenerator")
    public ItemEntity getItemById(UUID uuid) {
        return itemRepository.findById(uuid)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Cacheable(value = "item", keyGenerator = "customKeyGenerator")
    public List<ItemEntity> getAll() {
        return itemRepository.findAll();
    }

    public ItemEntity saveOneItem(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    public List<ItemEntity> saveAllItems(List<ItemEntity> itemEntity) {
        return itemRepository.saveAll(itemEntity);
    }

    public ItemEntity updateItem(UUID id, ItemUpdateDto newItem) {
        return itemRepository.findById(id)
                .map(oldItem -> {
                    if (isNull(newItem.getName()))
                        oldItem.setName(newItem.getName());

                    if (isNull(newItem.getCategory()))
                        oldItem.setCategory(newItem.getCategory());

                    if (isNull(newItem.getQuantity()))
                        oldItem.setQuantity(newItem.getQuantity());

                    if (isNull(newItem.getPrice()))
                        oldItem.setPrice(newItem.getPrice());

                    return itemRepository.save(oldItem);
                }).orElseThrow(ItemNotFoundException::new);
    }

    public void deleteById(UUID id) {
        var item = getItemById(id);
        itemRepository.deleteById(item.getId());
    }

    private boolean isNull(Object prop) {
        return Objects.nonNull(prop);
    }
}
