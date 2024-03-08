package library.gdx.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.gdx.Gdx;

import library.gdx.AndroidLauncher;
import library.gdx.R;
import library.gdx.rooms.DatabaseUtil;
import library.gdx.rooms.NotificationEntityAdapter;
import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.dbs.AppDataBase;

public class NotificationDialog extends Dialog implements DialogInterface.OnShowListener {
    private RecyclerView expandableListView;
    private AndroidLauncher launcher;
    public NotificationDialog(AndroidLauncher launcher) {
        super(launcher, R.style.FullScreenDialog);
        this.launcher=launcher;
        setOnShowListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_notification);
        expandableListView=findViewById(R.id.elv_notification);
        expandableListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onShow(DialogInterface dialog) {
        NotificationEntityDao notificationEntityDao= DatabaseUtil.getAppDataBase(getContext().getApplicationContext()).notificationEntityDao();
        AppDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NotificationEntityAdapter entityAdapter=new NotificationEntityAdapter(launcher,notificationEntityDao.loadAll());
                launcher.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        expandableListView.setAdapter(entityAdapter);
                    }
                });
            }
        });


    }




}
