package com.twuc.shopping.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twuc.shopping.domain.Product;
import com.twuc.shopping.model.addProduct.AddProductRequest;
import com.twuc.shopping.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wudibin
 * 2020/10/23
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    private ObjectMapper objectMapper;

    private String addProductRequestContent;

    private Product product;

    @BeforeEach
    void setUp() throws Exception {
        productRepository.deleteAll();
        objectMapper = new ObjectMapper();

        AddProductRequest request = AddProductRequest.builder()
                .name("可乐")
                .price(3)
                .unit("瓶")
                .url("/img/cola.jpg")
                .build();
        addProductRequestContent = objectMapper.writeValueAsString(request);

        product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .unit(request.getUnit())
                .url(request.getUrl())
                .build();
    }

    @Test
    @Order(1)
    public void shoule_add_product_when_product_name_not_exist() throws Exception {
        mockMvc.perform(post("/product")
                .content(addProductRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<Product> products = productRepository.findAll();
        assertEquals(1, products.size());
        assertEquals("可乐", products.get(0).getName());
    }

    @Test
    @Order(2)
    public void shoule_not_add_product_when_product_name_exist() throws Exception {
        productRepository.save(product);
        mockMvc.perform(post("/product")
                .content(addProductRequestContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void should_get_all_products() throws Exception {
        productRepository.save(product);
        mockMvc.perform(get("/products"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("可乐")))
                .andExpect(status().isOk());
    }

}
