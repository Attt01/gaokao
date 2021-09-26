package com.gaokao.common.service;

import com.gaokao.common.utils.SpiderHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author attack204
 * date:  2021/9/24
 * email: 757394026@qq.com
 */
@Slf4j
@Service
public class SpiderServiceImpl implements SpiderService{

    @Override
    public Long startSpiderMission() {
        try {
            String data = SpiderHttpClientUtil.doPost("http://123.60.99.51/index", "null");
        } catch (Exception e) {
            return -1L;
        }
        return 1L;
    }
}
