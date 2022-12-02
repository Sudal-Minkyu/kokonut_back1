package com.app.kokonut.refactor.alimtalkMessageRecipient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlimtalkMessageRecipientService {

    private final AlimtalkMessageRecipientRepository alimtalkMessageRecipientRepository;

    @Autowired
    public AlimtalkMessageRecipientService(AlimtalkMessageRecipientRepository alimtalkMessageRecipientRepository) {
        this.alimtalkMessageRecipientRepository = alimtalkMessageRecipientRepository;
    }


}
