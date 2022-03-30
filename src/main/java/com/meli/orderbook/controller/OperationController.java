package com.meli.orderbook.controller;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/operations")
@Slf4j
public class OperationController {

    private final OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody final OperationDto dto) {
        log.debug("Requesting operation with content {}",dto.toString());
        service.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}