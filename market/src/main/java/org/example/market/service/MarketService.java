package org.example.market.service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.ProductDTO;
import org.example.market.dto.request.ProductRequest;
import org.example.market.dto.request.ProductUpdateRequest;
import org.example.market.entity.Image;
import org.example.market.entity.Product;
import org.example.market.entity.SoldProduct;
import org.example.market.entity.User;
import org.example.market.entity.enums.StatusProduct;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    public static final Logger logger = LogManager.getLogger(MarketService.class);
    private final ProductsRepository productsRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SoldProductRepository soldProductRepository;
    private final ImageRepository imageRepository;
    private HttpClient httpClient;

    public void createProduct(@NotBlank String accessToken, @Valid ProductRequest request) {
        try {
            final String username = jwtService.getAccessClaims(accessToken).getSubject();
            logger.info(username);
            if (userRepository.findByUsername(username) != null) {
                Product product = Product.builder()
                        .categoryProduct(request.getCategoryProduct())
                        .productPrice(request.getProductPrice())
                        .productName(request.getProductName())
                        .quantity(request.getQuantity())
                        .build();
                    HttpRequest httpRequest = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:9000/test")).GET()
                            .build();
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                User user = userRepository.findByUsername(username);
                logger.info(user.getUsername());
                product.setUser(user);
                productsRepository.save(product);

            }
        } catch (Exception e) {
            logger.error(e);
        }
    }


    public List<Product> filterProductByCategory(String categoryProduct) {
        try {
            List<Product> products = productsRepository.findAllByCategoryProduct(categoryProduct);
            return products;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    public void deleteProductByUser(Long id, String token) {
        try {
            String username = jwtService.getAccessClaims(token).getSubject();
            if (userRepository.findByUsername(username) != null) {
                User user = userRepository.findByUsername(username);
                logger.info(user.getUsername());
                if (productsRepository.findAllByUser(user) != null) {
                    List<Product> products = productsRepository.findAllByUser(user);
                    logger.info(products.getFirst().getProductId());
                    Long idSearch = searchProduct(products, id);
                    logger.info(idSearch);

                    if (idSearch != null) {
                        productsRepository.deleteById(idSearch);
                    } else logger.info("Product not found");
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static Long searchProduct(List<Product> products, Long id) {
        for (int i = 0; i < products.size(); i++) {
            Long result = products.get(i).getProductId();
            if (result.equals(id)) {
                return result;
            } else logger.info(result + " Error");

        }
        return null;
    }


    public List getAllProducts() {
        try {
            List<ProductDTO> products = productsRepository.findAllProducts();
            return products;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    public void buyProduct(String token, Long id, Integer quantity) {
        try {
            String username = jwtService.getAccessClaims(token).getSubject();
            if (userRepository.findByUsername(username) != null) {
                User user = userRepository.findByUsername(username);
                Product product = productsRepository.findProductByProductId(id);
                if (product != null) {
                    BigDecimal productPrice = product.getProductPrice();
                    BigDecimal userBalance = user.getBalance();
                    int result = productPrice.compareTo(userBalance);
                    if (result == -1) {
                        if (quantity <= product.getQuantity() && product.getQuantity() > 0) {
                            Integer resultQuantity = product.getQuantity() - quantity;
                            product.setQuantity(resultQuantity);
                            User productOwner = userRepository.findByUsername(product.getUser().getUsername());
                            if (productOwner != user) {
                                if (productOwner.getBalance() != null) {
                                    productOwner.setBalance(productOwner.getBalance().add(productPrice));
                                    BigDecimal resultBalance = userBalance.subtract(productPrice);
                                    user.setBalance(resultBalance);
                                    SoldProduct soldProduct = SoldProduct.builder()
                                            .nameSeller(productOwner.getUsername())
                                            .nameBuyer(user.getUsername())
                                            .product(product)
                                            .id_buyer(user.getId())
                                            .id_seller(productOwner.getId())
                                            .quantity(quantity)
                                            .uniqueId(generateUnId())
                                            .build();
                                    soldProductRepository.save(soldProduct);
                                    userRepository.save(user);
                                    userRepository.save(productOwner);
                                } else {
                                    BigDecimal balance = BigDecimal.valueOf(0);
                                    productOwner.setBalance(balance);
                                    BigDecimal resultBalance = userBalance.subtract(productPrice);
                                    user.setBalance(resultBalance);
                                    productOwner.setBalance(productOwner.getBalance().add(productPrice));
                                    product.setUser(user);
                                    SoldProduct soldProduct = SoldProduct.builder()
                                            .nameSeller(productOwner.getUsername())
                                            .nameBuyer(user.getUsername())
                                            .product(product)
                                            .id_buyer(user.getId())
                                            .id_seller(productOwner.getId())
                                            .quantity(resultQuantity)
                                            .uniqueId(generateUnId())
                                            .build();
                                    soldProductRepository.save(soldProduct);
                                    userRepository.save(user);
                                    userRepository.save(productOwner);
                                }
                            } else throw new Exception("Вы не можете купить свой товар!");
                        } else throw new Exception("Увы... Товар закончился!!");
                    } else throw new Exception("Недостаточно денег для данного товара!");
                } else throw new Exception("Продукт не найден!");
            } else throw new Exception("Ваш username не валидный!");
        } catch (Exception e) {
            logger.error(e);
        }
    }
//if (product.getQuantity().equals(0)) {
//                    soldProductRepository.delete();

    public void updateProduct(Long productId, String token, ProductUpdateRequest request) {
        try {
            final String username = jwtService.getAccessClaims(token).getSubject();
            if (userRepository.findByUsername(username) != null) {
                Product product = productsRepository.findProductByUsernameAndProductId(username, productId);
                if (product != null) {
                    logger.info(product);
                    if (request.getProductName() != null) {
                        product.setProductName(request.getProductName());
                    }
                    if (request.getQuantity() != null) {
                        product.setQuantity(request.getQuantity());
                    }
                    if (request.getPrice() != null) {
                        product.setProductPrice(request.getPrice());
                    }
                    if (request.getCategoryProduct() != null) {
                        product.setCategoryProduct(request.getCategoryProduct());
                    }
                    productsRepository.save(product);
                } else throw new Exception("Товар не найден!");
            } else throw new Exception("Пользователь не найден");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static String generateUnId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


    public Image getImage(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                logger.info("image not found");
            }
            if(file != null) {
                Image image = new Image();
                image.setImageData(file.getBytes());
                return image;
            }
            } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }


}




