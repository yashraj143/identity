package com.bitespeed.identity.repository;

import com.bitespeed.identity.entity.Contact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository {
    Contact createContact(Contact contact);
    Contact findPrimaryContactByEmail(String email);
    Contact findPrimaryContactByPhoneNumber(String phoneNumber);

    Contact getContactById(Integer primaryId);
    List<Contact> getSecondaryContactsByPrimaryId(Integer primaryId);
    Contact updateContactById(Integer id, Contact contact);

    List<Contact> getAllContact();
}
