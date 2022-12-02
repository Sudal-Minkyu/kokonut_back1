package com.app.kokonut.refactor.setting.repository;

import com.app.kokonut.refactor.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettingRepository extends JpaRepository<Setting, Integer>, JpaSpecificationExecutor<Setting> {

}