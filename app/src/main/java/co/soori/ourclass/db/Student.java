package co.soori.ourclass.db;

import com.google.gson.Gson;

/**
 * Created by Soo-Young on 2018-02-25.
 */

public class Student {

    private int _id;
    private String name;
    private String phone;
    private String image;
    private String etc1;
    private String etc2;

    public Student(){}

    public Student(String name){
        this.name = name;
    }

    public Student(int _id, String name){
        this._id = _id;
        this.name = name;
    }

    public Student(int _id, String name, String phone){
        this._id = _id;
        this.name = name;
        this.phone = phone;
    }

    public int get_id(){
        return _id;
    }

    public void set_id(int _id){
        this._id = _id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getEtc1(){
        return etc1;
    }

    public void setEtc1(String etc1){
        this.etc1 = etc1;
    }

    public String getEtc2(){
        return etc2;
    }

    public void setEtc2(String etc2){
        this.etc2 = etc2;
    }

    @Override
    public String toString(){
        return "Student = id: "+ _id + ", name: "+name+", phone: "+phone+", image: "+image+"etc1: "
                +etc1+", etc12: "+etc2;
    }

    public String getJson(){
        Gson gson = new Gson();
        return gson.toJson(Student.class);
    }

}
