package com.test.banshee.web;

import com.test.banshee.service.CustomerService;
import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<GetCustomerDTO>> getCustomers(
            @RequestParam(defaultValue = "0", required = false) final int page,
            @RequestParam(defaultValue = "5", required = false) final int pageSize) {
        final Page<GetCustomerDTO> customerDTOPage = customerService.getCustomers(page, pageSize);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("total-pages", String.valueOf(customerDTOPage.getTotalPages()));
        return new ResponseEntity<>(customerDTOPage.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody final CreateCustomerDTO createCustomerDTO)
            throws URISyntaxException {
        final long customerId = customerService.createCustomer(createCustomerDTO);
        return ResponseEntity.created(new URI("/api/customers/" + customerId)).build();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomer(
            @PathVariable("customerId") final long customerId,
            @Valid @RequestBody final UpdateCustomerDTO updateCustomerDTO) {
        customerService.updateCustomer(customerId, updateCustomerDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") final long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
