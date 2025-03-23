package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.ResponseImageData;
import org.example.market.dto.ResponseSoldProduct;
import org.example.market.dto.ResponseUserInfo;
import org.example.market.entity.Image;
import org.example.market.entity.User;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ImageRepository imageRepository;
    private final SoldProductRepository soldProductRepository;
    public static final Logger logger = LogManager.getLogger(MarketService.class);


    @Transactional
    public ResponseUserInfo getUserInfo(Long userId,String token) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                String roles = jwtService.getAccessClaims(token).get("roles").toString();
                Optional<ResponseImageData> image = imageRepository.findImageByUserId(userId);
                List<ResponseSoldProduct> stateSeller = soldProductRepository.findSoldByIdUser(userId);
                List<ResponseSoldProduct> stateBuyer = soldProductRepository.findSoldByIdUser(userId);

                int sumSales = stateSeller.stream()
                  .collect(Collectors.
                   summingInt(ResponseSoldProduct::getQuantity));
                int sumBuyer = stateBuyer.stream()
                        .collect(Collectors.summingInt(ResponseSoldProduct::getQuantity));
                ResponseUserInfo responseUserInfo = ResponseUserInfo.builder()
                        .username(user.get().getUsername())
                        .sales(sumSales)
                        .roles(roles.substring(5))
                        .purchases(sumBuyer)
                        .image(image.get().getImageData())
                        .build();
                return responseUserInfo;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
        }




