package io.defy.chicken.lover.model;

import android.content.SharedPreferences;
import com.zeniex.www.zeniexautomarketing.model.LocalChickenInfoModel;
import io.defy.chicken.lover.model.data.UserInfoData;
import io.realm.Realm;
import io.realm.RealmResults;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LocalChickenInfoRepository implements LocalChickenInfoModel {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static LocalChickenInfoRepository instance;
    private static Realm realm;

    public static LocalChickenInfoRepository getInstance() {
        if (instance == null) {
            instance = new LocalChickenInfoRepository();
            realm = Realm.getDefaultInstance();
        }
        return instance;
    }

    @Override
    public void delete() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            realm.delete(UserInfoData.class);
        });
    }

    @Override
    public void insert(@Nullable Integer type, @Nullable Integer uid, @Nullable String hashedValue, @Nullable String guestId, @Nullable String name, @Nullable String id, @Nullable String password) {

        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            Number maxId = realm.where(UserInfoData.class).max("_id");
            // If there are no rows, currentId is null, so the next id must be 1
            // If currentId is not null, increment it by 1
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            // User object created with the new Primary key
            UserInfoData data = realm.createObject(UserInfoData.class, nextId);
            data.setType(type);
            data.setUid(uid);
            data.setHashed_value(hashedValue);
            data.setGuestId(guestId);
            data.setName(name);
            data.setId(id);
            data.setPassword(password);
        });
    }

    @Override
    public void update(@Nullable String hashed_value, @Nullable String guest_id, @Nullable String name) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.beginTransaction();
        long count = realm.where(UserInfoData.class).count();
        if (count > 0) {
            RealmResults<UserInfoData> realmResult = realm.where(UserInfoData.class).findAll();

            realmResult.get(0).setHashed_value(hashed_value);
            realmResult.get(0).setGuestId(guest_id);
            realmResult.get(0).setName(name);
            realmResult.get(0).setType(1);
        }
        realm.commitTransaction();
    }

    @Override
    public UserInfoData select() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        UserInfoData result = null;

        realm.beginTransaction();
        long count = realm.where(UserInfoData.class).count();
        if (count > 0) {
            RealmResults<UserInfoData> realmResult = realm.where(UserInfoData.class).findAll();
            result = realmResult.get(0);
        } else {
            result = null;
        }
        realm.commitTransaction();

        return result;
    }

    @Override
    public List<UserInfoData> selectAll() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<UserInfoData> result = null;

        realm.beginTransaction();
        int count = (int) realm.where(UserInfoData.class).count();
        if (count > 0) {
            result = realm.where(UserInfoData.class).findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<UserInfoData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public void realmClose() {
        realm.close();
    }
}