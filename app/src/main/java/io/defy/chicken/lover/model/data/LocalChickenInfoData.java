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

    public LocalChickenInfoData() {

    }

    public LocalChickenInfoData(String brand, String name) {
        this.brand = brand;
        this.name = name;
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
}