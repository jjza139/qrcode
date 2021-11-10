package com.kazimasum.qrdemofirebase;

public class Userinfo {
    public String name;
    public String email;
    public String phone;
    public long money ;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public long getMoney() {
        return money;
    }

    public Userinfo(){}

    public Userinfo(String name,String email, String phone){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.money=0;


    }
    public Userinfo(String name,String email, String phone ,long money){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.money=money;


    }

}
