package com.app.kokonut.bizMessage.kakaoChannel;

import com.app.kokonut.bizMessage.kakaoChannel.entity.KakaoChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KakaoChannelRepository extends JpaRepository<KakaoChannel, Integer>, JpaSpecificationExecutor<KakaoChannel>, KakaoChannelRepositoryCustom {

    @Transactional
    @Modifying
    @Query("delete from KakaoChannel a where a.channelId = :channelId")
    void findByKakaoChannelDelete(String channelId);

}