package com.example.miscontactos

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.miscontactos.models.Contact

class CustomAdapter(var context: Context, var contactlist:ArrayList<Contact>): BaseAdapter() {

    private class ViewHolder(row: View?){
        var txtName: TextView
        var txtPhone: TextView
        var txtMail: TextView
        var ivImage: ImageView

        init {
            this.txtName = row?.findViewById(R.id.name) as TextView
            this.txtPhone = row?.findViewById(R.id.phone) as TextView
            this.txtMail = row?.findViewById(R.id.Mail) as TextView
            this.ivImage = row?.findViewById(R.id.photo) as ImageView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView==null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.activity_list_item,convertView,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }



        var contact:Contact = getItem(position) as Contact

        viewHolder.txtName.text = contact.nombre
        viewHolder.txtPhone.text = (contact.tele)
        viewHolder.txtMail.text = contact.email
        viewHolder.ivImage.setImageResource(contact.image)



        return view as View


    }

    override fun getItem(position: Int): Any {
        return contactlist.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return contactlist.count()
    }
}