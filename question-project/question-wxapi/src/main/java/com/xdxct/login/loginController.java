package com.xdxct.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.xdxct.utils.ResultUtils;
import com.xdxct.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姜瑜欣
 * @description: TODO
 * @date 2022/1/162:48
 */
@RestController
@RequestMapping("/wxapi/login")
public class loginController {

    @RequestMapping("/wxLogin")
    public ResultVo wxLogin(@RequestParam("code") String code){
        Map<String,String> map = new HashMap<>();

        map.put("appid","wx81a55519046b458c");
        map.put("secret","fffb155fdd1ad2b080f4927bd8fac858");
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        //发送请求到微信服务获取openid
        String body = HttpRequest.get("https://api.weixin.qq.com/sns/jscode2session").form(map).body();
        JSONObject obj = JSON.parseObject(body);
        return ResultUtils.success("获取成功！",obj);

    }
}
