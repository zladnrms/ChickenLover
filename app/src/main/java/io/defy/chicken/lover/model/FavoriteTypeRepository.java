package io.defy.chicken.lover.model;

import android.content.SharedPreferences;
import com.zeniex.www.zeniexautomarketing.model.FavoriteTypeRepositoryModel;
import io.defy.chicken.lover.model.data.FavoriteTypeData;
import io.realm.Realm;
import io.realm.RealmResults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FavoriteTypeRepository implements FavoriteTypeRepositoryModel {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static FavoriteTypeRepository instance;
    private static Realm realm;

    public static FavoriteTypeRepository getInstance() {
        if (instance == null) {
            instance = new FavoriteTypeRepository();
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
            realm.delete(FavoriteTypeData.class);
        });
    }

    @Override
    public void insert(@Nullable Integer uid, @Nullable String typeName, @Nullable String imgUrlName, @Nullable Boolean checked) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        realm.executeTransaction(realm1 -> {
            Number maxId = realm.where(FavoriteTypeData.class).max("_id");
            // If there are no rows, currentId is null, so the next id must be 1
            // If currentId is not null, increment it by 1
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            // User object created with the new Primary key
            FavoriteTypeData data = realm.createObject(FavoriteTypeData.class, nextId);
            data.setUid(uid);
            data.setTypeName(typeName);
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

        FavoriteTypeData result = null;

        realm.beginTransaction();
        long count = realm.where(FavoriteTypeData.class).count();
        if (count > 0) {
            RealmResults<FavoriteTypeData> realmResult = realm.where(FavoriteTypeData.class).findAll();
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
    public List<FavoriteTypeData> selectByPaging(int page) {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<FavoriteTypeData> result = null;

        realm.beginTransaction();
        int count = (int)realm.where(FavoriteTypeData.class).count();
        if (count > 0) {
            result = realm.where(FavoriteTypeData.class)
                    .greaterThanOrEqualTo("uid", (page - 1) * 9)
                    .lessThan("uid", page * 9)
                    .findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<FavoriteTypeData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public FavoriteTypeData select() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        FavoriteTypeData result = null;

        realm.beginTransaction();
        long count = realm.where(FavoriteTypeData.class).count();
        if (count > 0) {
            RealmResults<FavoriteTypeData> realmResult = realm.where(FavoriteTypeData.class).findAll();
            result = realmResult.get(0);
        } else {
            result = null;
        }
        realm.commitTransaction();

        return result;
    }


    @Override
    public List<FavoriteTypeData> selectAll() {
        if (realm != null && !realm.isClosed()) {
        } else {
            realm = Realm.getDefaultInstance();
        }

        RealmResults<FavoriteTypeData> result = null;

        realm.beginTransaction();
        int count = (int)realm.where(FavoriteTypeData.class).count();
        if (count > 0) {
            result = realm.where(FavoriteTypeData.class).findAll();
        } else {
            result = null;
        }
        realm.commitTransaction();

        List<FavoriteTypeData> arrResult = realm.copyFromRealm(result);

        return arrResult;
    }

    @Override
    public void realmClose() {
        realm.close();
    }
}