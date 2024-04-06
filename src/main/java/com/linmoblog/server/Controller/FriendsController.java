package com.linmoblog.server.Controller;

import com.linmoblog.server.Entity.Friend;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.FriendsService;
import com.linmoblog.server.aspect.ApiOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Tag(name = "友情链接")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    @ApiOperationLog(description = "获取所有友情链接")
    @Operation(summary ="获取所有友情链接")
    @GetMapping("/public/friends")
    public Result<List<Friend>> getFriends() {
        return friendsService.getFriendsList();
    }

    @ApiOperationLog(description = "添加友情链接")
    @Operation(summary ="添加友情链接")
    @PostMapping("/public/friends")
    public Result<Null> addFriends(@RequestBody Friend friend) {
        return friendsService.addFriends(friend);
    }

    @ApiOperationLog(description = "修改友情链接")
    @Operation(summary = "修改友情链接")
    @PostMapping("/protected/friends/{friendKey}")
    public Result<Null> updateFriend(@PathVariable int friendKey) {
        return friendsService.updateFriend(friendKey);
    }

    @ApiOperationLog(description = "删除友情链接")
    @Operation(summary ="删除友情链接")
    @DeleteMapping("/protected/friends")
    public Result<Null> delFriend(@RequestBody List<Integer> friendKey) {
        return friendsService.deleteFriend(friendKey);
    }
}
