package org.example.serviceforcouriers.mapper;

import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CreateOrderMapper {
    Order toEntity(CreateOrderDTO dto);

    CreateOrderDTO toDto(Order entity);
}
