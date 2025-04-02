package org.example.securityservice.service;

import org.example.market.dto.ProductDTO;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.example.market.entity.enums.ProductCategory;
import org.example.market.entity.enums.Role;
import org.example.market.exception.NotFoundUserException;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.example.market.service.MarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.creation.bytebuddy.MockAccess;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarketServiceTest  {
    private ProductDTO product;
    private ProductDTO product2;

    @BeforeEach
    void setUp() {
        ProductDTO product = new ProductDTO();
        product.setProductName("test");
        product.setUsername("test");

        ProductDTO product2 = new ProductDTO();
        product2.setProductName("test2");
        product2.setUsername("test2");
    }


    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private MarketService marketService;

    @Mock
    private SoldProductRepository soldProductRepository;


    @Test
    void TestSearchIdProduct() {
        Product product = new Product();
        product.setProductId(1L);
            List<Product> products = new ArrayList<>();
            products.add(product);
            product.setProductId(1L);
        Long result = MarketService.searchProduct(products,1L);
        assertEquals(1L, result);
        MockitoAnnotations.openMocks(this);
    }

   @Test
   void TestGetAllProducts() {
        when(productsRepository.findAllProducts())
            .thenReturn(Arrays.asList(product2, product));

        List<ProductDTO> result = marketService.getAllProducts();

        assertTrue(result != null);
        assertEquals(2, result.size());
        verify(productsRepository, times(1)).findAllProducts();
   }

@Test
void BuyProduct() {

}


}
