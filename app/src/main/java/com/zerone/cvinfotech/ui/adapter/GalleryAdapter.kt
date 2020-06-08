package com.zerone.cvinfotech.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zerone.cvinfotech.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var clicked = false

    private var listarray = HashMap<String, List<File>>()


    fun setAdapterList(list: HashMap<String, List<File>>) {
        listarray = list

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_items, parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return listarray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageAdapter = ImageAdapter()

        CoroutineScope(Dispatchers.IO).launch {
            holder.count.text = listarray.values.elementAt(position).count().toString()
            imageAdapter.setImageAdapter(listarray.values.elementAt(position))
            CoroutineScope(Dispatchers.Main).launch { holder.gridView.adapter = imageAdapter }
        }

        if (listarray.values.elementAt(position).count() <= 6) {
            holder.count.visibility = GONE
            holder.dropIcon.visibility = GONE
        }

        holder.date.text = listarray.keys.elementAt(position).toString()
        val layoutParams: ViewGroup.LayoutParams = holder.gridView.layoutParams
        layoutParams.height = 500 //this is in pixels
        holder.gridView.layoutParams = layoutParams

        holder.dropIcon.setOnClickListener {
            if (!clicked) {
                val layoutParams: ViewGroup.LayoutParams = holder.gridView.layoutParams
                layoutParams.height = 500 //this is in pixels
                holder.gridView.layoutParams = layoutParams
                holder.dropIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                clicked = true
            } else {
                clicked = true
                holder.dropIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                val layoutParams: ViewGroup.LayoutParams = holder.gridView.layoutParams
                layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
                holder.gridView.layoutParams = layoutParams
                clicked = false
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gridView: GridView = itemView.findViewById<GridView>(R.id.gridView)
        var date: TextView = itemView.findViewById<TextView>(R.id.textView)
        var count: TextView = itemView.findViewById<TextView>(R.id.count)
        var dropIcon: ImageView = itemView.findViewById<ImageView>(R.id.dropIcon)

    }


}