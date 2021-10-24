package com.gaokao.controller.order;

import com.gaokao.common.config.WxPayProperties;
import com.gaokao.common.meta.po.Order;
import com.gaokao.common.service.OrderService;
import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/xhr/v1/users/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private WxPayProperties wxPayProperties;

    /**
     * 下单接口
     * @param request  用户信息
     * @return
     * @throws Exception
     */
    @GetMapping("/saveOrder")
    public void saveOrder(HttpServletRequest request,
                          HttpServletResponse response, @RequestParam Long userId) throws Exception {
        Order order = new Order();
        order.setUserId(userId);

        //统一下单拿支付交易链接codeUrl
        String codeUrl = orderService.saveOrder(userId);//orderService.saveOrder(userId);
        if(codeUrl == null){
            throw new NullPointerException();
        }

        try {
            //生成二维码配置
            Map<EncodeHintType,Object> hints = new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            //设置编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            //构造图片对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,hints);
            //输出流
            OutputStream out = response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
