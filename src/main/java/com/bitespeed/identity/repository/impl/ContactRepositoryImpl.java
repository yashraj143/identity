package com.bitespeed.identity.repository.impl;

import com.bitespeed.identity.entity.Contact;
import com.bitespeed.identity.enums.LinkPrecedence;
import com.bitespeed.identity.repository.ContactRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ContactRepositoryImpl implements ContactRepository {
    //Using in-memory data store for production we can replace with database
    private HashMap<Integer, Contact> contacts;
    private Integer nextId = 1;

    public ContactRepositoryImpl(){contacts = new HashMap<>();};

    @Override
    public Contact createContact(Contact contact){
        contacts.put(nextId,contact);
        contact.setId(nextId);
        nextId++;
        return contact;
    }


    @Override
    public Contact findPrimaryContactByEmail(String email) {
        for (Contact contact : contacts.values()) {
            if (email != null && email.equals(contact.getEmail())) {
                if(LinkPrecedence.PRIMARY.equals(contact.getLinkPrecedence())){
                    return contact;
                }
                Integer primaryId  = contact.getLinkedId();
                return getContactById(primaryId);
            }
        }
        return null;
    }

    @Override
    public Contact findPrimaryContactByPhoneNumber(String phoneNumber) {
        for (Contact contact : contacts.values()) {
            if (phoneNumber != null && phoneNumber.equals(contact.getPhoneNumber())) {
                if(LinkPrecedence.PRIMARY.equals(contact.getLinkPrecedence())){
                    return contact;
                }
                Integer primaryId  = contact.getLinkedId();
                return getContactById(primaryId);
            }
        }
        return null;
    }

    @Override
    public Contact getContactById(Integer id){
        return contacts.get(id);
    }

    @Override
    public Contact updateContactById(Integer id, Contact contact){
        return contacts.put(id,contact);
    }
    @Override
    public List<Contact> getSecondaryContactsByPrimaryId(Integer primaryId){
        List<Contact> contactList = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            if (!Objects.isNull(contact.getLinkedId()) && contact.getLinkedId().equals(primaryId)) {
                contactList.add(contact);
            }
        }
        return contactList;
    }

    @Override
    public List<Contact> getAllContact(){
        List<Contact> contactList = new ArrayList<>();
        for (Contact contact : contacts.values()) {
            contactList.add(contact);
        }
        return contactList;
    }

}
