package com.meli.orderbook.controller;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.enums.operation.Type;
import com.meli.orderbook.service.OperationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OperationControllerTest {

    @InjectMocks
    OperationController operationController;

    @Mock
    OperationService service;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("This test should create an operation")
    public void create() {
        var dto = mock(OperationDto.class);
        ResponseEntity<?> response = operationController.create(dto);
        assertEquals(response.getStatusCode(), (HttpStatus.CREATED));
    }

    @Test
    @DisplayName("This test should have no violations in operation dto")
    public void shouldHaveNoViolations() {

        var dto = new OperationDto();
        dto.setQuantity(1);
        dto.setValue(BigDecimal.TEN);
        dto.setOperationType(Type.SALE);
        dto.setUserId(Long.parseLong("1234"));

        Set<ConstraintViolation<OperationDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("This test should have Min Value violation operation dto")
    public void shouldHaveViolationsForMinOperationValue() {

        var dto = new OperationDto();
        dto.setQuantity(1);
        dto.setValue(BigDecimal.ZERO);
        dto.setOperationType(Type.SALE);
        dto.setUserId(Long.parseLong("1234"));

        Set<ConstraintViolation<OperationDto>> violations = validator.validate(dto);
        assertEquals(violations.size(), 1);
    }

    @Test
    @DisplayName("This test should have Min Operation quantity violation operation dto")
    public void shouldHaveViolationsForMinOperationQuantity() {

        var dto = new OperationDto();
        dto.setQuantity(0);
        dto.setValue(BigDecimal.ONE);
        dto.setOperationType(Type.SALE);
        dto.setUserId(Long.parseLong("1234"));

        Set<ConstraintViolation<OperationDto>> violations = validator.validate(dto);
        assertEquals(violations.size(), 1);
    }

    @Test
    @DisplayName("This test should have violation for null userId operation dto")
    public void shouldHaveViolationsForUserId() {

        var dto = new OperationDto();
        dto.setQuantity(1);
        dto.setValue(BigDecimal.ZERO);
        dto.setOperationType(Type.SALE);

        Set<ConstraintViolation<OperationDto>> violations = validator.validate(dto);
        assertEquals(violations.size(), 1);
    }

}