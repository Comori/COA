package net.wicp.yunjigroup.oa.net;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.wicp.yunjigroup.oa.models.AddressGroup;
import net.wicp.yunjigroup.oa.models.BaseData;
import net.wicp.yunjigroup.oa.models.CateList;
import net.wicp.yunjigroup.oa.models.Contact;
import net.wicp.yunjigroup.oa.models.ContactSimple;
import net.wicp.yunjigroup.oa.models.HomeData;
import net.wicp.yunjigroup.oa.models.MailDetail;
import net.wicp.yunjigroup.oa.models.MailList;
import net.wicp.yunjigroup.oa.models.NoticeDetail;
import net.wicp.yunjigroup.oa.models.NoticeList;
import net.wicp.yunjigroup.oa.models.Schedules;
import net.wicp.yunjigroup.oa.models.User;
import net.wicp.yunjigroup.oa.ui.SendMailActivity;
import net.wicp.yunjigroup.oa.utils.Contants;
import net.wicp.yunjigroup.oa.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

/**
 * 网络请求实体类
 * @author chenqiang
 * @2014年10月28日
 * @TODO
 */
public class NetEngine {
    //超时时间20s
    public static final int REQUEST_TIME_OUT = 20 * 1000 ;
    
    public static NetEngine engine = null;
    
    //登录用户的token
    public static String token = null;
    
    private NetEngine(){}
    
