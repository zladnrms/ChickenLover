package io.defy.chicken.lover.model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
*
* GUEST 유저 정보
*
* */

public class FavoriteBrandData extends RealmObject {

    @PrimaryKey
    private int _id;

    private int uid; // brand uid

    private String brandName; // brand name

    private String imgUrlName; // brand img file name for url connection

    private boolean checked; // check status

    public FavoriteBrandData() {

    }

    public FavoriteBrandData(int uid, String brandName, String imgUrlName, boolean checked) {
        this.uid = uid;
        this.brandName = brandName;
        this.imgUrlName = imgUrlName;
        this.checked = checked;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImgUrlName() {
        return imgUrlName;
    }

    public void setImgUrlName(String imgUrlName) {
        this.imgUrlName = imgUrlName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}