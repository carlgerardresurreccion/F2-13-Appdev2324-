package com.example.furryfound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationItem> notificationList;
    private OnNotificationClickListener listener;

    public NotificationAdapter(List<NotificationItem> notificationList, OnNotificationClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationItem notificationItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;
        private TextView shelterNameTextView;
        private ImageView shelterProfileImageView;
        private TextView feedbackTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shelterNameTextView = itemView.findViewById(R.id.shelterNameTextView);
            shelterProfileImageView = itemView.findViewById(R.id.shelterProfileImageView);
            feedbackTextView = itemView.findViewById(R.id.messageTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onNotificationClick(notificationList.get(getAdapterPosition()));
                    }
                }
            });
        }

        public void bind(NotificationItem item) {
            shelterNameTextView.setText(item.getShelterName());
            feedbackTextView.setText(item.getFirstLineOfMessage());

            if (item.getShelterProfilePictureUrl() != null && !item.getShelterProfilePictureUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(item.getShelterProfilePictureUrl())
                        .placeholder(R.drawable.profile)
                        .into(shelterProfileImageView);
            } else {
                // If there is no profile picture URL, set a default image
                shelterProfileImageView.setImageResource(R.drawable.profile);
            }
        }

    }
}
