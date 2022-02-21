package com.kazimasum.qrdemofirebase;

public class Userinfo {
    public String name;
    public String email;
    public String phone;
    public String plate;
    public String permission;
    public long money ;

    public String getName() {
        return name;
    }
    public String getPermission(){return permission;}

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getplate(){return plate;}

    public long getMoney() {
        return money;
    }

    public Userinfo(){}

    public Userinfo(String name,String email, String phone,String plate){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.money=0;
        this.plate=plate;


    }
    public Userinfo(String name,String email, String phone ,long money,String plate){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.money=money;
        this.plate=plate;


    }

}
