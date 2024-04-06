package com.linmoblog.server.Controller;

import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Entity.Social;
import com.linmoblog.server.Entity.WebInfo;
import com.linmoblog.server.Service.WebInfoService;
import com.linmoblog.server.aspect.ApiOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "网站设置")
public class WebInfoController {
    @Autowired
    private WebInfoService webInfoService;

    @ApiOperationLog(description = "修改设置")
    @Operation(summary = "修改设置")
    @PostMapping("/api/protected/websetting")
    public Result<Null> updateWebInfo(@RequestBody WebInfo webInfo){
        return webInfoService.updateWebInfo(webInfo);
    }

    @ApiOperationLog(description = "获取设置")
    @Operation(summary = "获取设置")
    @GetMapping("/api/protected/websetting")
    public Result<WebInfo> getWebInfo(){
        return webInfoService.getWebInfo();
    }

    @ApiOperationLog(description = "获取社交")
    @Operation(summary = "获取社交")
    @GetMapping("/api/public/social")
    public Result<Social> getSocial(){
        return webInfoService.getSocial();
    }
}
