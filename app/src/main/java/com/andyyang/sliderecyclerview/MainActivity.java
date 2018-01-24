package com.andyyang.sliderecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.andyyang.sliderecyclerview.adapter.MainListAdapter;
import com.andyyang.sliderecyclerview.adapter.decoration.DefaultDecoration;
import com.andyyang.srecyclerview.SlideRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AndyYang
 * date:2018/1/9.
 * mail:andyyang2014@126.com
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_recycler)
    SlideRecyclerView mMainRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        mMainRecycler.setLayoutManager(new LinearLayoutManager(this));
        MainListAdapter adapter = new MainListAdapter(this);
        mMainRecycler.addItemDecoration(new DefaultDecoration());
        mMainRecycler.setAdapter(adapter);
        mMainRecycler.setOnItemClickListener(new SlideRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "show" + position, Toast.LENGTH_LONG).show();
            }
        });
        mMainRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mMainRecycler.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mMainRecycler.loadMoreComplete();
            }
        });
    }
}
