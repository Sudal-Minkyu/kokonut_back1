package com.app.kokonut.addressBook.service;

import com.app.kokonut.addressBook.dto.AddressBookDTO;
import com.app.kokonut.addressBook.entity.AddressBook;
import com.app.kokonut.addressBook.repository.AddressBookRepository;
import com.app.kokonut.addressBook.vo.AddressBookQueryVO;
import com.app.kokonut.addressBook.vo.AddressBookUpdateVO;
import com.app.kokonut.addressBook.vo.AddressBookVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    public Integer save(AddressBookVO vO) {
        AddressBook bean = new AddressBook();
        BeanUtils.copyProperties(vO, bean);
        bean = addressBookRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        addressBookRepository.deleteById(id);
    }

    public void update(Integer id, AddressBookUpdateVO vO) {
        AddressBook bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        addressBookRepository.save(bean);
    }

    public AddressBookDTO getById(Integer id) {
        AddressBook original = requireOne(id);
        return toDTO(original);
    }

    public Page<AddressBookDTO> query(AddressBookQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AddressBookDTO toDTO(AddressBook original) {
        AddressBookDTO bean = new AddressBookDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AddressBook requireOne(Integer id) {
        return addressBookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
