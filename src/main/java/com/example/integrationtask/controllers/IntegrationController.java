package com.example.integrationtask.controllers;
import com.example.integrationtask.entity.User;
import com.example.integrationtask.model.IntegrationResult;
import com.example.integrationtask.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class IntegrationController {

     @Autowired
     IntegrationService integrationService;

    @Value("8097")
    private int serverPort;

    @PostMapping("/calculateAge")
    public ResponseEntity<IntegrationResult> calculateAgeAndFetchData(@RequestBody User input){
        IntegrationResult result= integrationService.calculateAgeAndFetchData(input);
        return ResponseEntity.ok(result);
    }
}
