package com.example.nchatapp0.Model;

public class Gchat {
    private String username;
    private String message;
    private String timestamp;
    private String time;
    private String image;

    public Gchat(String username, String message, String timestamp,String image) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
       // this.time = time;
        this.image = image;
    }

    public Gchat() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        char[] d =timestamp.toCharArray();
        StringBuilder st= new StringBuilder();
        for(int i=4;i<8;i++){
            if(i==6)
                st.append("/");
            st.append(d[i]);}
        st.append("\t");
        for(int i=9;i<15;i++){
            if(i==11||i==13)
                st.append(":");
            st.append(d[i]);}
        return st.toString();
    }

    public String getImage(){
        return image;
    }

    public void setDate(String date) {
        this.timestamp = date;
    }

//    public String getTime() {
//        return time;
//    }

    public void setTime(String time) {
        this.time = time;
    }
}
