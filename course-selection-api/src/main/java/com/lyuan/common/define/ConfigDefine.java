package com.lyuan.common.define;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigDefine {
    @Value("${imageSavePath}")
    private String imageSavePath;

    @Value("${imageVisitPath}")
    private String imageVisitPath;

    @Value("${ip}")
    private String ip;
}
