package pl.pytlak.photoart.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pytlak.photoart.dto.request.AddRateRequest;
import pl.pytlak.photoart.entity.Rate;
import pl.pytlak.photoart.service.Rate.RateService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class RateController {

    private final RateService rateService;

    @PutMapping("/addRate")
    public ResponseEntity<?> addRate(@Valid @RequestBody AddRateRequest addRateRequest) {
        try {
            rateService.addRateByPhotoId(addRateRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
