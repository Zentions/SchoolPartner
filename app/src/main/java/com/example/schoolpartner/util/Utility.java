package com.example.schoolpartner.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.gson.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by q on 2017/4/21.
 */
public class Utility {
    public static boolean handlePersonResponse(String response){
        if(!TextUtils.isEmpty(response)){

            Gson gson = new Gson();
            List<Person> list = gson.fromJson(response,new TypeToken<List<Person>>(){}.getType());
            if(list.size()==0)return false;
            else {
                Person user = list.get(0);
                if( QueryDB.QueryPerson(user.getNumber()).size()==0){
                    user.save();
                }else{
                    QueryDB.UpdatePerson(user.getNumber(),user);
                }


                return true;
            }
        }
        return false;
    }
    public static boolean handleAllTask(String response){
        if(!TextUtils.isEmpty(response)){
            DataSupport.deleteAll(Task.class);
            Gson gson = new Gson();
            List<Task> list = gson.fromJson(response,new TypeToken<List<Task>>(){}.getType());
            if(list.size()==0)return false;
            else {
                for(Task task:list){
                       task.save();

               }
                return true;
            }
        }
        return false;
    }
}
