package com.example.efpro.miscontactos.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version=1,exportSchema = false)
abstract class ContactDatabase : androidx.room.RoomDatabase(){

    abstract fun ContactDao(): ContactDao

    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase?{
            if(instance ==null){
                synchronized(ContactDatabase::class){
                    instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java, "contact_database"
                    ).fallbackToDestructiveMigration().addCallback(roomCallBack)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstace(){
            instance = null
        }

        private val roomCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }


    class PopulateDbAsyncTask(db: ContactDatabase?):AsyncTask<Unit, Unit, Unit>(){
        private val ContactDao = db?.ContactDao()

        override fun doInBackground(vararg params: Unit?) {
            ContactDao?.insert(Contact("Elvis Presley","12345678","rockking@gmail.com",null,1))
            ContactDao?.insert(Contact("Bill Gates","25896374","billgates@gmail.com",null,2))
        }
    }
}