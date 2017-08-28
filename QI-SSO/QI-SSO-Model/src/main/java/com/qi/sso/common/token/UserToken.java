package com.qi.sso.common.token;

import java.io.Serializable;

/**
 * 用户COOKIES的标记类
 */
public class UserToken implements Serializable {

    private static final long serialVersionUID = -723299916104448754L;

    //账户
    private String account;

    //Session ID
    private String sessionID;

    //session 数据
    private String sessionData;

    //磁盘分区标识
    private String space;

    //用户编号
    private String userID;

    //Token串
    private String token;

    //Token 加密盐值
    private String token_Key;

    //Token 加密盐值的Key
    private String tokenKey_Cache_Key;

    //内存过期时间
    private Long logoutTime;

    //登录时间
    private Long loginTimeStamp;

    //返回代码
    private String operatorCode;

    private String cacheKeyHashCode;
    /**
     * 操作结果标识 0= 失败， 1 = 成功，2=须重新写Cookies
     */
    private Integer operatorResult;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_Key() {
        return token_Key;
    }

    public void setToken_Key(String token_Key) {
        this.token_Key = token_Key;
    }

    public String getTokenKey_Cache_Key() {
        return tokenKey_Cache_Key;
    }

    public void setTokenKey_Cache_Key(String tokenKey_Cache_Key) {
        this.tokenKey_Cache_Key = tokenKey_Cache_Key;
    }

    public Long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Long getLoginTimeStamp() {
        return loginTimeStamp;
    }

    public void setLoginTimeStamp(Long loginTimeStamp) {
        this.loginTimeStamp = loginTimeStamp;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getCacheKeyHashCode() {
        return cacheKeyHashCode;
    }

    public void setCacheKeyHashCode(String cacheKeyHashCode) {
        this.cacheKeyHashCode = cacheKeyHashCode;
    }

    public Integer getOperatorResult() {
        return operatorResult;
    }

    public void setOperatorResult(Integer operatorResult) {
        this.operatorResult = operatorResult;
    }


    public String toString() {
        return "account[" + account + "]," +
                "sessionID[" + sessionID + "]," +
                "space[" + space + "]," +
                "userID[" + userID + "]," +
                "token[" + token + "]," +
                "CacheKey[" + tokenKey_Cache_Key + "]," +
                "operatorCode[" + operatorCode + "]," +
                "token_Key[" + token_Key + "]," +
                "operatorResult[" + operatorResult + "],";
    }

    public void refreshTimeStamp() {
        this.loginTimeStamp = System.currentTimeMillis();
    }

}
