package org.example.serviceforcouriers.mapper;

import org.example.serviceforcouriers.dto.order.OrderResponseDTO;
import org.example.serviceforcouriers.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ResponseOrderMapper {
    Order toEntity(OrderResponseDTO dto);

    OrderResponseDTO toDto(Order entity);
}
