package com.main;

import java.util.ArrayList;

public class Profile {
    private String name;
    private String pass;
    private ArrayList<String> topics;

    public Profile(String name, String pass){
        this.name = name;
        this.pass = pass;
        topics = new ArrayList<String>();
    }

    public void changeName(String n){
        name = n;
    }

    public void changePass(String pa){
        pass = pa;
    }

    public int addTopic(String topic){
        topics.add(topic);

        return topics.size();
    }

    public String getName(){
        return name;
    }
    public String getPass(){
        return pass;
    }
    public int getNumTopics(){
        return topics.size();
    }
    public String getTopic(int slot){
        return topics.get(slot);
    }
    public boolean verifyAccount(String n, String pa){
        return n.equals(name) && pa.equals(pass);
    }

    public String toString(){
        return("Name: "+ name +"\nPassword: " + pass + "\nYour topics: " + topics);
    }
}
