package com.meli.orderbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class Id {

    private Long userId;
    private UUID requestId;

}
