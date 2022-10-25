package com.app.kokonut.friendtalkMessage.repository;

import com.app.kokonut.friendtalkMessage.entity.FriendtalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FriendtalkMessageRepository extends JpaRepository<FriendtalkMessage, Integer>, JpaSpecificationExecutor<FriendtalkMessage> {

}