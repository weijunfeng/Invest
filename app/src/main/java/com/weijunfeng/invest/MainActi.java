package com.weijunfeng.invest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.weijunfeng.invest.db.DBManager;
import com.weijunfeng.invest.util.ToastUtils;
import com.weijunfeng.invest.util.UIUtils;

public class MainActi extends AppCompatActivity {
    private long lastClickBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBManager.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.toast("action");
//                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
//                        .setAction("", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ToastUtils.toast("action");
//                            }
//                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (System.currentTimeMillis() - lastClickBackTime <= 2000) {
            UIUtils.exitApp();
        } else {
            lastClickBackTime = System.currentTimeMillis();
            ToastUtils.toast("再按一次返回键退出程序");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_type) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View view = LayoutInflater.from(this).inflate(R.layout.dialog_type, null, false);
            AlertDialog alertDialog = builder.setView(view)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CharSequence identifier = ((TextView) view.findViewById(R.id.identifier)).getText().toString().trim();
                            CharSequence name = ((TextView) view.findViewById(R.id.name)).getText().toString().trim();
                            if (TextUtils.isEmpty(identifier) || TextUtils.isEmpty(name)) {
                                ToastUtils.toast("输入内容不能为空");
                                return;
                            }
                            boolean result = DBManager.getInstance().insertInvestType(identifier, name);
                            if (result) {
                                ToastUtils.toast("插入成功");
                            } else {
                                ToastUtils.toast("失败");
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
//            alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
