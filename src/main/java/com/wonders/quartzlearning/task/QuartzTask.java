package com.wonders.quartzlearning.task;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ExpanseWong
 */
@Service
@SuppressWarnings("unused")
public class QuartzTask {
    /***
     * 业务处理逻辑
     */
    public void reptilian(){
        System.out.println("执行业务逻辑："+new Date());
    }
}
