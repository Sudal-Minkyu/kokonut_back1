package com.app.kokonut.friendtalkMessage.service;

import com.app.kokonut.friendtalkMessage.dto.FriendtalkMessageDTO;
import com.app.kokonut.friendtalkMessage.entity.FriendtalkMessage;
import com.app.kokonut.friendtalkMessage.repository.FriendtalkMessageRepository;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageQueryVO;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageUpdateVO;
import com.app.kokonut.friendtalkMessage.vo.FriendtalkMessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FriendtalkMessageService {

    @Autowired
    private FriendtalkMessageRepository friendtalkMessageRepository;

    public Integer save(FriendtalkMessageVO vO) {
        FriendtalkMessage bean = new FriendtalkMessage();
        BeanUtils.copyProperties(vO, bean);
        bean = friendtalkMessageRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        friendtalkMessageRepository.deleteById(id);
    }

    public void update(Integer id, FriendtalkMessageUpdateVO vO) {
        FriendtalkMessage bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        friendtalkMessageRepository.save(bean);
    }

    public FriendtalkMessageDTO getById(Integer id) {
        FriendtalkMessage original = requireOne(id);
        return toDTO(original);
    }

    public Page<FriendtalkMessageDTO> query(FriendtalkMessageQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private FriendtalkMessageDTO toDTO(FriendtalkMessage original) {
        FriendtalkMessageDTO bean = new FriendtalkMessageDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private FriendtalkMessage requireOne(Integer id) {
        return friendtalkMessageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
