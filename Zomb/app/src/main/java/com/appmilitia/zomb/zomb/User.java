package com.appmilitia.zomb.zomb;

/**
 * Created by Salman on 12-01-2018.
 */

public class User {
    //String first_name, last_name, email, id, birthday, gender;
    //double latti,longi;
    String user_fb_first_name,user_fb_last_name,user_fb_email,user_fb_id,user_fb_birthday,user_fb_gender,user_zomb_status;
    double user_current_latti,user_current_longi;

    public User(){

    }

    public User(String user_fb_first_name, String user_fb_last_name, String user_fb_email, String user_fb_id, String user_fb_birthday, String user_fb_gender, String user_zomb_status, double user_current_latti, double user_current_longi) {
        this.user_fb_first_name = user_fb_first_name;
        this.user_fb_last_name = user_fb_last_name;
        this.user_fb_email = user_fb_email;
        this.user_fb_id = user_fb_id;
        this.user_fb_birthday = user_fb_birthday;
        this.user_fb_gender = user_fb_gender;
        this.user_zomb_status = user_zomb_status;
        this.user_current_latti = user_current_latti;
        this.user_current_longi = user_current_longi;
    }

    public String getUser_fb_first_name() {
        return user_fb_first_name;
    }

    public String getUser_fb_last_name() {
        return user_fb_last_name;
    }

    public String getUser_fb_email() {
        return user_fb_email;
    }

    public String getUser_fb_id() {
        return user_fb_id;
    }

    public String getUser_fb_birthday() {
        return user_fb_birthday;
    }

    public String getUser_fb_gender() {
        return user_fb_gender;
    }

    public String getUser_zomb_status() {
        return user_zomb_status;
    }

    public double getUser_current_latti() {
        return user_current_latti;
    }

    public double getUser_current_longi() {
        return user_current_longi;
    }
}
