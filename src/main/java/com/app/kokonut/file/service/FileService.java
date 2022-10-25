package com.app.kokonut.file.service;

import com.app.kokonut.file.dto.FileDTO;
import com.app.kokonut.file.entity.File;
import com.app.kokonut.file.repository.FileRepository;
import com.app.kokonut.file.vo.FileQueryVO;
import com.app.kokonut.file.vo.FileUpdateVO;
import com.app.kokonut.file.vo.FileVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public Integer save(FileVO vO) {
        File bean = new File();
        BeanUtils.copyProperties(vO, bean);
        bean = fileRepository.save(bean);
        return bean.getIdx();
    }

    public void delete(Integer id) {
        fileRepository.deleteById(id);
    }

    public void update(Integer id, FileUpdateVO vO) {
        File bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        fileRepository.save(bean);
    }

    public FileDTO getById(Integer id) {
        File original = requireOne(id);
        return toDTO(original);
    }

    public Page<FileDTO> query(FileQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private FileDTO toDTO(File original) {
        FileDTO bean = new FileDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private File requireOne(Integer id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
