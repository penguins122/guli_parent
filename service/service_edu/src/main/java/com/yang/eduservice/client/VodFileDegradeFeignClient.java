package com.yang.eduservice.client;

import com.yang.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: yscong
 * @create: 2022/7/5 19:57
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除失败");
    }

    @Override
    public R delVideo(List<String> videoIdList) {
        return R.error().message("删除失败");

    }
}
