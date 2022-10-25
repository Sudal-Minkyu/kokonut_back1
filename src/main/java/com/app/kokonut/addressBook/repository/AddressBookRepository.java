package com.app.kokonut.addressBook.repository;

import com.app.kokonut.addressBook.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressBookRepository extends JpaRepository<AddressBook, Integer>, JpaSpecificationExecutor<AddressBook> {

}