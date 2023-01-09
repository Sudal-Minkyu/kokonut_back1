package com.app.kokonut.setting;
import com.app.kokonut.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Joy
 * Date : 2022-01-05
 * Time :
 * Remark : SettingRepository 관리자 환경설정
 */
@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer>, JpaSpecificationExecutor<Setting>, SettingRepositoryCustom {

}