package com.example.nchatapp0.Model;

//public class Chat {
//    private String sender;
//    private String receiver;
//    private String message;
//
//    public Chat(String sender, String receiver, String message) {
//        this.sender = sender;
//        this.receiver = receiver;
//        this.message = message;
//    }
//
//    public Chat() {
//    }
//
//    public String getSender() {
//        return sender;
//    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }
//
//    public String getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(String receiver) {
//        this.receiver = receiver;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
   // private String time;

    public Chat(String sender, String receiver, String message, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;

    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }


    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
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



    public String getTime() {
//        char[] dz = timestamp.toCharArray();
//        StringBuilder s= new StringBuilder();
//        for(int i=4;i<8;i++){
//            if(i==6)
//                s.append("/");
//            s.append(dz[i]);}
//        return s.toString();
        return timestamp;
    }

}
