package com.example.aditya.friends.utils;

import android.graphics.Bitmap;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.android.MediaManager;
import com.example.aditya.friends.api.OldPerson;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FriendsUtils {

    public final static String BASE_URL = "http://431fdfd9.ngrok.io";
    public final static int PERMISSION_ACCESS_FINE_LOCATION = 1001;

    public static Bitmap profilePic = null;


    public static OldPerson mOldPersonData = new OldPerson();

    public static String getAgeFromBirthday(String birthday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(birthday + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(60 * 31556952000L);
        }
        long ageMilliseconds = System.currentTimeMillis() - date.getTime();
        return (ageMilliseconds / 31556952000L) + "";
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static byte[] getByteFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }
}

