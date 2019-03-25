package io.defy.chicken.lover.model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
*
* GUEST 유저 정보
*
* */

public class FirstVisitFlagData extends RealmObject {

    @PrimaryKey
    private int _id;

    private int flag = 1; // 0 : Not First , 1 : First

    public FirstVisitFlagData() {

    }

    public FirstVisitFlagData(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}