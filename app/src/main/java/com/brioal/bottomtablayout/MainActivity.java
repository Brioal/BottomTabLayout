package com.brioal.bottomtablayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.brioal.bottomtab.entity.TabEntity;
import com.brioal.bottomtab.interfaces.OnTabSelectedListener;
import com.brioal.bottomtab.view.BottomLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomLayout mBottomLayout;
    private List<TabEntity> mList;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomLayout = (BottomLayout) findViewById(R.id.main_tab);
        mList = new ArrayList<>();
        mList.add(new TabEntity(R.mipmap.icon_1, "推荐"));
        mList.add(new TabEntity(R.mipmap.icon_2, "游戏"));
        mList.add(new TabEntity(R.mipmap.icon_3, "软件"));
        mList.add(new TabEntity(R.mipmap.icon_4, "应用圈"));
        mList.add(new TabEntity(R.mipmap.icon_5, "管理"));
        mBottomLayout.setList(mList);
        mBottomLayout.setSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(int position) {
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
