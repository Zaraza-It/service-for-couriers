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

@Controller
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<Void> createRequest(@NotBlank @RequestHeader(name = "AccessToken") String token,
                                            @Valid @RequestBody final CreateRequestChangeStatusDTO requestDto) {
        requestService.create(token, requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> receivingAccept(@NotBlank @RequestHeader(name = "AccessToken") String token,
                                                @PathVariable Long requestId, Status status) {
        requestService.accept(token, requestId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseChangeStatusDTO> getRequestById(@Positive @PathVariable Long requestId) {
        return ResponseEntity.ok(
                new ResponseChangeStatusDTO(requestService.getById(requestId))
        );
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> cancelRequest (@NotBlank @RequestHeader(name = "AccessToken") String token,
                                               @PathVariable Long requestId) {
        requestService.cancel(token, requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notaccepted")
    public ResponseEntity<List<ResponseChangeStatusDTO>> getAllRequestNotAccept() {
        List<ResponseChangeStatusDTO> responses = convertToResponseDTOList(requestService.getAllRequestNotAccept());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<ResponseChangeStatusDTO>> getAllRequestAccept() {
        List<ResponseChangeStatusDTO> responses = convertToResponseDTOList(requestService.getAllRequestWithAccept());
        return ResponseEntity.ok(responses);
    }

    private List<ResponseChangeStatusDTO> convertToResponseDTOList(List<RequestChangeStatus> requestChangeStatusList) {
        return requestChangeStatusList.stream()
                .map(ResponseChangeStatusDTO::new)
                .collect(Collectors.toList());
    }
}