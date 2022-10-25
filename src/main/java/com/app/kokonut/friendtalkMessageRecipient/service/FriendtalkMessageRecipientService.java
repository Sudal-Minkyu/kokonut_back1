package com.app.kokonut.friendtalkMessageRecipient.service;

import com.app.kokonut.friendtalkMessageRecipient.dto.FriendtalkMessageRecipientDTO;
import com.app.kokonut.friendtalkMessageRecipient.entity.FriendtalkMessageRecipient;
import com.app.kokonut.friendtalkMessageRecipient.repository.FriendtalkMessageRecipientRepository;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientQueryVO;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientUpdateVO;
import com.app.kokonut.friendtalkMessageRecipient.vo.FriendtalkMessageRecipientVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FriendtalkMessageRecipientService {

    @Autowired
    private FriendtalkMessageRecipientRepository friendtalkMessageRecipientRepository;

    public Integer save(FriendtalkMessageRecipientVO vO) {
        FriendtalkMessageRecipient bean = new FriendtalkMessageRecipient();
        BeanUtils.copyProperties(vO, bean);
        bean = friendtalkMessageRecipientRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        friendtalkMessageRecipientRepository.deleteById(id);
    }

    public void update(Integer id, FriendtalkMessageRecipientUpdateVO vO) {
        FriendtalkMessageRecipient bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        friendtalkMessageRecipientRepository.save(bean);
    }

    public FriendtalkMessageRecipientDTO getById(Integer id) {
        FriendtalkMessageRecipient original = requireOne(id);
        return toDTO(original);
    }

    public Page<FriendtalkMessageRecipientDTO> query(FriendtalkMessageRecipientQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private FriendtalkMessageRecipientDTO toDTO(FriendtalkMessageRecipient original) {
        FriendtalkMessageRecipientDTO bean = new FriendtalkMessageRecipientDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private FriendtalkMessageRecipient requireOne(Integer id) {
        return friendtalkMessageRecipientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
