package com.example.administrator.jaja;

/**
 * Created by Administrator on 2016/3/17.
 */
public enum  messageType
{
        LOGIN ,             //请求登陆
        LOGIN_SUCCESS,      //登陆成功1
        LOGIN_FAILURE,      //登录失败
        USER_NO_EXIST,      //用户不存在
        USER_IS_ONLINE,     //用户在线
        USER_NO_ONLINE,     //用户不在线
        USER_ALREADY_EXIST, //用户已经存在

        USER_REGISTER,      //注册账号6
        REGISTER_SUCCESS,   //注册成功
        REGISTER_FAILED,    //注册失败
        CHANGE_PASSWORD,    //修改密码9
        CHANGE_SUCCESS,     //修改成功
        PASSWORD_ERROR,     //密码错误
        ORIGIN_PASSWD_ERROR,//原始密码错误12

        GET_ROOM_DATA,   	//获取可入住的房间
        ROOM_DATA_LOADING,	//不断获取房间数据
        ROOM_DATA_LOAD_COMPLETED,//获取完成
        ROOM_RESERVATION_SUCCESS,//入住成功
        ROOM_RESERVATION_FAILURE,//入住失败
        DO_RESERVATION,

        CANCEL_ROOM_RESERVATION_SUCCESS,//退房成功
        CANCEL_ROOM_RESERVATION_FAILURE,//退房失败
        DO_CANCEL_RESERVATION,
}
