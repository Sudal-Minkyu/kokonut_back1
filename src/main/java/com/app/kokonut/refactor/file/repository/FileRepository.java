package com.app.kokonut.refactor.file.repository;

import com.app.kokonut.refactor.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<File, Integer>, JpaSpecificationExecutor<File> {

}