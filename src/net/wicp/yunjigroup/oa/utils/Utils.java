package net.wicp.yunjigroup.oa.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.wicp.yunjigroup.oa.R;
import net.wicp.yunjigroup.oa.models.ContactSimple;
import net.wicp.yunjigroup.oa.models.MailDetail;
import net.wicp.yunjigroup.oa.net.NetEngine;
import net.wicp.yunjigroup.oa.side.CharacterParser;
import net.wicp.yunjigroup.oa.side.SortModel;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {
	
	public static List<String> receviers = null;

    public static String mapToJsonStr( Map<String, Object> map ) {
        if (map == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        Iterator<String> keys = map.keySet().iterator();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = map.get(key);
                json.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static String encrypt( String str ) {
        String result = null;
        if (!TextUtils.isEmpty(str)) {
            result = Uri.encode(str);
            result = Base64.encodeToString(result.getBytes(), Base64.DEFAULT);
        }
        return result;
    }

    public static String decrypt( String str ) {
        String result = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                result = new String(Base64.decode(str, Base64.DEFAULT), "UTF-8");
                result = Uri.decode(result);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void writeStringToSP( Context context, String key, String value ) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static void writeSetToSp(Context context, String key, Set<String> value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static String readStringFromSP( Context context, String key ) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }
    public static Set<String> readSetFromSP( Context context, String key ) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(key, null);
    }

    public static void saveObjToSP( Context context, String key, Object value ) throws IOException {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(value);
        String objString = new String(Base64.encode(byteArrayOutputStream.toByteArray(),
                Base64.DEFAULT));
        sp.edit().putString(key, objString).commit();
        objectOutputStream.close();
    }

    public static Object getObjFromSP( Context context, String key )
            throws StreamCorruptedException, IOException, ClassNotFoundException {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sp.getString(key, "");
        if (str.length() <= 0)
            return null;
        Object obj = null;
        byte[] mobileBytes = Base64.decode(str.toString().getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream;
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }

    public static boolean isLogin( Context context ) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Contants.SP_IS_LOGIN, false);
    }

    public static void setLoginState( Context context, boolean isLogin ) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(Contants.SP_IS_LOGIN, isLogin);
        editor.commit();
    }

    public static String getFileNameFromUrl( String url ) {
        if (!TextUtils.isEmpty(url)) {
            String str[] = url.split("/");
            return str[str.length - 1];
        }
        return null;
    }

    /**
     * 下载文件
     * 
     * @param url
     */
    public static void downFile( final String urlStr, final String filedir, final String fileName,
            final FileDownListener listener ) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                listener.onStart();
            }

            @Override
            protected Void doInBackground( Void... params ) {
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                File outputFiledir = new File(filedir);
                File outputFile = new File(outputFiledir, fileName);
                URL url;
                try {
                    url = new URL(urlStr);
                    if (!outputFiledir.exists()) {
                        outputFiledir.mkdirs();
                    }
                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                    } else
                        return null;
                    bos = new BufferedOutputStream(new FileOutputStream(outputFile));
                    URLConnection connection = url.openConnection();
                    bis = new BufferedInputStream(connection.getInputStream());
                    byte b[] = new byte[1];
                    while (bis.read(b) != -1) {
                        bos.write(b);
                    }
                    bos.flush();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null)
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (bos != null)
                        try {
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return null;
            }

            @Override
            protected void onPostExecute( Void result ) {
                listener.onComplete();
            }

        }.execute();
    }

    public interface FileDownListener {
        public void onComplete();

        public void onFaile();

        public void onStart();
    }

    public static String urlEncode( String url ) {
        String result = Uri.encode(url);
        result = result.replaceAll("%3A", ":").replaceAll("%2F", "/");
        return result;
    }

    public static String getPostfix( String str ) {
        String re[] = str.split("\\.");
        if (re != null && re.length > 0) {
            return re[re.length - 1];
        }
        return null;
    }

    public static String getDataType( String postfix ) {
        String re = null;
        if (postfix.equalsIgnoreCase("txt")) {
            re = "text/*";
        } else if (postfix.equalsIgnoreCase("png") || postfix.equalsIgnoreCase("jpg")
                || postfix.equalsIgnoreCase("gif") || postfix.equalsIgnoreCase("jpeg")) {
            re = "image/*";
        } else if (postfix.equalsIgnoreCase("mp3") || postfix.equalsIgnoreCase("wma")) {
            re = "audio/*";
        } else if (postfix.equalsIgnoreCase("mp4") || postfix.equalsIgnoreCase("avi")) {
            re = "video/*";
        } else if (postfix.equalsIgnoreCase("pdf")) {
            re = "application/pdf";
        } else if (postfix.equalsIgnoreCase("doc") || postfix.equalsIgnoreCase("docx")) {
            re = "application/msword";
        } else if (postfix.equalsIgnoreCase("xls") || postfix.equalsIgnoreCase("xlsx")) {
            re = "application/vnd.ms-excel";
        } else if (postfix.equalsIgnoreCase("ppt") || postfix.equalsIgnoreCase("pptx")) {
            re = "application/vnd.ms-powerpoint";
        } else
            re = "application/*";
        return re;
    }

    public static String getNowDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    
    public static int getAccessoryIconId(String path){
    	String postfix = getPostfix(path);
    	if(postfix.equalsIgnoreCase("doc") || postfix.equals("docx")){
    		return R.drawable.fujian_doc;
    	}else if(postfix.equalsIgnoreCase("ppt") || postfix.equalsIgnoreCase("pptx") ){
    		return R.drawable.fujian_ppt;
    	}else return R.drawable.fujian_other;
    }
    
    public static void openAccessory(Context context,Uri uri,String mime){
        Intent intent = new Intent("android.intent.action.VIEW"); 
        intent.addCategory("android.intent.category.DEFAULT"); 
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        intent.setDataAndType(uri,mime);
        if(intent.resolveActivity(context.getPackageManager()) == null) {  
            intent.setDataAndType(uri,"application/*");
        }
        if(intent.resolveActivity(context.getPackageManager()) == null) {  
            Toast.makeText(context, context.getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
        }else{
            context.startActivity(intent);
        }
    }
    
//    Html.fromHtml(  
//    "<font color=#E61A6B>红色代码</font> "+ "<i><font color=#1111EE>蓝色斜体代码</font></i>"+"<u><i><font color=#1111EE>蓝色斜体加粗体下划线代码</font></i></u>")
    
    public static String createDefaultTextForSendMail(int typeId,MailDetail detail){
        String html = null;
        switch (typeId) {
        case Contants.MAIL_SEND:
            html = "<html><body><p>请输入正文</p></body></html>";
            break;
        case Contants.MAIL_REPLY:
        case Contants.MAIL_FORWORD:
            String types = detail.getSendUser()==null?("收件人:"+detail.getReceiveUser()):("发件人:"+detail.getSendUser());
            html = "<html><body>"
                    + "<p>请输入正文</p>"
                    +"<p>--------------------------------------</p>"
                    +"<p><font color=#ACACAC>原邮件内容:</font></p>"
                    + "<p><font color=#ACACAC>标题:"
                    +detail.getTitle()
                    +"</font></p>"
                    + "<p><font color=#ACACAC>"
                    + types
                    +"</font></p>"
                    + "<p><font color=#ACACAC>时间:"
                    +detail.getSendTime()
                    +"</font></p>"
                    + "<p><font color=#ACACAC>正文:</font></p>"
                    + "<p><font color=#ACACAC>"
                    +detail.getContent()
                    +"</font></p>"
                    +"</body></html>";
            break;
        default:
            break;
        }
        return html;
    }
    
    public static void addRecevier(String s){
    	if(receviers == null){
    		receviers = new ArrayList<String>();
    	}
    	if(!receviers.contains(s)){
    		receviers.add(s);
    	}
    }
    public static void removeRecevier(String s){
    	if(receviers == null){
    		receviers = new ArrayList<String>();
    	}
    	receviers.remove(s);
    }
    
    public static String getMailRecevier(){
    	StringBuilder builder = new StringBuilder();
    	if(receviers != null && !receviers.isEmpty()){
    		int len = receviers.size();
    		for(int i=0;i < len;i++){
    			builder.append(receviers.get(i));
    			if(i < len-1)builder.append(",");
    		}
    	}
    	return builder.toString();
    }
    
    public static void releaseReceviers(){
    	if(receviers != null) receviers.clear();
    	receviers = null;
    }
    
    public static void clearReceviers(){
    	if(receviers != null) receviers.clear();
    }
    
    /**
	 * 填充数据
	 * @param data
	 * @return
	 */
	public static List<SortModel> filledData(List<ContactSimple> data){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<data.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(data.get(i).getName());
			sortModel.setId(data.get(i).getId());
			//汉字转换成拼音
			String pinyin = CharacterParser.getInstance().getSelling(data.get(i).getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
	}
	
	public static void clearToken(Context context){
	    NetEngine.token = null;
	    Utils.writeStringToSP(context, Contants.SP_USER, null);
	}
	
	public static void callPhone(Context mContext,String phoneNum){
	    Uri uri = Uri.parse("tel:"+phoneNum);
	    Intent intent = new Intent(Intent.ACTION_DIAL,uri);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    mContext.startActivity(intent);
	}
	public static void sendSms(Context mContext,String phoneNum){
	    Uri uri = Uri.parse("smsto://"+phoneNum);
	    Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    mContext.startActivity(intent);
	}
	
	public static String getAboutUsContent(Context mContext){
	    try {
            InputStream in = mContext.getAssets().open("about_us");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null){
                buffer.append(line);
            }
            in.close();
            br.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    return null;
	}
	
	public static int getStatusBarHeight(Activity context){
	    Rect frame = new Rect();
	    context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
	    return frame.top;
	}
	
	/**
	 * 隐藏键盘输入法
	 */
	public static void hideSoftInput(Activity context){
	    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
