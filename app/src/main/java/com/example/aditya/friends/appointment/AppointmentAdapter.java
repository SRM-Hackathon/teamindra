package com.example.aditya.friends.appointment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.Appointment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>{

    private Context mContext;
    private ArrayList<Appointment> mAppointmentList;


    public AppointmentAdapter(Context context, ArrayList<Appointment> appointmentList){
        mAppointmentList = appointmentList;
        mContext = context;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_appointments, viewGroup, false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder appointmentViewHolder, int position) {
        appointmentViewHolder.Bind(mAppointmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private TextView mDate;
        private TextView mTime;
        private TextView mLocation;

        public AppointmentViewHolder(View view){
            super(view);
            mName = (TextView) view.findViewById(R.id.list_item_appointment_name);
            mDate = (TextView) view.findViewById(R.id.list_item_appointment_date);
            mTime = (TextView) view.findViewById(R.id.list_item_appointment_time);
            mLocation = (TextView) view.findViewById(R.id.list_item_appointment_location);
        }

        public void Bind(final Appointment appointment) {
            mName.setText(appointment.getUniqueIdYoung());
            mDate.setText(appointment.getDate());
            mTime.setText(appointment.getTime());
            mLocation.setText(appointment.getLocation());
        }
    }




}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               