package com.assignment.rapifuzz.controller;

import com.assignment.rapifuzz.dto.response.PostalCodeResponse;
import com.assignment.rapifuzz.service.PostalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostalCodeController {

    private final PostalCodeService postalCodeService;

    @Autowired
    public PostalCodeController(PostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    @GetMapping("/api/postal/{pinCode}")
    public ResponseEntity<?> getCityAndCountry(@PathVariable String pinCode) {
        return ResponseEntity.status(HttpStatus.OK).body(postalCodeService.getCityAndCountryByPostalCode(pinCode));
    }
}
