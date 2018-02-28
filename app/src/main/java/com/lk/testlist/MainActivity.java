package com.lk.testlist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_app_list;
    private AppAdapter mAppAdapter;
    public Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_app_list = (ListView) findViewById(R.id.lv_app_list);
        mAppAdapter = new AppAdapter();
        lv_app_list.setAdapter(mAppAdapter);
        initAppList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAppList();
    }

    private void initAppList(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<MyAppInfo> appInfos = ApkTool.scanLocalInstallAppList(MainActivity.this.getPackageManager());
                //排序
                Collections.sort(appInfos, new Comparator<MyAppInfo>() {
                    @Override
                    public int compare(MyAppInfo myAppInfo, MyAppInfo t1) {
                        if(myAppInfo.getLastUpdateTime() > t1.getLastUpdateTime()){
                            return 1;
                        }
                        if(myAppInfo.getLastUpdateTime() == t1.getLastUpdateTime()){
                            return 0;
                        }
                        return -1;
                    }
                });
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAppAdapter.setData(appInfos);
                    }
                });
            }
        }.start();
    }



    class AppAdapter extends BaseAdapter {

        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();

        public void setData(List<MyAppInfo> myAppInfos) {
            this.myAppInfos = myAppInfos;
            notifyDataSetChanged();
        }

        public List<MyAppInfo> getData() {
            return myAppInfos;
        }

        @Override
        public int getCount() {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            MyAppInfo myAppInfo = myAppInfos.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_app_info, null);
                mViewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
                mViewHolder.tx_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
                mViewHolder.tv_app_code = (TextView) convertView.findViewById(R.id.versionCode);
                mViewHolder.tv_app_updateName = (TextView) convertView.findViewById(R.id.updateName);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_app_icon.setImageDrawable(myAppInfo.getImage());
            mViewHolder.tx_app_name.setText(myAppInfo.getAppName());
            mViewHolder.tv_app_code.setText("版本号："+myAppInfo.getVersionCode()+"");
            mViewHolder.tv_app_updateName.setText("最后更新时间："+longToDate(myAppInfo.getLastUpdateTime()));
            return convertView;
        }

        class ViewHolder {
            ImageView iv_app_icon;
            TextView tx_app_name;
            TextView tv_app_code;
            TextView tv_app_updateName;
        }
        public String longToDate(long lo){
            Date date = new Date(lo);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sd.format(date);
        }

    }

}