    public static NetEngine getInstance(Context context){
        if(engine == null){
            engine = new NetEngine();
        }
        if(TextUtils.isEmpty(token)){
            initToken(context);
        }
        return engine;
    }
    
    
    private static void initToken(Context context) {
        User user = null;
        try {
            user = (User) Utils.getObjFromSP(context, Contants.SP_USER);
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(user != null){
            token = user.getToken();
        }
        
    }

    /**
     * post 请求
     * @param url
     * @param request
     * @return
     */
    public  Response post(String url,String request){
        HttpPost post = new HttpPost(url);
        HttpParams httpParams = new BasicHttpParams();
        HttpResponse response = null;
        
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, REQUEST_TIME_OUT);
        
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Request.KEY_REQUEST, request));
        try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = client.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.bulid(response);
    }
    
    /**
     * 发送邮件的请求
     * @param url
     * @param card
     * @param id
     * @param act 发送消息；回复；转发。其中一个
     * @param title
     * @param sjr 收件人
     * @param content
     * @return
     */
    public  Response send(String url,String card,String id,String act,String title,String sjr,String content){
        HttpPost post = new HttpPost(url);
        HttpParams httpParams = new BasicHttpParams();
        HttpResponse response = null;
        
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, REQUEST_TIME_OUT);
        
        HttpClient client = new DefaultHttpClient(httpParams);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Request.SendMail.KEY_CONTENT, content));
        params.add(new BasicNameValuePair(Request.SendMail.KEY_ID, id));
        params.add(new BasicNameValuePair(Request.SendMail.KEY_CARD, card));
        params.add(new BasicNameValuePair(Request.SendMail.KEY_TITLE, title));
        params.add(new BasicNameValuePair(Request.SendMail.KEY_SJR, sjr));
        params.add(new BasicNameValuePair(Request.SendMail.KEY_ACT, act));
        params.add(new BasicNameValuePair(Request.KEY_TOKEN, token));
        params.add(new BasicNameValuePair(Request.KEY_ROUND_STR, Request.VALUE_ROUND_STR));
        try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = client.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.bulid(response);
    }
    
    /**
     * 登陆
     * @param name
     * @param passwd
     * @return
     */
    public  User login(String name,String passwd){
        
        Response response = post(Request.Login.URL, Request.Login.createRequest(name, passwd));
        User user = null;
        if(response == null){
            return null;
        }
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){   
                try {
                    JSONObject jsonObject = new JSONObject(response.content);
                    user = User.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return user;
    }
    
    /**
     * 首页数据
     * @param token
     * @return
     */
    public  HomeData getHomeData(String token){
        if(this.token == null) this.token = token;
        Response response = post(Request.HomeList.URL, Request.HomeList.createRequest(token));
        HomeData homeData = null;
        if(response == null){
            return null;
        }
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                try {
                    JSONObject jsonObject = new JSONObject(response.content);
                    homeData = HomeData.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return homeData;
    }
    
    /**
     * 通讯录中部门列表
     * @return
     */
    public  List<AddressGroup> getAddressList(){
        Response response = post(Request.AddressList.URL, Request.AddressList.createRequest(token));
        if(response == null) return null;
        List<AddressGroup> addressGroups = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                try {
                    JSONObject jsonObject = new JSONObject(response.content);
                    addressGroups = AddressGroup.fromJsons(jsonObject);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return addressGroups;
    }
    
    /**
     * 部门中成员列表
     * @param department 部门名称
     * @param name name为空则返回整个部门的成员
     * @return
     */
    public  List<ContactSimple> getAddressListUser(String department,String name){
        Response response = post(Request.AddressListUser.URL, Request.AddressListUser.createRequest(token,department,name));
        if(response == null) return null;
        List<ContactSimple> simples = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                try {
                    JSONObject jsonObject = new JSONObject(response.content);
                    simples = ContactSimple.fromJsons(jsonObject);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return simples;
    }
    
    /**
     * 联系人详情
     * @param id
     * @param name
     * @return
     */
    public  Contact getAddressDetail(String id,String name){
        Response response = post(Request.AddressDetail.URL, Request.AddressDetail.createRequest(token,id,name));
        if(response == null) return null;
        Contact contact = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                try {
                    JSONObject jsonObject = new JSONObject(response.content);
                    contact = Contact.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return contact;
    }
    
    /**
     * 发送反馈意见
     * @param content
     * @param mobile
     * @return
     */
    public  boolean sendFeedback(String content,String mobile ){
        Response response = post(Request.Setting.URL, Request.Setting.createRequest(token, content, mobile));
        if(response == null){
            return false;
        }else{
            if(response.statusCode == HttpStatus.SC_OK){
                return true;
            }else return false;
        }
    }
    
    /**
     * 获取通知公告列表
     * @return
     */
    public NoticeList getNoticeList(){
        Response response = post(Request.NoticeList.URL, Request.NoticeList.createRequest(token));
        if(response == null)return null;
        NoticeList noticeList = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    noticeList = NoticeList.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return noticeList;
    }
    
    /**
     * 通知公告详情
     * @param id
     * @param card
     * @return
     */
    public NoticeDetail getNoticeDetail(String id,String card){
        Response response = post(Request.NoticeDetail.URL, Request.NoticeDetail.createRequest(token, id,card));
        if(response == null) return null;
        NoticeDetail detail = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    detail = NoticeDetail.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return detail;
    }
    
    /**
     * 收件箱列表
     * @param title title为空时返回整个收件箱邮件</p>
     *              title不为空时返回包含title内容的邮件</p>
     * @return
     */
    public MailList getReceviceMailList(String title){
        Response response = post(Request.ReceiveList.URL, Request.ReceiveList.createRequest(token, title));
        if(response == null) return null;
        MailList mailList = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    mailList = MailList.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return mailList;
    }
    
    /**
     * 发件箱列表
     * @param title title为空时返回整个收件箱邮件</p>
     *              title不为空时返回包含title内容的邮件</p>
     * @return
     */
    public MailList getSendMailList(String title){
        Response response = post(Request.SendMailList.URL, Request.SendMailList.createRequest(token, title));
        if(response == null) return null;
        MailList mailList = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    mailList = MailList.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return mailList;
    }
    
    /**
     * 发件箱邮件详情
     * @param card
     * @param id
     * @return
     */
    public MailDetail getRecevieMailDetail(String card,String id){
        Response response = post(Request.RecevieMailDetail.URL, Request.RecevieMailDetail.createRequest(token, card, id));
        if(response == null) return null;
        MailDetail mailDetail = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    mailDetail = MailDetail.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return mailDetail;
    }
    
    /**
     * 收件箱邮件详情
     * @param card
     * @param id
     * @return
     */
    public MailDetail getSendMailDetail(String card,String id){
        Response response = post(Request.SendMailDetail.URL, Request.SendMailDetail.createRequest(token, card, id));
        if(response == null) return null;
        MailDetail mailDetail = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    mailDetail = MailDetail.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return mailDetail;
    }
    
    /**
     * {@link NetEngine#send(String, String, String, String, String, String, String)}
     * 发送邮件
     * @param card
     * @param id
     * @param act
     * @param title
     * @param sjr
     * @param content
     * @return
     */
    public boolean sendMail(String card,String id,String act,String title,String sjr,String content){
    	Response response = send(Request.SendMail.URL,card,id,act,title,sjr,content);
    	if(response == null) return false;
    	BaseData baseData = null;
    	if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    baseData = BaseData.fromJson(jsonObject);
                    if(baseData != null){
                    	if(baseData.getErrorcode().equals("0")){
                    		return true;
                    	}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
    	return false;
    }
    
    /**
     * 获取组织结构树\部门\人员
     * @return
     */
    public CateList getOrgs(){
        Response response = post(Request.GetOrg.URL, Request.GetOrg.createRequest(token));
        if(response == null) return null;
        CateList cateList = null;
        if(response.statusCode == HttpStatus.SC_OK){
        	if(!TextUtils.isEmpty(response.content)){
        		JSONObject jsonObject = null;
        		try {
					jsonObject = new JSONObject(response.content);
					cateList = CateList.fromJson(jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
        	}
        }
        return cateList;
    }
    /**
     * 获取会议列表
     * @return
     */
    public NoticeList getMeetingList(){
        Response response = post(Request.MeetingList.URL, Request.MeetingList.createRequest(token));
        if(response == null)return null;
        NoticeList noticeList = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    noticeList = NoticeList.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return noticeList;
    }
    
    /**
     * 获取会议详情
     * @param id
     * @param card
     * @return
     */
    public NoticeDetail getMeetingDetail(String id,String card){
        Response response = post(Request.MeetingDetail.URL, Request.MeetingDetail.createRequest(token, id,card));
        if(response == null) return null;
        NoticeDetail detail = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    detail = NoticeDetail.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return detail;
    }
    
    /**
     * 代办流程列表
     * @return
     */
    public Schedules getWorkflowList(){
        Response response = post(Request.WorkflowList.URL, Request.WorkflowList.createRequest(token));
        if (response == null) return null;
        Schedules schedules = null;
        if(response.statusCode == HttpStatus.SC_OK){
            if(!TextUtils.isEmpty(response.content)){
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response.content);
                    schedules = Schedules.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }  
        }
        return schedules;
    }
}
