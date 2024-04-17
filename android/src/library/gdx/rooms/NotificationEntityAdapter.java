package library.gdx.rooms;

import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import library.gdx.AndroidLauncher;
import library.gdx.R;
import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.dbs.AppDataBase;
import library.gdx.rooms.entities.NotificationEntity;

public class NotificationEntityAdapter extends RecyclerView.Adapter<NotificationEntityAdapter.ViewHolder> {

    private AndroidLauncher launcher;
    private List<NotificationEntity> entities;
    public NotificationEntityAdapter(AndroidLauncher context, List<NotificationEntity> entities){
        this.launcher =context;
        this.entities=entities;
        Log.e("Total Entities",entities.size()+"");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_notification, parent, false);
        return new ViewHolder(launcher,this,view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(entities.get(position),position);
    }


    @Override
    public int getItemCount() {
        return entities.size();
    }

    public void removeNotification(int position){
        entities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, entities.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView messageWrapText,messageExpanded,timeText;
        private Button actionButton;
        private LinearLayout menuButton;
        private boolean expanded;
        private NotificationEntity notificationEntity;
        private AndroidLauncher launcher;
        private NotificationEntityAdapter adapter;
        private int position=-1;

        public ViewHolder(AndroidLauncher launcher,NotificationEntityAdapter adapter,@NonNull View itemView) {
            super(itemView);
            this.launcher =launcher;
            this.adapter=adapter;
            titleText=itemView.findViewById(R.id.tv_title);
            messageWrapText=itemView.findViewById(R.id.tv_message_wrapped);
            messageExpanded=itemView.findViewById(R.id.tv_message_expanded);
            actionButton=itemView.findViewById(R.id.btn_action);
            menuButton=itemView.findViewById(R.id.ll_menu);
            timeText=itemView.findViewById(R.id.tv_time);

            expanded=false;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationClicked();
                }
            });
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notificationEntity!=null){
                        openMenu(menuButton);
                    }
                }
            });
        }

        private void onNotificationClicked(){
            if (!notificationEntity.read && !expanded){
                notificationEntity.read=true;
                changeReadStatus(false,null,null);
            }
            expanded=!expanded;
            onExpanded();
        }

        public void setData(NotificationEntity notificationEntity,int position){
            this.notificationEntity=notificationEntity;
            this.position=position;
            titleText.setText(notificationEntity.title);
            timeText.setText(getTime(notificationEntity.time));
            changeReadStatus(true,null,null);
//            messageWrapText.setText(notificationEntity.message);
//            messageExpanded.setText("Expanded"+notificationEntity.message);
            actionButton.setTag(notificationEntity.action);
            expanded=false;
            onExpanded();
        }

        public void onExpanded(){
            if (expanded){
                messageWrapText.setVisibility(View.GONE);
                messageExpanded.setVisibility(View.VISIBLE);
                actionButton.setVisibility(View.VISIBLE);
            }else {
                messageWrapText.setVisibility(View.VISIBLE);
                messageExpanded.setVisibility(View.GONE);
                actionButton.setVisibility(View.GONE);
            }
        }

        public void openMenu(LinearLayout menuView){
            LayoutInflater inflater = (LayoutInflater)launcher.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.layout_notification_menu, null);
            LinearLayout readLayout=popupView.findViewById(R.id.ll_read);
            TextView textView=popupView.findViewById(R.id.tv_read);
            TextView delete=popupView.findViewById(R.id.tv_delete);
            ImageView imageView=popupView.findViewById(R.id.iv_check);
            textView.setTextColor(launcher.getResources().getColor(notificationEntity.read?R.color.notification_message_color:R.color.notification_title_color));
            imageView.setVisibility(notificationEntity.read?View.VISIBLE:View.INVISIBLE);


            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            readLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationEntity.read=true;
                    changeReadStatus(false,textView,imageView);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    NotificationEntityDao notificationEntityDao=AppDataBase.getInstance(launcher).notificationEntityDao();
                    AppDataBase.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            notificationEntityDao.delete(notificationEntity);
                            launcher.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.removeNotification(position);
                                }
                            });
                        }
                    });
                }
            });
            popupWindow.showAsDropDown(menuView,-menuView.getWidth(),-menuView.getHeight(),Gravity.NO_GRAVITY);

        }

        private void changeReadStatus(boolean updateViewOnly, TextView popupTextView, ImageView popupImageView){
            if (updateViewOnly){
                updateView();
            }else {
                NotificationEntityDao notificationEntityDao=AppDataBase.getInstance(launcher).notificationEntityDao();
                AppDataBase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        notificationEntityDao.update(notificationEntity);
                        launcher.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (popupTextView!=null && popupImageView!=null){
                                        popupTextView.setTextColor(launcher.getResources().getColor(notificationEntity.read ? R.color.notification_message_color : R.color.notification_title_color));
                                        popupImageView.setVisibility(notificationEntity.read ? View.VISIBLE : View.INVISIBLE);
                                    }
                                updateView();
                            }
                        });
                    }
                });
            }

            boolean read= notificationEntity.read;
            if (read){
                titleText.setTextColor(launcher.getResources().getColor(R.color.notification_message_color));
            }else {
                titleText.setTextColor(launcher.getResources().getColor(R.color.notification_title_color));
            }
        }

        private void updateView(){
            boolean read= notificationEntity.read;
            if (read){
                titleText.setTextColor(launcher.getResources().getColor(R.color.notification_message_color));
            }else {
                titleText.setTextColor(launcher.getResources().getColor(R.color.notification_title_color));
            }
        }

        private String getTime(long time){
            long elapsedTime=System.currentTimeMillis()-time;
            return parseElapsedTime(TimeUnit.MILLISECONDS.toMinutes(elapsedTime));
        }

        public String parseElapsedTime(long timeLapsedInMinutes) {
            if (timeLapsedInMinutes < 0)
                return "Recently";

            if (timeLapsedInMinutes == 0)
                return "Just Now";

            long days= TimeUnit.MILLISECONDS.toDays(timeLapsedInMinutes);

            int h = (int) (timeLapsedInMinutes / 60);
            int m = (int) (timeLapsedInMinutes % 60);

            if (h > 0) {
                if (h >= 24) {
                    int day = h / 24;
                    return day + " "+"day ago";
                } else if (m > 0) {
                    return h + "h " + m + "m ago";
                } else {
                    return h + " h ago";
                }
            }
            return m + " m ago";
        }
    }


}
