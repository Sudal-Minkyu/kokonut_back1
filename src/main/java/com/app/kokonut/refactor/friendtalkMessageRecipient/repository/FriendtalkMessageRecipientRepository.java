package com.app.kokonut.refactor.friendtalkMessageRecipient.repository;

import com.app.kokonut.refactor.friendtalkMessageRecipient.entity.FriendtalkMessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FriendtalkMessageRecipientRepository extends JpaRepository<FriendtalkMessageRecipient, Integer>, JpaSpecificationExecutor<FriendtalkMessageRecipient> {

}