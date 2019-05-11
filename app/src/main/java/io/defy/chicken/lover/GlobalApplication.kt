package io.defy.chicken.lover

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import io.defy.chicken.lover.model.data.AppVersionData
import io.realm.FieldAttribute
import io.realm.Realm
import io.realm.RealmConfiguration

fun main() {

}

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
/*            .schemaVersion(3)
            .migration { realm, oldVersion, newVersion ->
                val schema = realm.schema

                val local = schema.get("LocalChickenInfoData")
                local?.apply {
                    this.addField("brand", String::class.java)
                    this.addField("name", String::class.java)
                } ?: Log.d("GlobalApplication★", "왜없냐")
                //user!!.addField("_id", Int::class.java, FieldAttribute.PRIMARY_KEY)

/*
                         schema.get("UserInfoData")
                        .addField("_id", Int::class.java, FieldAttribute.PRIMARY_KEY)
                        .addField("type", Int::class.java)
                        .addField("uid", Int::class.java)
                        .addField("guestId", String::class.java)
                        .addField("name", String::class.java)
                        .addField("id", String::class.java)
                        .addField("password", String::class.java)
                        */

            }*/
            .build()


            /*
            val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .initialData {
                initialAppVersionData(it, 1)
                initialFavoriteBrandData(it)
                initialFavoriteTypeData(it)
            }
            .build()
            */

        Realm.setDefaultConfiguration(config)
/*
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this)
            // Normal app init code...*/

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}