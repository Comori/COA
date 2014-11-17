package net.wicp.yunjigroup.oa.utils;

import java.util.Stack;

import android.app.Activity;

public class ActivitesStack {
    
    private static Stack<Activity> activitiesStack = null;
    
    private static ActivitesStack instance = null;
    
    private ActivitesStack(){
        if(activitiesStack == null){
            activitiesStack = new Stack<Activity>();
        }
    }
    
    public static ActivitesStack getInstance(){
        if(instance == null){
            instance = new ActivitesStack();
        }
        return instance;
    }
    
    public void push(Activity activity){
        if(activity != null){
            if(!activitiesStack.contains(activity)){
                activitiesStack.push(activity);
            }
        }
    }
    
    public Activity pop(){
        if(!activitiesStack.isEmpty()){
           return activitiesStack.pop();
        }
        return null;
    }
    
    public void pop(Activity activity){
        if(activity != null){
            activitiesStack.remove(activity);
        }
    }
    
    public void popAll(){
        Activity activity = null;
        while((activity = pop()) != null){
            activity.finish();
        }
    }
}
