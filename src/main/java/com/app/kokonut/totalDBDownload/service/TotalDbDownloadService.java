package com.app.kokonut.totalDBDownload.service;

import com.app.kokonut.totalDBDownload.dto.TotalDbDownloadDTO;
import com.app.kokonut.totalDBDownload.entity.TotalDbDownload;
import com.app.kokonut.totalDBDownload.repository.TotalDbDownloadRepository;
import com.app.kokonut.totalDBDownload.vo.TotalDbDownloadQueryVO;
import com.app.kokonut.totalDBDownload.vo.TotalDbDownloadUpdateVO;
import com.app.kokonut.totalDBDownload.vo.TotalDbDownloadVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TotalDbDownloadService {

    @Autowired
    private TotalDbDownloadRepository totalDbDownloadRepository;

    public Integer save(TotalDbDownloadVO vO) {
        TotalDbDownload bean = new TotalDbDownload();
        BeanUtils.copyProperties(vO, bean);
        bean = totalDbDownloadRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        totalDbDownloadRepository.deleteById(id);
    }

    public void update(Integer id, TotalDbDownloadUpdateVO vO) {
        TotalDbDownload bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        totalDbDownloadRepository.save(bean);
    }

    public TotalDbDownloadDTO getById(Integer id) {
        TotalDbDownload original = requireOne(id);
        return toDTO(original);
    }

    public Page<TotalDbDownloadDTO> query(TotalDbDownloadQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private TotalDbDownloadDTO toDTO(TotalDbDownload original) {
        TotalDbDownloadDTO bean = new TotalDbDownloadDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private TotalDbDownload requireOne(Integer id) {
        return totalDbDownloadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
