package org.example.market.repository;

import org.example.market.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
