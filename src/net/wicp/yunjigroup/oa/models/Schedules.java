package net.wicp.yunjigroup.oa.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Schedules extends BaseData{

    private static final long serialVersionUID = 1965754L;
    
    public static final String KEY_BACKLOGWORK = "backlogwork";//代办理
    public static final String KEY_GOINGWORK = "goingwork";//已办理
    public static final String KEY_ENDWORK = "endwork";//已结束
    
    private List<ScheduleSimple> unSchedules = null;
    
    private List<ScheduleSimple> doneSchedules = null;
    
    private List<ScheduleSimple> overSchedules = null;

    public Schedules() {
        super();
    }

    public List<ScheduleSimple> getUnSchedules() {
        return unSchedules;
    }

    public void setUnSchedules( List<ScheduleSimple> unSchedules ) {
        this.unSchedules = unSchedules;
    }

    public List<ScheduleSimple> getDoneSchedules() {
        return doneSchedules;
    }

    public void setDoneSchedules( List<ScheduleSimple> doneSchedules ) {
        this.doneSchedules = doneSchedules;
    }

    public List<ScheduleSimple> getOverSchedules() {
        return overSchedules;
    }

    public void setOverSchedules( List<ScheduleSimple> overSchedules ) {
        this.overSchedules = overSchedules;
    }

    @Override
    public String toString() {
        return "Schedules [unSchedules=" + unSchedules + ", doneSchedules=" + doneSchedules
                + ", overSchedules=" + overSchedules + "]";
    }
    
    public static Schedules fromJson(JSONObject jsonObject){
        if(jsonObject == null) return null;
        Schedules schedules = new Schedules();
        List<ScheduleSimple> unSimples = new ArrayList<ScheduleSimple>();
        List<ScheduleSimple> doneSimples = new ArrayList<ScheduleSimple>();
        List<ScheduleSimple> overSimples = new ArrayList<ScheduleSimple>();
        
        JSONArray unArray = jsonObject.optJSONArray(KEY_BACKLOGWORK);
        if(unArray != null){
            int len = unArray.length();
            for(int i=0;i < len;i++){
                ScheduleSimple simple = ScheduleSimple.fromJson(unArray.optJSONObject(i));
                unSimples.add(simple);
            }
        }
        schedules.setUnSchedules(unSimples);
        
        JSONArray doneArray = jsonObject.optJSONArray(KEY_GOINGWORK);
        if(doneArray != null){
            int len = doneArray.length();
            for(int i=0;i < len;i++){
                ScheduleSimple simple = ScheduleSimple.fromJson(doneArray.optJSONObject(i));
                doneSimples.add(simple);
            }
        }
        schedules.setDoneSchedules(doneSimples);
        JSONArray overArray = jsonObject.optJSONArray(KEY_ENDWORK);
        if(overArray != null){
            int len = overArray.length();
            for(int i=0;i < len;i++){
                ScheduleSimple simple = ScheduleSimple.fromJson(overArray.optJSONObject(i));
                overSimples.add(simple);
            }
        }
        schedules.setOverSchedules(overSimples);
        return schedules;
    }

}
