package com.app.kokonut.addressBookUser.repository;

import com.app.kokonut.addressBookUser.entity.AddressBookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressBookUserRepository extends JpaRepository<AddressBookUser, Integer>, JpaSpecificationExecutor<AddressBookUser> {

}