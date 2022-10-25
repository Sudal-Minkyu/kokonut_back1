package com.app.kokonut.kakaoChannel.repository;

import com.app.kokonut.kakaoChannel.entity.KakaoChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KakaoChannelRepository extends JpaRepository<KakaoChannel, Integer>, JpaSpecificationExecutor<KakaoChannel> {

}