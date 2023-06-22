package com.bitespeed.identity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private Integer primaryContactId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Integer> secondaryContactIds;
}

