package com.app.kokonut.refactor.kakaoChannel.service;

import com.app.kokonut.refactor.kakaoChannel.dto.KakaoChannelDTO;
import com.app.kokonut.refactor.kakaoChannel.entity.KakaoChannel;
import com.app.kokonut.refactor.kakaoChannel.repository.KakaoChannelRepository;
import com.app.kokonut.refactor.kakaoChannel.vo.KakaoChannelQueryVO;
import com.app.kokonut.refactor.kakaoChannel.vo.KakaoChannelUpdateVO;
import com.app.kokonut.refactor.kakaoChannel.vo.KakaoChannelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class KakaoChannelService {

    @Autowired
    private KakaoChannelRepository kakaoChannelRepository;

    public Integer save(KakaoChannelVO vO) {
        KakaoChannel bean = new KakaoChannel();
        BeanUtils.copyProperties(vO, bean);
        bean = kakaoChannelRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        kakaoChannelRepository.deleteById(id);
    }

    public void update(Integer id, KakaoChannelUpdateVO vO) {
        KakaoChannel bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        kakaoChannelRepository.save(bean);
    }

    public KakaoChannelDTO getById(Integer id) {
        KakaoChannel original = requireOne(id);
        return toDTO(original);
    }

    public Page<KakaoChannelDTO> query(KakaoChannelQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private KakaoChannelDTO toDTO(KakaoChannel original) {
        KakaoChannelDTO bean = new KakaoChannelDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private KakaoChannel requireOne(Integer id) {
        return kakaoChannelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
