package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MailList extends BaseData{
    private static final long serialVersionUID = 1645359L;
    
    private List<MailSimple> mailSimples = null;

    public MailList() {
        super();
    }

    public List<MailSimple> getMailSimples() {
        return mailSimples;
    }

    public void setMailSimples( List<MailSimple> mailSimples ) {
        this.mailSimples = mailSimples;
    }

    @Override
    public String toString() {
        return "MailList [mailSimples=" + mailSimples + "]";
    }
    
    public static MailList fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        MailList list = new MailList();
        List<MailSimple> simples = new ArrayList<MailSimple>();
        list.setErrorcode(jsonObject.optString(KEY_ERRORCODE));
        list.setErrordesc(jsonObject.optString(KEY_ERRORDESC));
        JSONArray array = jsonObject.optJSONArray(KEY_DATA);
        if(array != null){
            int len = array.length();
            for(int i=0;i < len;i++){
                try {
                    JSONObject object = array.getJSONObject(i);
                    MailSimple simple = MailSimple.fromJson(object);
                    simples.add(simple);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        list.setMailSimples(simples);
        return list;
    }

}
