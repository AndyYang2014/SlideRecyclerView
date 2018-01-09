package com.andyyang.sliderecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andyyang.sliderecyclerview.R;
import com.andyyang.sliderecyclerview.widget.SlideItem;

/**
 * Created by AndyYang
 * date:2018/1/9.
 * mail:andyyang2014@126.com
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private Context mContext;

    public MainListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
        View menu = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
        SlideItem slideItem = new SlideItem(mContext);
        slideItem.setContentView(content, menu);
        return new ViewHolder(slideItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.view.setTag(position);

        holder.menuView.findViewById(R.id.item_totop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了置顶" + position, Toast.LENGTH_LONG).show();
            }
        });

        holder.menuView.findViewById(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了删除" + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public View contentView;
        public View menuView;

        ViewHolder(SlideItem view) {
            super(view);
            this.view = view;
            contentView = view.getContentView();
            menuView = view.getMenuView();

        }
    }
}
