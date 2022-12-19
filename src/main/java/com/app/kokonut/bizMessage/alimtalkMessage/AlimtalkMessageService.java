package com.app.kokonut.bizMessage.alimtalkMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlimtalkMessageService {

    private final AlimtalkMessageRepository alimtalkMessageRepository;

    @Autowired
    public AlimtalkMessageService(AlimtalkMessageRepository alimtalkMessageRepository) {
        this.alimtalkMessageRepository = alimtalkMessageRepository;
    }

}
