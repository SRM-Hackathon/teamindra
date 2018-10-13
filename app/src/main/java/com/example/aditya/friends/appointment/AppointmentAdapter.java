package com.example.aditya.friends.appointment;

import android.content.Context;

import com.example.aditya.friends.api.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter {

    private Context mContext;
    private ArrayList<Appointment> mAppointmentList;
    private OnItemClickListner mOnItemClickListner;

    public interface OnItemClickListner{
        void onItemClick(Appointment appointment);
    }



}
