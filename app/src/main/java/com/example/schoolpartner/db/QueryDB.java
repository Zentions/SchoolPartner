package com.example.schoolpartner.db;

import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.gson.Task;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2017/4/21.
 */
public class QueryDB {
    public static List<Person> QueryPerson(String id){
        return DataSupport.where("number=?",id).find(Person.class);

    }
    public static void UpdatePerson(String id,Person person){
        person.updateAll("number=?",id);
    }
    public static List<Task> QueryTask(){//查询所有未完成状态
        return DataSupport.where("finished=?","0").find(Task.class);

    }
    public static List<Task> QueryTask(Integer id){//用id查询
        return DataSupport.where("id=?",""+id).find(Task.class);

    }
    public static void UpdateTask(Integer id,Task task){//更新
       task.updateAll("id=?",""+id);

    }
}
