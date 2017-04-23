package com.example.schoolpartner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.schoolpartner.gson.Task;

import java.util.List;

/**
 * Created by q on 2017/4/23.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> list;
    private Context  mContext;
    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = list.get(position);
        holder.title.setText(task.getTitle());
        holder.money.setText("悬赏金额："+task.getMoney());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView state;
        TextView money;
        Button association;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.TaskTitle);
            state =(TextView)itemView.findViewById(R.id.state);
            money = (TextView)itemView.findViewById(R.id.money);
            association=(Button)itemView.findViewById(R.id.associate);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }
}
