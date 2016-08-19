package com.brioal.bottomtablayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brioal.bottomtab.entity.TabEntity;
import com.brioal.bottomtab.interfaces.OnTabSelectedListener;
import com.brioal.bottomtab.view.BottomLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomLayout mBottomLayout;
    private Button mBtnAdd;
    private List<TabEntity> mList;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomLayout = (BottomLayout) findViewById(R.id.main_tab);
        mBtnAdd = (Button) findViewById(R.id.main_add);
        initBottonLayout();
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomLayout.setNews(1, 0); //设置未读消息
                mBottomLayout.setNews(2, 1);
                mBottomLayout.setNews(3, 2);
                mBottomLayout.setNews(4, 3);
                mBottomLayout.setNews(5, 4);
            }
        });

    }

    private void initBottonLayout() {
        mList = new ArrayList<>();
        mList.add(new TabEntity(R.mipmap.icon_1, "推荐"));
        mList.add(new TabEntity(R.mipmap.icon_2, "游戏"));
        mList.add(new TabEntity(R.mipmap.icon_3, "软件"));
        mList.add(new TabEntity(R.mipmap.icon_4, "应用圈"));
        mList.add(new TabEntity(R.mipmap.icon_5, "管理"));
        mBottomLayout.setList(mList); //设置数据源
        mBottomLayout.setNews(1, 0); //设置未读消息
        mBottomLayout.setNews(2, 1);
        mBottomLayout.setNews(3, 2);
        mBottomLayout.setNews(4, 3);
        mBottomLayout.setNews(5, 4);
        //设置Item点击事件
        mBottomLayout.setSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(int position) {
                mBottomLayout.cleanNews(position); //清除未读消息
                if (mToast == null) {
                    mToast = Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(position + "");
                }
                mToast.show();
            }
        });
    }
}
