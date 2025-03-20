package org.example.serviceforcouriers.service;

import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.order.CreateOrderDTO;
import org.example.serviceforcouriers.dto.request.CreateRequestChangeStatusDTO;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.entity.RequestChangeStatus;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.exceptions.RequestNotFoundException;
import org.example.serviceforcouriers.exceptions.UserNotFoundException;
import org.example.serviceforcouriers.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final JwtService jwtService;
    private final OrderService orderService;

    public void create(String token, @RequestBody CreateRequestChangeStatusDTO requestChangeStatusDTO) {
        if (nonNull(jwtService.getAccessClaims(token).getSubject())) {
            requestRepository.save(new RequestChangeStatus(
                    requestChangeStatusDTO.getOrderId(),
                    requestChangeStatusDTO.getNowStatus(),
                    requestChangeStatusDTO.getDesiredStatus(),
                    requestChangeStatusDTO.isAccept()

            ));
        } else {
            throw new UserNotFoundException();
        }
    }

    public RequestChangeStatus getById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> {
                    throw new RequestNotFoundException();
                }
        );
    }

    public List<RequestChangeStatus> getAllRequestNotAccept() {
        return requestRepository.findAll().stream()
                .filter(request -> !request.isAccept())
                .collect(Collectors.toList());
    }

    public List<RequestChangeStatus> getAllRequestWithAccept() {
        return requestRepository.findAll().stream()
                .filter(request -> request.isAccept())
                .collect(Collectors.toList());
    }

    @Transactional
    public void accept(String token, Long requestId, Status status) {
        if (nonNull(jwtService.getAccessClaims(token).getSubject())) {
            RequestChangeStatus request = getById(requestId);
            request.setAccept(true);
            Order order = orderService.getById(request.getOrderId());
            order.setStatus(status);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void cancel(String token, Long requestId) {
        if (nonNull(jwtService.getAccessClaims(token).getSubject())) {
            requestRepository.deleteById(requestId);
        } else {
            throw new UserNotFoundException();
        }
    }

}
