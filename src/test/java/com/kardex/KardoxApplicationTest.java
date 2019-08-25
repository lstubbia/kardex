package com.kardex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kardox.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductService.class)
public class KardoxApplicationTest {

    @Test
    public void contextLoads() {
    }

}