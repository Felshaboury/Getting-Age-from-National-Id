package com.example.integrationtask.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SoapService {

    private final RestTemplate restTemplate;

    @Autowired
    public SoapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callSoapMockService(String nationalId) {
        try {
            String soapRequestBody = generateSoapRequest(nationalId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8091/integration/test/calculateAge",
                    HttpMethod.POST,
                    new HttpEntity<>(soapRequestBody, headers),
                    String.class
            );

            // Process the SOAP response
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while calling SOAP service.";
        }
    }

    private String generateSoapRequest(String nationalId) {
        // Generate the SOAP request body using the provided national ID
        // Return the generated XML SOAP request as a string
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "<soapenv:Header/>\n"
                + "<soapenv:Body>\n"
                + "<YourSoapOperationName>\n"
                + "<NationalId>" + nationalId + "</NationalId>\n"
                + "</YourSoapOperationName>\n"
                + "</soapenv:Body>\n"
                + "</soapenv:Envelope>";
    }
}

