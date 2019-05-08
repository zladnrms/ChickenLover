package io.defy.chicken.lover.model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
*
* GUEST 유저 정보
*
* */

public class LocalChickenInfoData extends RealmObject {

    @PrimaryKey
    private int _id;

    private String brand;

    private String name;

    private int type_number;

    private String type_array;

    public LocalChickenInfoData() {

    }

    public LocalChickenInfoData(String brand, String name, int type_number) {
        this.brand = brand;
        this.name = name;
        this.type_number = type_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getType_number() { return type_number; }

    public void setType_number(int type_number) { this.type_number = type_number; }


    public String getType_array() {
        return type_array;
    }

    public void setType_array(String type_array) {
        this.type_array = type_array;
    }

}