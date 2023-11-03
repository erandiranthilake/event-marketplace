package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Location;
import com.uta.adveng.eventmarketplace.dataaccess.ILocationRepository;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LocationController {

    private ILocationRepository locationRepository;

    @CrossOrigin
    @PostMapping("/location/create")
    public ResponseEntity<Object> createLocation(@RequestBody Location location) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(location.getServiceId(), "ServiceId");
            ValidationUtil.checkNotNull(location.getCompanyId(), "CompanyId");

            Location responseLocation = locationRepository.save(location);
            responseEntity = new ResponseEntity<Object>(responseLocation, HttpStatus.CREATED);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Create Location Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @CrossOrigin
    @GetMapping("/location/{companyId}/{serviceId}")
    public ResponseEntity<Object> getLocationByServiceKey(@PathVariable String companyId, @PathVariable String serviceId) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(companyId, "CompanyId");
            ValidationUtil.checkNotNull(serviceId, "ServiceId");

            List<Location> response = locationRepository.findByCompanyIdAndServiceId(companyId, serviceId);
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get Location by CompanyId and ServiceId", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
