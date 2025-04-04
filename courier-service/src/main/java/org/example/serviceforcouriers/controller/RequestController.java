package org.example.serviceforcouriers.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.serviceforcouriers.dto.request.CreateRequestChangeStatusDTO;
import org.example.serviceforcouriers.dto.request.ResponseChangeStatusDTO;
import org.example.serviceforcouriers.entity.RequestChangeStatus;
import org.example.serviceforcouriers.enums.Status;
import org.example.serviceforcouriers.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public void createRequest(@NotBlank @RequestHeader(name = "AccessToken") String token,
                                            @Valid @RequestBody final CreateRequestChangeStatusDTO requestDto) {
        requestService.create(token, requestDto);
    }

//    @PutMapping("/{requestId}")
//    public void receivingAccept(@NotBlank @RequestHeader(name = "AccessToken") String token,
//                                                @PathVariable Long requestId, Status status) {
//        requestService.accept(token, requestId, status);
//    }

//    @GetMapping("/{requestId}")
//    public ResponseChangeStatusDTO getRequestById(@Positive @PathVariable Long requestId) {
//        return requestService.getById(requestId);
//    }

    @DeleteMapping("/{requestId}")
    public void cancelRequest (@NotBlank @RequestHeader(name = "AccessToken") String token,
                                               @PathVariable Long requestId) {
        requestService.cancel(token, requestId);
    }

//    @GetMapping("/notaccepted")
//    public List<ResponseChangeStatusDTO> getAllRequestNotAccept() {
//        return convertToResponseDTOList(requestService.getAllRequestNotAccept());
//    }

//    @GetMapping("/accepted")
//    public List<ResponseChangeStatusDTO> getAllRequestAccept() {
//        return convertToResponseDTOList(requestService.getAllRequestWithAccept());
//    }

//    TODO: переносим эту логику в service; +вместо конструктора для преобразования в дто используем mapstruct
//    private List<ResponseChangeStatusDTO> convertToResponseDTOList(List<RequestChangeStatus> requestChangeStatusList) {
//        return requestChangeStatusList.stream()
//                .map(ResponseChangeStatusDTO::new)
//                .collect(Collectors.toList());
//    }
}