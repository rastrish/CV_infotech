package com.zerone.cvinfotech.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.zerone.cvinfotech.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class ImageAdapter : BaseAdapter() {

    var inflater: LayoutInflater? = null

    lateinit var imageList: List<File>

    fun setImageAdapter(list: List<File>) {

        imageList = list

    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val retView: View


        if (convertView == null) {
            inflater = LayoutInflater.from(parent!!.context);
            retView = inflater!!.inflate(R.layout.grid_layout, null)
            holder = ViewHolder()
            holder.itemImage = retView.findViewById(R.id.imageView) as ImageView?
            retView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }

        CoroutineScope(Dispatchers.IO).launch {
            val imgFile = File(imageList[position].toString())
            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                CoroutineScope(Dispatchers.Main).launch {
                    holder.itemImage!!.setImageBitmap(myBitmap)
                }
            }
        }
        return retView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imageList.size
    }


}


internal class ViewHolder {
    var itemImage: ImageView? = null
}