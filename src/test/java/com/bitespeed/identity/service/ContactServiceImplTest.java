package com.bitespeed.identity.service;

import com.bitespeed.identity.entity.Contact;
import com.bitespeed.identity.enums.LinkPrecedence;
import com.bitespeed.identity.model.ContactRequest;
import com.bitespeed.identity.model.ContactResponse;
import com.bitespeed.identity.repository.ContactRepository;
import com.bitespeed.identity.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // adding one simeple test case for check validity
    @Test
    void identifyContact_WithValidRequest_ReturnsContactResponse() {
        // Arrange
        String email = "test@example.com";
        String phoneNumber = "1234567890";

        ContactRequest contactRequest = new ContactRequest(email, phoneNumber);

        Contact primaryContact = Contact.builder().id(1).email(email).phoneNumber(phoneNumber).linkPrecedence(LinkPrecedence.PRIMARY).build();


        when(contactRepository.createContact(any())).thenReturn(primaryContact);
        when(contactRepository.getContactById(1)).thenReturn(primaryContact);

        // Act
        ContactResponse response = contactService.identifyContact(contactRequest);

        // Assert
        assertNotNull(response);
        assertEquals(primaryContact.getId(), response.getPrimaryContactId());
        assertEquals(Arrays.asList(email), response.getEmails());
        assertEquals(Arrays.asList(phoneNumber), response.getPhoneNumbers());

        verify(contactRepository, times(1)).createContact(any());
    }

}
