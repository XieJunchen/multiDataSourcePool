package com.wyt.HikariCP.demo.controller;

import com.wyt.HikariCP.demo.mapper.ChatDetailMapper;
import com.wyt.HikariCP.demo.model.ChatDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyitao on 2019/3/27.
 */
@RestController
@RequestMapping("/cdc")
public class ChatDetailController {

    @Autowired
    private ChatDetailMapper chatDetailMapper;

    @RequestMapping("/query")
    @ResponseBody
    public String query(long companyId) {
        long start = System.currentTimeMillis();
        ChatDetail detail = new ChatDetail();
        detail.setId(companyId);
        detail = chatDetailMapper.selectById(detail);
        long end = System.currentTimeMillis();
        return String.valueOf(detail.getName());
    }
}
