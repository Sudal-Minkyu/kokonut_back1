package com.app.kokonut.downloadHistory.service;

import com.app.kokonut.downloadHistory.dto.DownloadHistoryDTO;
import com.app.kokonut.downloadHistory.entity.DownloadHistory;
import com.app.kokonut.downloadHistory.repository.DownloadHistoryRepository;
import com.app.kokonut.downloadHistory.vo.DownloadHistoryQueryVO;
import com.app.kokonut.downloadHistory.vo.DownloadHistoryUpdateVO;
import com.app.kokonut.downloadHistory.vo.DownloadHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DownloadHistoryService {

    @Autowired
    private DownloadHistoryRepository downloadHistoryRepository;

    public Integer save(DownloadHistoryVO vO) {
        DownloadHistory bean = new DownloadHistory();
        BeanUtils.copyProperties(vO, bean);
        bean = downloadHistoryRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        downloadHistoryRepository.deleteById(id);
    }

    public void update(Integer id, DownloadHistoryUpdateVO vO) {
        DownloadHistory bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        downloadHistoryRepository.save(bean);
    }

    public DownloadHistoryDTO getById(Integer id) {
        DownloadHistory original = requireOne(id);
        return toDTO(original);
    }

    public Page<DownloadHistoryDTO> query(DownloadHistoryQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private DownloadHistoryDTO toDTO(DownloadHistory original) {
        DownloadHistoryDTO bean = new DownloadHistoryDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private DownloadHistory requireOne(Integer id) {
        return downloadHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
