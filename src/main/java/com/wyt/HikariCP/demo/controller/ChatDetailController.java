package com.wyt.HikariCP.demo.controller;

import com.wyt.HikariCP.demo.mapper.ChatDetailMapper;
import com.wyt.HikariCP.demo.model.ChatDetail;
import com.wyt.HikariCP.demo.multi.MultiDsPoolContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangyitao on 2019/3/27.
 */
@RestController
@RequestMapping("/cdc")
public class ChatDetailController {

    @Autowired
    private ChatDetailMapper chatDetailMapper;

    @Resource
    private MultiDsPoolContext context;

    @RequestMapping("/query")
    @ResponseBody
    public String query(long companyId) {
        long start = System.currentTimeMillis();
        ChatDetail detail = new ChatDetail();
        detail.setId(companyId);
        detail = chatDetailMapper.selectById(detail);
        context.setDataSourcePool("test");
        detail = chatDetailMapper.selectById(detail);
        long end = System.currentTimeMillis();
        return String.valueOf(detail.toString());
    }

    @RequestMapping("save")
    @ResponseBody
    @Transactional
    public String save(){
        ChatDetail detail = new ChatDetail();
        detail.setAge(20);
        detail.setName("12312");
        chatDetailMapper.insert(detail);
        if (true==true){
            detail.setId(null);
            chatDetailMapper.insert(detail);
//            throw new RuntimeException("1");
        }
        return "ok";
    }
}
