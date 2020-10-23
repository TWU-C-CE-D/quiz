package com.twuc.shopping.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.domain.OrderItemPO;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private OrderItemPO orderItemPO;

    private OrderPO orderPO;

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
        orderPO = OrderPO.builder().id("12345").build();
        orderItemPO = OrderItemPO.builder()
                .number(5)
                .productPO(productPO)
                .orderPO(orderPO)
                .build();
        List<OrderItemPO> orderItemPOs = Lists.newArrayList(orderItemPO);
        orderPO.setOrderItem(orderItemPOs);
        orderPO.setTotal(productPO.getPrice());
    }

    @Test
    @Order(1)
    public void shoule_add_order_when_product_exist() throws Exception {
        productRepository.save(productPO);
        mockMvc.perform(post("/order")
                .content(addOrderRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    @Order(2)
    public void shoule_not_add_order_when_product_not_exist() throws Exception {
        mockMvc.perform(post("/order")
                .content(addOrderRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void should_get_all_order() throws Exception {
        productRepository.save(productPO);
        OrderPO saveOrder = orderRepository.save(orderPO);
        mockMvc.perform(get("/orders"))
                .andExpect(jsonPath("$.getOrderVOs", hasSize(1)))
                .andExpect(jsonPath("$.getOrderVOs[0].orderId", is(saveOrder.getId())))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void should_delete_order_when_id_exsit() throws Exception {
        productRepository.save(productPO);
        OrderPO saveOrder = orderRepository.save(orderPO);
        mockMvc.perform(delete("/order/{id}", saveOrder.getId()))
                .andExpect(status().isOk());
        assertEquals(0, orderRepository.findAll().size());
    }

}
