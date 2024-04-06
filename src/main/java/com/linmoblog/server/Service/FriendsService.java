package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.FriendsDao;
import com.linmoblog.server.Entity.Friend;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.enums.ResultCode;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    @Autowired
    private FriendsDao friendsDao;

    public Result<List<Friend>> getFriendsList() {
        List<Friend> friends = friendsDao.getFriendsList();
        return new Result<List<Friend>>(ResultCode.SUCCESS,friends);
    }

    public Result<Null> addFriends(Friend friend) {
        friendsDao.addFriends(friend);
        return new Result<Null>(ResultCode.SUCCESS);
    }

    public Result<Null> updateFriend(int friendKey) {
        friendsDao.updateFriend(friendKey);
        return new Result<Null>(ResultCode.SUCCESS);
    }

    public Result<Null> deleteFriend(List<Integer> friendKey) {
        friendsDao.deleteFriend(friendKey);
        return new Result<Null>(ResultCode.SUCCESS);
    }
}
