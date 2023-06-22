package com.bitespeed.identity.controller;

import com.bitespeed.identity.entity.Contact;
import com.bitespeed.identity.model.ContactRequest;
import com.bitespeed.identity.model.ContactResponse;
import com.bitespeed.identity.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;
    @PostMapping("/identify")
    public ResponseEntity<ContactResponse> identifyContact(@RequestBody ContactRequest contactRequest) {
        ContactResponse response = contactService.identifyContact(contactRequest);
        return ResponseEntity.ok(response);
    }

    // using in memory data so to check contact data
    @GetMapping("/state")
    public ResponseEntity<List<Contact>> identifyContact() {
        return ResponseEntity.ok(contactService.getContacts());
    }
}
