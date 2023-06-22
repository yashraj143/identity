package com.bitespeed.identity.service;

import com.bitespeed.identity.entity.Contact;
import com.bitespeed.identity.model.ContactRequest;
import com.bitespeed.identity.model.ContactResponse;

import java.util.List;

public interface ContactService {
    ContactResponse identifyContact(ContactRequest contactRequest);
    List<Contact> getContacts();
}
