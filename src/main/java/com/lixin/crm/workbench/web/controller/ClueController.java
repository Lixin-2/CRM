package com.lixin.crm.workbench.web.controller;

import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService = null;

}
