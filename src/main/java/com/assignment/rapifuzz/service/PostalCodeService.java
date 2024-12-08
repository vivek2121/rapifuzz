package com.assignment.rapifuzz.service;

import com.assignment.rapifuzz.dto.response.PostalCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostalCodeService {

    private static final Map<String, PostalCodeResponse> postalCodeMap = new HashMap<>();

    static {
        // Static block to initialize postal code data
        postalCodeMap.put("110001", new PostalCodeResponse("Connaught Place", "India"));
        postalCodeMap.put("122001", new PostalCodeResponse("Patel Nagar", "India"));
        postalCodeMap.put("262401", new PostalCodeResponse("Bazpur", "India"));
    }

    public PostalCodeResponse getCityAndCountryByPostalCode(String postalCode) {
        return postalCodeMap.get(postalCode);
    }

}
