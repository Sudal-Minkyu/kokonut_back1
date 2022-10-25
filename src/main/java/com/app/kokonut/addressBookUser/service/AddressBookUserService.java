package com.app.kokonut.addressBookUser.service;

import com.app.kokonut.addressBookUser.dto.AddressBookUserDTO;
import com.app.kokonut.addressBookUser.entity.AddressBookUser;
import com.app.kokonut.addressBookUser.repository.AddressBookUserRepository;
import com.app.kokonut.addressBookUser.vo.AddressBookUserQueryVO;
import com.app.kokonut.addressBookUser.vo.AddressBookUserUpdateVO;
import com.app.kokonut.addressBookUser.vo.AddressBookUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AddressBookUserService {

    @Autowired
    private AddressBookUserRepository addressBookUserRepository;

    public Integer save(AddressBookUserVO vO) {
        AddressBookUser bean = new AddressBookUser();
        BeanUtils.copyProperties(vO, bean);
        bean = addressBookUserRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        addressBookUserRepository.deleteById(id);
    }

    public void update(Integer id, AddressBookUserUpdateVO vO) {
        AddressBookUser bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        addressBookUserRepository.save(bean);
    }

    public AddressBookUserDTO getById(Integer id) {
        AddressBookUser original = requireOne(id);
        return toDTO(original);
    }

    public Page<AddressBookUserDTO> query(AddressBookUserQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AddressBookUserDTO toDTO(AddressBookUser original) {
        AddressBookUserDTO bean = new AddressBookUserDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AddressBookUser requireOne(Integer id) {
        return addressBookUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
