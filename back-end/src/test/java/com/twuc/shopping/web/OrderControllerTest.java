package com.twuc.shopping.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.domain.OrderPO;
import com.twuc.shopping.domain.ProductPO;
import com.twuc.shopping.model.addOrder.AddOrderRequest;
import com.twuc.shopping.model.addOrder.AddProductVO;
import com.twuc.shopping.repository.OrderRepository;
import com.twuc.shopping.repository.ProductRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wudibin
 * 2020/10/23
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    private String addOrderRequestContent;

    private ProductPO productPO;

    @BeforeEach
    void setUp() throws Exception {
        productRepository.deleteAll();
        orderRepository.deleteAll();
        ObjectMapper objectMapper = new ObjectMapper();

        List<AddProductVO> addProductVOs = Lists.newArrayList();
        addProductVOs.add(new AddProductVO("可乐", 3));
        AddOrderRequest request = AddOrderRequest.builder()
                .addProductVOs(addProductVOs)
                .build();
        addOrderRequestContent = objectMapper.writeValueAsString(request);

        productPO = ProductPO.builder()
                .name("可乐")
                .price(3)
                .unit("瓶")
                .url("/img/cola.jpg")
                .build();
    }

    @Test
    @Order(1)
    public void shoule_add_order_when_product_exist() throws Exception {
        productRepository.save(productPO);
        mockMvc.perform(post("/order")
                .content(addOrderRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<OrderPO> orderPOs = orderRepository.findAll();
        assertEquals(1, orderPOs.size());
        assertEquals("可乐", orderPOs.get(0).getOrderItem().get(0).getProductPO().getName());
    }

    @Test
    @Order(2)
    public void shoule_not_add_order_when_product_not_exist() throws Exception {
        mockMvc.perform(post("/order")
                .content(addOrderRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
