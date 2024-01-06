package com.example.furryfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment_Notifications extends Fragment {
    private RecyclerView recyclerView;
    private List<NotificationItem> notificationList;
    private NotificationAdapter notificationAdapter;
    private FirebaseDatabase database;
    private List<NotificationItem> allNotifications = new ArrayList<>();
    private List<NotificationItem> unreadNotifications = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__notifications, container, false);

        recyclerView = view.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList, new NotificationAdapter.OnNotificationClickListener() {
            @Override
            public void onNotificationClick(NotificationItem item) {
                showNotificationDetails(item);

                // Handle notification click
                if (item.isRead() == 0) {
                    item.setRead(1);
                    unreadNotifications.remove(item);
                    notificationAdapter.notifyItemChanged(notificationList.indexOf(item));
                    updateNotificationReadStatusInFirebase(item.getApplicationId());
                }
            }
        });
        recyclerView.setAdapter(notificationAdapter);

        database = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        updateNotificationList();

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.bringToFront();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterNotifications(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                filterNotifications(tab.getPosition());
            }
        });

        return view;
    }

    private void filterNotifications(int position) {
        if (position == 0) {
            notificationAdapter.updateData(new ArrayList<>(allNotifications));
        } else {
            notificationAdapter.updateData(new ArrayList<>(unreadNotifications));
        }
    }



    private void showNotificationDetails(NotificationItem item) {
        Log.d("NotificationClick", "Remarks value: " + item.getRemarks());
        Fragment_NotificationDetails detailsFragment = new Fragment_NotificationDetails();

        // Passing data to the new Fragment
        Bundle bundle = new Bundle();
        bundle.putString("application_id", item.getApplicationId());
        bundle.putString("petName", item.getPetName());
        bundle.putString("shelterName", item.getShelterName());
        bundle.putString("message", item.getMessage());
        bundle.putString("feedback", item.getFeedback());
        bundle.putInt("remarks", item.getRemarks());
        detailsFragment.setArguments(bundle);

        // Replace the current fragment with detailsFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.FragmentContainer, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateNotificationList() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String adopterId = currentUser.getUid();
            DatabaseReference applicationsRef = database.getReference("applicationform");

            applicationsRef.orderByChild("adopter_id").equalTo(adopterId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Clear the lists to avoid duplication
                            allNotifications.clear();
                            unreadNotifications.clear();

                            for (DataSnapshot applicationSnapshot : dataSnapshot.getChildren()) {
                                String applicationId = applicationSnapshot.getKey();
                                Integer remarks = applicationSnapshot.child("remarks").getValue(Integer.class);
                                Integer status = applicationSnapshot.child("status").getValue(Integer.class);
                                String petId = applicationSnapshot.child("pet_id").getValue(String.class);
                                if (remarks != null && remarks == 1 && petId != null || remarks == 0 && status == 1 && petId != null || remarks == -1 && status == 1 && petId != null || remarks == 2 && petId != null) {
                                    DatabaseReference petsRef = database.getReference("pets").child(petId);
                                    petsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot petSnapshot) {
                                            String shelterId = petSnapshot.child("shelter_id").getValue(String.class);
                                            if (shelterId != null) {
                                                DatabaseReference sheltersRef = database.getReference("shelters").child(shelterId);
                                                sheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot shelterSnapshot) {
                                                        String shelterName = shelterSnapshot.child("shelter_name").getValue(String.class);
                                                        String profilePictureUrl = shelterSnapshot.child("profile_picture").getValue(String.class);
                                                        String message;
                                                        if (remarks == 0) {
                                                            message = "Your application has been disapproved.";
                                                        } else if (remarks == 1) {
                                                            message = "Your application has been approved.";
                                                        } else if (remarks == -1) {
                                                            message = "Your application has been cancelled.";
                                                        } else if (remarks == 2) {
                                                            message = "Please confirm your adoption status.";
                                                        } else {
                                                            message = "";
                                                        }

                                                        NotificationItem newItem = new NotificationItem(
                                                                shelterName,
                                                                profilePictureUrl,
                                                                message,
                                                                applicationId
                                                        );

                                                        newItem.setDateCancelled(applicationSnapshot.child("date_cancelled").getValue(String.class));
                                                        newItem.setDateDisapproved(applicationSnapshot.child("date_disapproved").getValue(String.class));
                                                        newItem.setDateApproved(applicationSnapshot.child("date_approved").getValue(String.class));
                                                        newItem.setDateConfirmationSent(applicationSnapshot.child("date_confirmation_sent").getValue(String.class));
                                                        newItem.setRead(applicationSnapshot.child("is_read").getValue(Integer.class));
                                                        newItem.setApplicationId(applicationId);
                                                        newItem.setPetName(petSnapshot.child("name").getValue(String.class));
                                                        newItem.setFeedback(applicationSnapshot.child("feedback").getValue(String.class));
                                                        newItem.setRemarks(applicationSnapshot.child("remarks").getValue(Integer.class));
                                                        notificationList.add(newItem);

                                                        // After populating notificationList
                                                        for (NotificationItem item : notificationList) {
                                                            Date latestDate = getLatestDate(item);
                                                            item.setLatestDate(latestDate);
                                                        }

                                                        allNotifications.add(newItem);

                                                        if (newItem.isRead() == 0) {
                                                            unreadNotifications.add(newItem);
                                                        }

                                                        // Sort all lists
                                                        sortNotifications(notificationList);
                                                        sortNotifications(allNotifications);
                                                        sortNotifications(unreadNotifications);

                                                        notificationAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        Log.w("FirebaseDatabase", "loadShelter:onCancelled", databaseError.toException());
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.w("FirebaseDatabase", "loadPet:onCancelled", databaseError.toException());
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("FirebaseDatabase", "loadApplicationForm:onCancelled", databaseError.toException());
                        }
                    });
        }
    }

    private void sortNotifications(List<NotificationItem> listToSort) {
        Collections.sort(listToSort, new Comparator<NotificationItem>() {
            @Override
            public int compare(NotificationItem o1, NotificationItem o2) {
                Date date1 = getLatestDate(o1);
                Date date2 = getLatestDate(o2);
                if (date1 == null) {
                    return (date2 == null) ? 0 : 1;
                }
                if (date2 == null) {
                    return -1;
                }
                return date2.compareTo(date1); // For descending order
            }
        });
    }

    private Date getLatestDate(NotificationItem item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date latestDate = null;
        try {
            if (item.getDateApproved() != null) {
                Date dateApproved = sdf.parse(item.getDateApproved());
                latestDate = updateLatestDate(latestDate, dateApproved);
            }
            if (item.getDateDisapproved() != null) {
                Date dateDisapproved = sdf.parse(item.getDateDisapproved());
                latestDate = updateLatestDate(latestDate, dateDisapproved);
            }
            if (item.getDateCancelled() != null) {
                Date dateCancelled = sdf.parse(item.getDateCancelled());
                latestDate = updateLatestDate(latestDate, dateCancelled);
            }
            if (item.getDateConfirmationSent() != null) {
                Date dateConfirmationSent = sdf.parse(item.getDateConfirmationSent());
                latestDate = updateLatestDate(latestDate, dateConfirmationSent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return latestDate;
    }

    private Date updateLatestDate(Date currentLatest, Date newDate) {
        if (currentLatest == null || newDate.after(currentLatest)) {
            return newDate;
        }
        return currentLatest;
    }

    private void updateNotificationReadStatusInFirebase(String applicationId) {
        DatabaseReference notificationRef = database.getReference("applicationform").child(applicationId);
        notificationRef.child("is_read").setValue(1);
    }


}
