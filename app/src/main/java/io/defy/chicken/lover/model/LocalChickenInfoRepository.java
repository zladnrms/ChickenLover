package io.defy.chicken.lover.model;

import android.content.SharedPreferences;
import com.zeniex.www.zeniexautomarketing.model.LocalChickenInfoModel;
import io.defy.chicken.lover.model.data.LocalChickenInfoData;
import io.defy.chicken.lover.model.data.UserInfoData;
import io.realm.Realm;
import io.realm.RealmResults;
import org.jetbrains.annotations.NotNull;
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
    public void insert(@Nullable String brand, @Nullable String name) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            Number maxId = realm.where(LocalChickenInfoData.class).max("_id");
            // If there are no rows, currentId is null, so the next id must be 1
            // If currentId is not null, increment it by 1
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            // User object created with the new Primary key
            LocalChickenInfoData data = realm.createObject(LocalChickenInfoData.class, nextId);
            data.setBrand(brand);
            data.setName(name);
        });
    }

    @Override
    public LocalChickenInfoData select(@NotNull String text) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        LocalChickenInfoData result = null;

        realm.beginTransaction();
        long count = realm.where(LocalChickenInfoData.class).count();
        if (count > 0) {
            RealmResults<LocalChickenInfoData> realmResult = realm.where(LocalChickenInfoData.class).findAll();
            result = realmResult.get(0);
        } else {
            result = null;
        }
        realm.commitTransaction();

        return result;
    }

    @Override
    public List<LocalChickenInfoData> selectAll() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<LocalChickenInfoData> result = null;

        realm.beginTransaction();
        int count = (int) realm.where(LocalChickenInfoData.class).count();
        if (count > 0) {
            result = realm.where(LocalChickenInfoData.class).findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<LocalChickenInfoData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public void realmClose() {
        realm.close();
    }
}