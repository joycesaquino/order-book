package com.meli.orderbook.controller;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OperationController {

    OperationService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody final OperationDto dto) {
        service.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}