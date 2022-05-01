package storage

import android.content.Context
import android.content.SharedPreferences
import models.Model

class SharedPrefManager {
    val SHARED_PREF_NAME: String = "my_shared_preff"
    lateinit var mInstance: SharedPrefManager
    private lateinit var mCtx: Context
    fun SharedPrefManager(mCtx: Context): SharedPrefManager {
        this.mCtx = mCtx
        return mInstance
    }
    //  var mCtx: Context

//    constructor(mCtx: Context) {
//        this.mCtx = mCtx
//    }


    fun   getInstance(mCtx: Context):   SharedPrefManager  {

        if (mInstance == null) {
            mInstance = SharedPrefManager()
        }
        return mInstance
    }


    fun saveUser(user: Model) {
        var sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        // editor.putInt("id", user.getId());
        editor.putString("name", user.name)
        editor.putString("email", user.email)

        //editor.putstring("school", user.getSchool());
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        var sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", -1) != -1
    }

    fun getUser(): Model {
        var sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return Model(
            sharedPreferences.getString("name", "none"),
            sharedPreferences.getString("email", "none").toString(),
            null, null
        )
    }

    fun clear() {
        var sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

    }

}

//    public static synchronized SharedPrefManager getInstance(Context mCtx) {
//        if (mInstance == null) {
//            mInstance = new SharedPrefManager (mCtx);
//        }
//        return mInstance;

