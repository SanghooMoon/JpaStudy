package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
//    @Rollback(value = false)
    public void 상품_저장() throws Exception {
        // given
        Item item = new Book();
        item.setName("book");

        // when
        Long savedId = itemService.saveItem(item);

        // then
        assertEquals(item, itemRepository.findOne(savedId));
    }

    @Test
//    @Rollback(value = false)
    public void 기존상품_저장() throws Exception {
        // given
        Item item = new Book();
        item.setName("book");

        itemService.saveItem(item);
        item.setName("transBook");

        // when
        Long reSavedId = itemService.saveItem(item);

        // then
        assertEquals(item, itemRepository.findOne(reSavedId));
        assertEquals("transBook", itemRepository.findOne(reSavedId).getName());
    }

    @Test
    public void 재고_증가() throws Exception {
        // given
        Item item = new Book();

        // when
        item.addStock(10);

        // then
        assertEquals(10, item.getStockQuantity());
    }

    @Test
    public void 재고_감소() throws Exception {
        // given
        Item item = new Book();
        item.addStock(10);

        // when
        item.removeStock(5);

        // then
        assertEquals(5, item.getStockQuantity());
    }

}