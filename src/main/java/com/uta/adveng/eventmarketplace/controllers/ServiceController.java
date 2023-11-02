package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Service;
import com.uta.adveng.eventmarketplace.dao.ServiceKey;
import com.uta.adveng.eventmarketplace.dataaccess.IServiceRepository;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ServiceController {

    private IServiceRepository serviceRepository;

    @PostMapping("/service/create")
    public ResponseEntity<Object> createService(@RequestBody Service service) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(service.getServiceKey().getServiceId(), "ServiceId");
            ValidationUtil.checkNotNull(service.getServiceKey().getCompanyId(), "CompanyId");

            Service responseService = serviceRepository.save(service);
            responseEntity = new ResponseEntity<Object>(responseService, HttpStatus.CREATED);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Create Service Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/service/{companyId}/{serviceId}")
    public ResponseEntity<Object> getServiceByServiceKey(@PathVariable String companyId, @PathVariable String serviceId) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(companyId, "CompanyId");
            ValidationUtil.checkNotNull(serviceId, "ServiceId");

            ServiceKey key = new ServiceKey(companyId, serviceId);
            Service responseService = serviceRepository.findByServiceKey(key);

            responseEntity = new ResponseEntity<Object>(responseService, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get service by Service Kay", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/service/{companyId}")
    public ResponseEntity<Object> getAllServicesByCompanyId(@PathVariable String companyId) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(companyId, "CompanyId");
            List<Service> responseService = serviceRepository.findByServiceKeyCompanyId(companyId);

            responseEntity = new ResponseEntity<Object>(responseService, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get service by Company Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
