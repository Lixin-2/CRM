package com.lixin.crm.settings.web.controller;

import com.lixin.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/dic")
public class DicController {

    @Autowired
    private DicService dicService = null;

}
