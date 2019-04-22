package io.defy.chicken.lover.model;

import android.content.SharedPreferences;
import com.zeniex.www.zeniexautomarketing.model.AppVersionDataModel;
import io.defy.chicken.lover.model.data.AppVersionData;
import io.defy.chicken.lover.model.data.UserInfoData;
import io.realm.Realm;
import io.realm.RealmResults;

public class AppVersionDataRepository implements AppVersionDataModel {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static AppVersionDataRepository instance;
    private static Realm realm;

    public static AppVersionDataRepository getInstance() {
        if (instance == null) {
            instance = new AppVersionDataRepository();
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
    public void insert(int chickenInfo) {

        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            Number maxId = realm.where(AppVersionData.class).max("_id");
            // If there are no rows, currentId is null, so the next id must be 1
            // If currentId is not null, increment it by 1
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            // User object created with the new Primary key
            AppVersionData data = realm.createObject(AppVersionData.class, nextId);
        });
    }

    @Override
    public void update(int versionCode) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.beginTransaction();
        long count = realm.where(AppVersionData.class).count();
        if (count > 0) {
            RealmResults<AppVersionData> realmResult = realm.where(AppVersionData.class).findAll();

            realmResult.get(0).setVersionCode(versionCode);
        }
        realm.commitTransaction();
    }

    @Override
    public int selectVersionCode() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        int result = 0;

        realm.beginTransaction();
        long count = realm.where(AppVersionData.class).count();
        if (count > 0) {
            RealmResults<AppVersionData> realmResult = realm.where(AppVersionData.class).findAll();
            result = realmResult.get(0).getVersionCode();
        } else {
            result = 0;
        }
        realm.commitTransaction();

        return result;
    }

    @Override
    public void realmClose() {
        realm.close();
    }
}