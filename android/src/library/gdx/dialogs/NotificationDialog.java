package library.gdx.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import library.gdx.AndroidLauncher;
import library.gdx.R;
import library.gdx.rooms.NotificationEntityAdapter;
import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.dbs.AppDataBase;
import library.gdx.rooms.dbs.DBCallback;
import library.gdx.rooms.entities.NotificationEntity;

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
        AppDataBase.loadAllNotifications(getContext().getApplicationContext(), new DBCallback<List<NotificationEntity>>() {
            @Override
            public void onCompleted(boolean success, List<NotificationEntity> response) {
                launcher.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NotificationEntityAdapter entityAdapter=new NotificationEntityAdapter(launcher,response);
                        expandableListView.setAdapter(entityAdapter);
                    }
                });
            }
        });
    }




}
