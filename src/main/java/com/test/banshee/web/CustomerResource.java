package com.test.banshee.web;

import com.test.banshee.dto.customer.CreateCustomerDTO;
import com.test.banshee.dto.customer.CustomerSummaryDTO;
import com.test.banshee.dto.customer.GetCustomerDTO;
import com.test.banshee.dto.customer.UpdateCustomerDTO;
import com.test.banshee.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<GetCustomerDTO> getCustomer(@PathVariable("customerId") final long customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerSummaryDTO>> getCustomers(
            @RequestParam(defaultValue = "0", required = false) final int page,
            @RequestParam(defaultValue = "10", required = false) final int pageSize) {
        final Page<CustomerSummaryDTO> customerDTOPage = customerService.getCustomers(page, pageSize);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("total-pages", String.valueOf(customerDTOPage.getTotalPages()));
        return new ResponseEntity<>(customerDTOPage.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody final CreateCustomerDTO createCustomerDTO) {
        final long customerId = customerService.createCustomer(createCustomerDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerId)
                .toUri();
        return ResponseEntity.created(uri).build();
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
