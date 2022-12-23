package com.app.kokonut.refactor.addressBook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/AddressBook")
public class AddressBookRestController {

    @Value("${kokonut.mail.host}")
    public String hostUrl;

    private final AddressBookService addressBookService;

    @Autowired
    public AddressBookRestController(AddressBookService addressBookService){
        this.addressBookService = addressBookService;
    }





}
