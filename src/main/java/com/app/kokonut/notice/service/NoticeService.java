package com.app.kokonut.notice.service;

import com.app.kokonut.notice.dto.NoticeDTO;
import com.app.kokonut.notice.entity.Notice;
import com.app.kokonut.notice.repository.NoticeRepository;
import com.app.kokonut.notice.vo.NoticeQueryVO;
import com.app.kokonut.notice.vo.NoticeUpdateVO;
import com.app.kokonut.notice.vo.NoticeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Integer save(NoticeVO vO) {
        Notice bean = new Notice();
        BeanUtils.copyProperties(vO, bean);
        bean = noticeRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        noticeRepository.deleteById(id);
    }

    public void update(Integer id, NoticeUpdateVO vO) {
        Notice bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        noticeRepository.save(bean);
    }

    public NoticeDTO getById(Integer id) {
        Notice original = requireOne(id);
        return toDTO(original);
    }

    public Page<NoticeDTO> query(NoticeQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private NoticeDTO toDTO(Notice original) {
        NoticeDTO bean = new NoticeDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Notice requireOne(Integer id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
