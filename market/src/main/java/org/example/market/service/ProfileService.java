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
import org.example.market.exception.NotFoundUserException;
import org.example.market.repository.ImageRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final SoldProductRepository soldProductRepository;
    public static final Logger logger = LogManager.getLogger(MarketService.class);


    public ResponseUserInfo getUserInfo(final Long userId,final String username) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) throw new NotFoundUserException("User not found");

               final String roles = user.get().getRoles().toString();
                    Optional<ResponseImageData> image = imageRepository.findImageByUserId(userId);
                    List<ResponseSoldProduct> stateSeller = soldProductRepository.findAllSalesProductByIdUser(userId);
                    List<ResponseSoldProduct> stateBuyer = soldProductRepository.findAllBuyProductByIdUser(userId);

               final int sumSales = summingSalesAndPurchases(stateSeller);
               final int sumPurchases = summingSalesAndPurchases(stateBuyer);
               return ResponseUserInfo.builder()
                        .username(user.get().getUsername())
                        .sales(sumSales)
                        .roles(roles.substring(5))
                        .purchases(sumPurchases)
                        .image(image.get().getImageData())
                        .build();
    }

    public static int summingSalesAndPurchases(List<ResponseSoldProduct> products) {
    if (products.isEmpty()) return 0;
    return products.stream()
          .filter(response -> response.getQuantity() > 0)
          .collect(Collectors.summingInt(ResponseSoldProduct::getQuantity));
    }

    public ResponseSettingsUser getAndSetSettingsUser(String username, RequestSettingsUser request) {
            Optional<User> user = userRepository.findByUsername(username);
               if (user.isEmpty()) throw new NotFoundUserException("User not found"); {

               if (Objects.nonNull(request.getUsername() != null)) {
                    user.get().setUsername(request.getUsername());
               }

               if (Objects.nonNull(request.getEmail())) {
                   user.get().setEmail(request.getEmail());
               }

               userRepository.save(user.get());

               return ResponseSettingsUser.builder()
                       .username(user.get().getUsername())
                       .email(user.get().getEmail())
                       .role( user.get().getRoles().
                               toString().substring(5))
                       .build();
              }
    }


}




