package org.example.market.service.admin;

import org.example.market.repository.ProductsRepository;
import org.example.market.repository.SoldProductRepository;
import org.example.market.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private ProductsRepository productsRepository;
    private SoldProductRepository soldProductRepository;
    private UserRepository userRepository;

}
