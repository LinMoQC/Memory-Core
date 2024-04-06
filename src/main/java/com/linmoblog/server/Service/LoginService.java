package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.LoginDao;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Entity.User;
import com.linmoblog.server.Entity.UserInfo;
import com.linmoblog.server.Utils.JWTTokenUtil;
import com.linmoblog.server.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginDao loginDao;

    public ResponseEntity<Result<String>> login(User user) {
        User res = loginDao.login(user);
        if(res != null) {
            String token = JWTTokenUtil.CreateToken("admin");
            Result<String> result = new Result<>(ResultCode.SUCCESS_LOGIN, token);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        Result<String> result =  new Result<>(ResultCode.ERROR_LOGIN,null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

    public Result<UserInfo> userinfo() {
        return loginDao.userinfo();
    }
}
