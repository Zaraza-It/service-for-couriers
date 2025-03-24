package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.market.dto.response.ResponseImageData;
import org.example.market.dto.response.ResponseSettingsUser;
import org.example.market.dto.response.ResponseSoldProduct;
import org.example.market.dto.response.ResponseUserInfo;
import org.example.market.dto.request.RequestSettingsUser;
import org.example.market.entity.User;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ImageRepository imageRepository;
    private final SoldProductRepository soldProductRepository;
    public static final Logger logger = LogManager.getLogger(MarketService.class);


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




    public ResponseSettingsUser getAndSetSettingsUser(String token, RequestSettingsUser requestSettingsUser) {
        try {
            String username = jwtService.getAccessClaims(token).getSubject();
            Optional<User> user = userRepository.findByUsername(username);
            if (username != null && user.isPresent()) {

               if (nonNull(requestSettingsUser.getUsername())) {
                    user.get().setUsername(requestSettingsUser.getUsername());
               }

               if (nonNull(requestSettingsUser.getEmail())) {
                   user.get().setEmail(requestSettingsUser.getEmail());
               }

               userRepository.save(user.get());
               ResponseSettingsUser resp = new ResponseSettingsUser();
                    resp.setUsername(user.get().getUsername());
                    resp.setEmail(requestSettingsUser.getEmail());
               return resp;
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
        }




