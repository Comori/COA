package net.wicp.yunjigroup.oa.utils;

import android.os.Environment;

public class Contants {

    public static final String SP_USER = "sp_user";
    
    public static final String SP_IS_LOGIN = "sp_is_login";
    
    public static final String SP_MAIL_CONTACT = "sp_mail_contact";
    
    public static final String SP_USER_NAMES = "sp_user_names";
    public static final String SP_USER_LATELY_NAME = "sp_user_lately_name";
    
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/COA";
    
    public static final String MAIL_UN_READ = "1";//未读
    public static final String MAIL_READED = "0";//已读
    
    public static final int MAIL_SEND = 0;//发邮件
    public static final int MAIL_REPLY = 1;//回复邮件
    public static final int MAIL_FORWORD = 2;//转发邮件
    
    public static final String MAIL_SEND_ACT = "发送消息";
    public static final String MAIL_REPLY_ACT = "回复";
    public static final String MAIL_FORWORD_ACT = "转发";
    
    public static int ACTIVITY_RESULT_OK_CODE = 456;
}
