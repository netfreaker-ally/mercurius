package com.Mercurius.Bridge.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mercurius.Bridge.entity.AccountRepresentation;
import com.Mercurius.Bridge.entity.ProductRepresentation;

@FeignClient("account-service")
public interface AccountManagementClient {

    @PostMapping("/accounts/create")
    ResponseEntity<AccountRepresentation> createAccount(@RequestBody AccountRepresentation account);

    @GetMapping("/accounts/{id}")
    ResponseEntity<AccountRepresentation> getAccountById(@PathVariable("id") String accountId);

    @PutMapping("/accounts/{id}")
    ResponseEntity<AccountRepresentation> updateAccount(@PathVariable("id") String accountId, @RequestBody AccountRepresentation account);

    @DeleteMapping("/accounts/{id}")
    ResponseEntity<String> deleteAccount(@PathVariable("id") String accountId);

}
