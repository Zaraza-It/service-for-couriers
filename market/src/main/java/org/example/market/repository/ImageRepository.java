package org.example.market.repository;

import org.example.market.dto.ResponseImageData;
import org.example.market.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @NativeQuery("SELECT image_data FROM image WHERE user_id = :userId")
   Optional<ResponseImageData> findImageByUserId(@Param("userId") Long userId);
}
