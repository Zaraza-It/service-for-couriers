package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.entity.Image;
import org.example.market.entity.Product;
import org.example.market.entity.User;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.ProductsRepository;
import org.example.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductsRepository productsRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public static final Logger logger = LogManager.getLogger(MarketService.class);

    public void addImageInProduct(MultipartFile file,String token,Long id) {
        try {
           String username = jwtService.getAccessClaims(token).getSubject();
           if (username != null) {
             Product product = productsRepository.findProductByUsernameAndProductId(username,id);
             if (product != null) {
                 Image image = Image.builder()
                         .imageName(file.getOriginalFilename())
                         .imageData(file.getBytes())
                         .build();
                 image.setProduct(product);
                 imageRepository.save(image);
             }
           }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void addAvatarUser(String token,MultipartFile file) throws IOException {
      try {
       String username = jwtService.getAccessClaims(token).getSubject();
       if (username != null) {
        User user = userRepository.findByUsername(username);
        Image image = Image.builder()
                .imageName(file.getOriginalFilename())
                .imageData(file.getBytes())
                .build();
        image.setUser(user);
        imageRepository.save(image);
       }
    } catch (Exception e) {
      logger.error(e);
      }
    }



}
