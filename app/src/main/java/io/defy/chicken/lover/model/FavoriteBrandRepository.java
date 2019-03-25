package io.defy.chicken.lover.model;

import android.content.SharedPreferences;
import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel;
import io.defy.chicken.lover.model.data.FavoriteBrandData;
import io.realm.Realm;
import io.realm.RealmResults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FavoriteBrandRepository implements FavoriteBrandRepositoryModel {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static FavoriteBrandRepository instance;
    private static Realm realm;

    public static FavoriteBrandRepository getInstance() {
        if (instance == null) {
            instance = new FavoriteBrandRepository();
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
            realm.delete(FavoriteBrandData.class);
        });
    }

    @Override
    public void insert(@Nullable Integer uid, @Nullable String brandName, @Nullable String imgUrlName, @Nullable Boolean checked) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            Number maxId = realm.where(FavoriteBrandData.class).max("_id");
            // If there are no rows, currentId is null, so the next id must be 1
            // If currentId is not null, increment it by 1
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            // User object created with the new Primary key
            FavoriteBrandData data = realm.createObject(FavoriteBrandData.class, nextId);
            data.setUid(uid);
            data.setBrandName(brandName);
            data.setImgUrlName(imgUrlName);
            data.setChecked(checked);
        });
    }

    @Override
    public boolean setCheck(@Nullable Integer position) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        FavoriteBrandData result = null;

        realm.beginTransaction();
        long count = realm.where(FavoriteBrandData.class).count();
        if (count > 0) {
            RealmResults<FavoriteBrandData> realmResult = realm.where(FavoriteBrandData.class).findAll();
            result = realmResult.get(position);
            if(result.getChecked())
            {
                result.setChecked(false);
            }
            else
            {
                result.setChecked(true);
            }
        }

        realm.commitTransaction();

        return true;
    }

    @NotNull
    @Override
    public List<FavoriteBrandData> selectByPaging(int page) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<FavoriteBrandData> result = null;

        realm.beginTransaction();
        int count = (int)realm.where(FavoriteBrandData.class).count();
        if (count > 0) {
            result = realm.where(FavoriteBrandData.class)
                    .greaterThanOrEqualTo("uid", (page - 1) * 9)
                    .lessThan("uid", page * 9)
                    .findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<FavoriteBrandData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public FavoriteBrandData select() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        FavoriteBrandData result = null;

        realm.beginTransaction();
        long count = realm.where(FavoriteBrandData.class).count();
        if (count > 0) {
            RealmResults<FavoriteBrandData> realmResult = realm.where(FavoriteBrandData.class).findAll();
            result = realmResult.get(0);
        } else {
            result = null;
        }
        realm.commitTransaction();

        return result;
    }


    @Override
    public List<FavoriteBrandData> selectAll() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<FavoriteBrandData> result = null;

        realm.beginTransaction();
        int count = (int)realm.where(FavoriteBrandData.class).count();
        if (count > 0) {
            result = realm.where(FavoriteBrandData.class).findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<FavoriteBrandData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public void realmClose() {
        realm.close();
    }
}