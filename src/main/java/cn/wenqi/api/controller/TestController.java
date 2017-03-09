package cn.wenqi.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wenqi
 */
@Controller
@RequestMapping("/test")
@PreAuthorize("hasRole('ADMIN')")
public class TestController {

    @RequestMapping("/key")
    @ResponseBody
    public String key(){
        return "success";
    }
}
