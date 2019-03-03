package com.example.efpro.miscontactos.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.efpro.miscontactos.R
import kotlinx.android.synthetic.main.activity_crear.*
import java.io.ByteArrayOutputStream

@Database(entities = [Contact::class], version=2,exportSchema = false)
abstract class ContactDatabase : androidx.room.RoomDatabase(){

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "contacts_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallBack)
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
        private val ContactDao = db?.contactDao()

        override fun doInBackground(vararg params: Unit?) {
            //ContactDao?.insert(Contact("Elvis Presley","12345678","rockking@gmail.com",1,imagen))
            //ContactDao?.insert(Contact("Bill Gates","25896374","billgates@gmail.com",2,imagen))
        }

        fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            return stream.toByteArray()
        }
    }
}