package com.linmoblog.server.Controller;

import com.linmoblog.server.Entity.Talk;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.TalkService;
import com.linmoblog.server.aspect.ApiOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api")
@RestController
@Tag(name = "说说接口")
public class TalkController {
    @Autowired
    TalkService talkService;

    @ApiOperationLog(description = "添加说说")
    @Operation(summary = "添加说说")
    @PostMapping("/protect/talk")
    public Result<Null> addComment(@RequestBody Talk talk) {
        System.out.println(talk.toString());
        return talkService.addTalk(talk);
    }

    @ApiOperationLog(description = "获取说说")
    @Operation(summary = "获取说说")
    @GetMapping("/public/talk")
    public Result<List<Talk>> getTalkList() {
        return talkService.getTalkList();
    }
    @ApiOperationLog(description = "删除说说")
    @Operation(summary = "删除说说")
    @DeleteMapping("/protect/talk/{id}")
    public Result<Null> delTalk(@PathVariable Integer id){
        return talkService.del(id);
    }

    @ApiOperationLog(description = "修改说说")
    @Operation(summary = "修改说说")
    @PostMapping("/protect/talk/{id}")
    public Result<Null> updateTalk(@PathVariable Integer id,@RequestBody Talk talk){
        System.out.println(id + "  " + talk.toString());
        return talkService.updateTalk(id,talk);
    }
}
