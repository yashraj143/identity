package com.bitespeed.identity.service.impl;

import com.bitespeed.identity.entity.Contact;
import com.bitespeed.identity.enums.LinkPrecedence;
import com.bitespeed.identity.model.ContactRequest;
import com.bitespeed.identity.model.ContactResponse;
import com.bitespeed.identity.repository.ContactRepository;
import com.bitespeed.identity.repository.impl.ContactRepositoryImpl;
import com.bitespeed.identity.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContactServiceImpl implements ContactService {


    ContactRepository contactRepository = new ContactRepositoryImpl();
    @Override
    public ContactResponse identifyContact(ContactRequest contactRequest) {
        String email = contactRequest.getEmail();
        String phoneNumber = contactRequest.getPhoneNumber();

        if(email.isEmpty()) email=null;
        if(phoneNumber.isEmpty()) phoneNumber=null;

        if(Objects.isNull(email) && Objects.isNull(phoneNumber)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Either email or phonenumber is needed"
            );
        }

        Contact contactByEmail = contactRepository.findPrimaryContactByEmail(email);
        Contact contactByPhone = contactRepository.findPrimaryContactByPhoneNumber(phoneNumber);

        // if both null then means new data need to create
        if(Objects.isNull(contactByEmail) && Objects.isNull(contactByPhone)){
            Contact crateContact = Contact.builder().email(email).phoneNumber(phoneNumber)
                    .linkPrecedence(LinkPrecedence.PRIMARY).build();
            Contact createdContact  = contactRepository.createContact(crateContact);
            return createContactResponse(createdContact.getId());
        }
        // when email is provided and phoneNumber is null
        if(Objects.nonNull(contactByEmail) && Objects.isNull(contactByPhone)){
            // upate existing as it's phonenumber is missing
            if(Objects.nonNull(phoneNumber) && Objects.isNull(contactByEmail.getPhoneNumber())){
                contactByEmail.setPhoneNumber(phoneNumber);
                contactRepository.updateContactById(contactByEmail.getId(),contactByEmail);
            }
            else if(Objects.nonNull(phoneNumber)){
                Contact crateContact = Contact.builder().email(email).phoneNumber(phoneNumber)
                        .linkPrecedence(LinkPrecedence.SECONDARY).linkedId(contactByEmail.getId()).build();
                contactRepository.createContact(crateContact);
            }
            return createContactResponse(contactByEmail.getId());
        }
        // when email is null and phoneNumber is provided
        if(Objects.isNull(contactByEmail) && Objects.nonNull(contactByPhone)){
            // upate existing as it's email is missing
            if(Objects.nonNull(email) && Objects.isNull(contactByPhone.getEmail())){
                contactByPhone.setEmail(email);
                contactRepository.updateContactById(contactByPhone.getId(),contactByPhone);
            }
            else if(Objects.nonNull(email)){
                Contact crateContact = Contact.builder().email(email).phoneNumber(phoneNumber)
                        .linkPrecedence(LinkPrecedence.SECONDARY).linkedId(contactByPhone.getId()).build();
                contactRepository.createContact(crateContact);
            }
            return createContactResponse(contactByPhone.getId());

        }
        // if both are not null

        //if both having same id return data
        if(contactByEmail.getId() == contactByPhone.getId()){
            return createContactResponse(contactByPhone.getId());
        }
        // if both are primary then convert newer one into secondary
        // make newerId secondary with updated value;
        Contact needToUpdate = contactByEmail.getId()>contactByPhone.getId()?contactByEmail:contactByPhone;
        Integer primaryId = contactByEmail.getId()>contactByPhone.getId()?contactByPhone.getId():contactByEmail.getId();

        needToUpdate.setEmail(email);
        needToUpdate.setPhoneNumber(phoneNumber);
        needToUpdate.setLinkPrecedence(LinkPrecedence.SECONDARY);
        needToUpdate.setLinkedId(primaryId);
        contactRepository.updateContactById(needToUpdate.getId(),needToUpdate);

        return createContactResponse(primaryId);

    }

    private ContactResponse createContactResponse(Integer primaryId){
        LinkedHashSet<String> emails = new LinkedHashSet<>();
        LinkedHashSet<String> phoneNumbers = new LinkedHashSet<>();
        LinkedHashSet<Integer> secondaryContactIds = new LinkedHashSet<>();

        Contact primaryContact = contactRepository.getContactById(primaryId);
        emails.add(primaryContact.getEmail());
        phoneNumbers.add(primaryContact.getPhoneNumber());

        List<Contact> secondaryContact = contactRepository.getSecondaryContactsByPrimaryId(primaryId);

        emails.addAll(secondaryContact.stream().map(Contact::getEmail).collect(Collectors.toList()));
        phoneNumbers.addAll(secondaryContact.stream().map(Contact::getPhoneNumber).collect(Collectors.toList()));
        secondaryContactIds.addAll(secondaryContact.stream().map(Contact::getId).collect(Collectors.toList()));

        return ContactResponse.builder()
                .primaryContactId(primaryId)
                .emails(new ArrayList<>(emails))
                .phoneNumbers(new ArrayList<>(phoneNumbers))
                .secondaryContactIds(new ArrayList<>(secondaryContactIds))
                .build();


    }

    @Override
    public List<Contact> getContacts(){
        return contactRepository.getAllContact();
    }
}
