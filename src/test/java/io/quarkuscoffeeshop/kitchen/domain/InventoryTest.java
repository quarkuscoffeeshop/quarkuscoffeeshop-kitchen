package io.quarkuscoffeeshop.kitchen.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.kitchen.domain.exceptions.EightySixException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryTest {

    @Inject
    Inventory inventory;

    @Test @Order(1)
    public void testStockIsPopulated() {

        Map<Item, Integer> inStock = inventory.getStock();
        assertNotNull(inStock);
        inStock.forEach((k,v) -> {
            System.out.println(k + " " + v);
        });
    }

    @Test @Order(2)
    public void testEightySixCroissants() {

        Integer itemCount = inventory.getItemCount(Item.CROISSANT);
        for (int i = 0; i < itemCount; i++) {
            try {
                inventory.decrementItem(Item.COFFEE_BLACK);
            } catch (Exception e) {
                assertEquals(EightySixException.class, e.getClass());
                assertEquals(itemCount, Integer.valueOf(i));
            }
        }
    }
}
