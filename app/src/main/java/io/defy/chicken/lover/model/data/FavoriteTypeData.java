package io.defy.chicken.lover.model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
*
* GUEST 유저 정보
*
* */

public class FavoriteTypeData extends RealmObject {

    @PrimaryKey
    private int _id;

    private int uid; // brand uid

    private String typeName; // brand name

    private String imgUrlName; // brand img file name for url connection

    private boolean checked; // check status

    public FavoriteTypeData() {

    }

    public FavoriteTypeData(int uid, String typeName, String imgUrlName, boolean checked) {
        this.uid = uid;
        this.typeName = typeName;
        this.imgUrlName = imgUrlName;
        this.checked = checked;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String TypeName) {
        this.typeName = TypeName;
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