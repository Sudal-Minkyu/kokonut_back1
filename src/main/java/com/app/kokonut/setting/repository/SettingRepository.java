package com.app.kokonut.setting.repository;

import com.app.kokonut.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettingRepository extends JpaRepository<Setting, Integer>, JpaSpecificationExecutor<Setting> {

}