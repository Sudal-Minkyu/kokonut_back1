package com.app.kokonut.friendtalkMessageRecipient.repository;

import com.app.kokonut.friendtalkMessageRecipient.entity.FriendtalkMessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FriendtalkMessageRecipientRepository extends JpaRepository<FriendtalkMessageRecipient, Integer>, JpaSpecificationExecutor<FriendtalkMessageRecipient> {

}