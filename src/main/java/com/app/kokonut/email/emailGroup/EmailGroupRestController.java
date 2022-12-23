package com.app.kokonut.email.emailGroup;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/emailGroup")
public class EmailGroupRestController {
    private final EmailGroupService emailGroupService;

    public EmailGroupRestController(EmailGroupService emailGroupService) {
        this.emailGroupService = emailGroupService;
    }

}
