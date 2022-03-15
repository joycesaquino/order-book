package com.meli.orderbook.service;

import com.meli.orderbook.converter.OperationConverter;
import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.enums.OperationType;
import com.meli.orderbook.repository.buy.BuyRepository;
import com.meli.orderbook.repository.sale.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

    private final SaleRepository saleRepository;
    private final BuyRepository buyRepository;
    private final OperationConverter operationConverter;

    public OperationService(SaleRepository saleRepository,
                            BuyRepository buyRepository,
                            OperationConverter operationConverter) {
        this.saleRepository = saleRepository;
        this.buyRepository = buyRepository;
        this.operationConverter = operationConverter;
    }

    public void save(OperationDto dto) {
        var operation = operationConverter.apply(dto);
        operation.setHash(operation.hash());

        if (dto.getOperationType().equals(OperationType.SALE)) {
            saleRepository.save(operation);
        }

        if (dto.getOperationType().equals(OperationType.BUY)) {
            buyRepository.save(operation);
        }
    }
}
