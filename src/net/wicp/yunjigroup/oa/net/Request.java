package net.wicp.yunjigroup.oa.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import net.wicp.yunjigroup.oa.utils.Utils;
/**
 * 请求类，包含所有接口请求
 * @author chenqiang5
 *
 */
public class Request implements Serializable{

    private static final long serialVersionUID = 126485236L;
    
    public static final String HOST = "http://coa.gdftg.cn";
//    public static final String HOST = "http://yunjigroup.wicp.net";
    
    public static final String KEY_ROUND_STR = "roundstr";
    public static final String VALUE_ROUND_STR = "dd7576aecadd630b6a329799736b2b25";
    public static final String KEY_REQUEST = "request";
    public static final String KEY_TOKEN = "token";
    
    public static Map<String, Object> buildBaseRequest(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_ROUND_STR, VALUE_ROUND_STR);
        return map;
    }
    
    /**
     * 登陆请求
     * @author chenqiang5
     *
     */
    protected static class Login{
        
        private static final String KEY_NAME = "xingming";
        private static final String KEY_PASSWD = "mima";
        
        public static final String URL = HOST + "/app/logined.php";
        
        public static String createRequest(String name,String passwd){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_NAME, name);
            params.put(KEY_PASSWD, passwd);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    
    /**
     * 首页请求
     * @author chenqiang5
     *
     */
    protected static class HomeList{
        
        public static final String URL = HOST + "/app/index.php";
        
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    /**
     * 通讯录部门列表
     * @author chenqiang5
     *
     */
    protected static class AddressList{
        
        public static final String URL = HOST + "/app/address/addresslist.php";
        
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    
    /**
     * 某个部门下面具体人员
     * @author chenqiang5
     *
     */
    protected static class AddressListUser{
        
        private static final String KEY_DEPARTMENT = "department";
        private static final String KEY_NAME = "xingming";
        
        public static final String URL = HOST + "/app/address/addresslistuser.php";
        
        public static String createRequest(String token,String department,String name){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            params.put(KEY_DEPARTMENT, department);
            params.put(KEY_NAME, name);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    /**
     * 联系人详情
     * @author chenqiang5
     *
     */
    protected static class AddressDetail{
        
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "xingming";
        
        public static final String URL = HOST + "/app/address/addressdetail.php";
        
        public static String createRequest(String token,String id,String name){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            params.put(KEY_ID, id);
            params.put(KEY_NAME, name);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    
    /**
     * 会议列表
     * @author chenqiang
     * @2014年10月21日
     * @TODO
     */
    protected static class MeetingList{
        
        public static final String URL = HOST + "/app/meeting/meetinglist.php";
        
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    protected static class MeetingDetail{
        
        public static final String URL = HOST + "/app/meeting/meetingdetail.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        
        public static String createRequest(String token,String id,String card){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 设置：反馈意见
     * @author chenqiang
     * @2014年10月21日
     * @TODO
     */
    protected static class Setting{
        
        public static final String URL = HOST + "/app/setup/suggestion.php";
        
        public static final String KEY_FEEDBACK_CONTENT = "content";
        public static final String KEY_MOBILE = "mobile";
        
        public static String createRequest(String token,String content,String mobile){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            params.put(KEY_FEEDBACK_CONTENT, content);
            if(!TextUtils.isEmpty(mobile)) params.put(KEY_MOBILE, mobile);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
        
    }
    
    /**
     * 会议通知列表
     * @author chenqiang
     *
     */
    protected static class NoticeList{
        public static final String URL = HOST + "/app/notice/noticelist.php";
        
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 会议通知详情
     * @author chenqiang
     *
     */
    protected static class NoticeDetail{
        public static final String URL = HOST + "/app/notice/noticedetail.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        
        public static String createRequest(String token,String id,String card){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }

    /**
     * 内部邮件收件箱列表
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class ReceiveList{
        public static final String URL = HOST + "/app/message/receivelist.php";
        public static final String KEY_TITLE = "title";
        
        public static String createRequest(String token,String title){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TITLE, title);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 内部邮件发件箱列表
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class SendMailList{
        public static final String URL = HOST + "/app/message/sendlist.php";
        public static final String KEY_TITLE = "title";
        
        public static String createRequest(String token,String title){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TITLE, title);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 内部邮件收件邮件详情
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class RecevieMailDetail{
        public static final String URL = HOST + "/app/message/receivedetail.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        
        public static String createRequest(String token,String card,String id){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 内部邮件发件箱邮件详情
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class SendMailDetail{
        public static final String URL = HOST + "/app/message/senddetail.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        
        public static String createRequest(String token,String card,String id){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 发送内部邮件
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class SendMail{
        public static final String URL = HOST + "/app/message/reply2forwardok.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        public static final String KEY_ACT = "act";
        public static final String KEY_TITLE = "title";
        public static final String KEY_SJR = "sjr";
        public static final String KEY_CONTENT = "content";
        
        public static String createRequest(String token,String card,String id,String act,String title,String sjr){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_ACT , act);
            params.put(KEY_TITLE, title);
            params.put(KEY_SJR, sjr);
//            params.put(KEY_CONTENT, content);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 转发与回复显示界面
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class ReplyOrForwordShowMail{
        public static final String URL = HOST + "/app/message/reply2forward.php";
        public static final String KEY_ID = "id";
        public static final String KEY_CARD = "card";
        public static final String KEY_ACT = "act";
        
        public static String createRequest(String token,String card,String id,String act){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_CARD, card);
            params.put(KEY_ID, id);
            params.put(KEY_ACT , act);
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 获取组织结构树\部门\人员
     * @author chenqiang
     * @2014年10月23日
     * @TODO
     */
    protected static class GetOrg{
        public static final String URL = HOST + "/app/message/getorg.php";
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
    
    /**
     * 工作流程列表
     * @author chenqiang
     * @2014年10月27日
     * @TODO
     */
    protected static class WorkflowList{
        public static final String URL = HOST + "/app/workflow/workflowlist.php";
        public static String createRequest(String token){
            Map<String, Object> params = buildBaseRequest();
            params.put(KEY_TOKEN, token);
            String request = Utils.mapToJsonStr(params);
            return Utils.encrypt(request);
        }
    }
}
