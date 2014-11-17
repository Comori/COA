package net.wicp.yunjigroup.oa.net;

import java.io.Serializable;

import net.wicp.yunjigroup.oa.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


public class Response implements Serializable{
    
    private static final long serialVersionUID = 147987346L;
    
    public int statusCode = 0; 
    
    public String content = "";
    
    public static Response bulid(HttpResponse httpResponse){
        if(httpResponse == null) return null;
        Response response = new Response();
        response.statusCode = httpResponse.getStatusLine().getStatusCode();
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null){
            try {
                String re = EntityUtils.toString(entity, "UTF-8");
                response.content = Utils.decrypt(re);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    
}
