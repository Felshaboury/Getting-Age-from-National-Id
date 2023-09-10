package com.example.integrationtask.service;
import com.example.integrationtask.entity.User;
import com.example.integrationtask.model.IntegrationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class IntegrationService {
    private final SoapService soapService;

    public IntegrationService(SoapService soapService) {
        this.soapService = soapService;
    }


    public IntegrationResult calculateAgeAndFetchData(User input) {
        String soapResponse = soapService.callSoapMockService(input.getNationalId());
        validateNationalId(input.getNationalId());
            LocalDate birthdate = extractBirthdate(input.getNationalId());
            int age = calculateAgeFromBirthdate(birthdate);

            String status = (age > 20) ? "Hello java man" : (age < 20) ? "Hello java baby" : "Hello in java";
            String address = extractAddressFromSoapResponse(soapResponse);
            String university = extractUniversityFromSoapResponse(soapResponse);

            IntegrationResult result = new IntegrationResult();
            result.setAge(age);
            result.setStatus(status);
            result.setAddress(address);
            result.setUniversity(university);

            return result;
        }

        private LocalDate extractBirthdate(String nationalId) {
            int birthYearLastTwoDigits = Integer.parseInt(nationalId.substring(1, 3));
            int birthMonth = Integer.parseInt(nationalId.substring(3,5));
            int birthDay = Integer.parseInt(nationalId.substring(5,7));

            int birthYear;
            int birthCentury = Character.getNumericValue(nationalId.charAt(0));
            if (birthCentury == 2) {
                birthYear = 1900 + birthYearLastTwoDigits;
            } else if (birthCentury == 3) {
                birthYear = 2000 + birthYearLastTwoDigits;
            } else {
                throw new IllegalArgumentException("Invalid birth century");
            }

            return LocalDate.of(birthYear, birthMonth, birthDay);
        }

        private int calculateAgeFromBirthdate(LocalDate birthdate) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthdate, currentDate);
            return period.getYears();
        }
    private String extractAddressFromSoapResponse(String soapResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(soapResponse);

            if (jsonNode.has("address")) {
                return jsonNode.get("address").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Address";
    }

    private String extractUniversityFromSoapResponse(String soapResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(soapResponse);

            if (jsonNode.has("university")) {
                return jsonNode.get("university").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown University";
    }
        void validateNationalId(String nationalId) {
            if (nationalId != null && !nationalId.equals("")) {
                if (nationalId.length() != 14) {
                    throw new IllegalArgumentException("National ID must be 14 characters long");
                }

                int birthCentury = Character.getNumericValue(nationalId.charAt(0));
                if (birthCentury != 2 && birthCentury != 3) {
                    throw new IllegalArgumentException("Invalid birth century");
                }
            }
        }
    }


