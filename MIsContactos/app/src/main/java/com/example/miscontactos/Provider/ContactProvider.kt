package com.example.miscontactos.Provider

import android.Manifest.permission_group.CONTACTS
import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.Telephony.Mms.Addr.CONTACT_ID
import android.text.TextUtils
import java.util.HashMap

class ContactProvider : ContentProvider() {

    private var db: SQLiteDatabase? = null

    private class DatabaseHelper(context: Context) :

        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $CONTACTS_TABLE_NAME")
            onCreate(db)
        }
    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)

        /*Creamos base de datos*/
        db = dbHelper.writableDatabase
        return db != null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        /*Agregar un nuevo contacto*/
        val rowID = db!!.insert(CONTACTS_TABLE_NAME, "", values)

        /*Si agrega correctamente*/
        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }

        throw SQLException("Failed to add a record into $uri")
    }

    override fun query(
        uri: Uri, projection: Array<String>?,
        selection: String?, selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        //        @Suppress("NAME_SHADOWING") var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = CONTACTS_TABLE_NAME

        when (uriMatcher.match(uri)) {
            CONTACTS -> qb.setProjectionMap(CONTACTS_PROJECTION_MAP)

            CONTACT_ID -> qb.appendWhere(_ID + "=" + uri.pathSegments[1])
        }

        val c = qb.query(
            db, projection, selection,
            selectionArgs, null, null, sortOrder ?: NAME
        )

        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            CONTACTS -> count = db!!.delete(CONTACTS_TABLE_NAME, selection, selectionArgs)

            CONTACT_ID -> {
                val id = uri.pathSegments[1]
                count = db!!.delete(
                    CONTACTS_TABLE_NAME, _ID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri, values: ContentValues?,
        selection: String?, selectionArgs: Array<String>?
    ): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            CONTACTS -> count = db!!.update(CONTACTS_TABLE_NAME, values, selection, selectionArgs)

            CONTACT_ID -> count = db!!.update(
                CONTACTS_TABLE_NAME, values,
                _ID + " = " + uri.pathSegments[1] +
                        if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs
            )
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            /*OBTENER TODOS LOS DATOS*/
            CONTACTS -> return "vnd.android.cursor.dir/vnd.example.contacts"
            /**
             * Get a particular student
             */
            CONTACT_ID -> return "vnd.android.cursor.item/vnd.example.contacts"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    companion object {
        internal val PROVIDER_NAME = "com.example.miscontactos.Provider.ContactProvider"
        internal val URL = "content://$PROVIDER_NAME/contacts"
        internal val CONTENT_URI = Uri.parse(URL)

        internal val _ID = "_id"
        internal val NAME = "name"
        internal val PHONE = "phone"
        internal val EMAIL = "email"
        internal val IMAGEN = "imagen"

        private val CONTACTS_PROJECTION_MAP: HashMap<String, String>? = null

        internal val CONTACTS = 1
        internal val CONTACT_ID = 2

        internal val uriMatcher: UriMatcher

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS)
            uriMatcher.addURI(PROVIDER_NAME, "Contacto/#", CONTACT_ID)
        }

        internal val DATABASE_NAME = "AGENDA"
        internal val CONTACTS_TABLE_NAME = "contactos"
        internal val DATABASE_VERSION = 1
        internal val CREATE_DB_TABLE = " CREATE TABLE " + CONTACTS_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " phone TEXT NOT NULL, " +
                " email TEXT NOT NULL, " +
                " imagen INTEGER);"
    }

}
