package com.zerone.cvinfotech.ui

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zerone.cvinfotech.R
import com.zerone.cvinfotech.ui.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.activity_gallery_activty.*
import kotlinx.coroutines.*
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_activty)

        CoroutineScope(Dispatchers.Main).launch {
            val list = async(Dispatchers.Default) { getFiles() }.await()
            val linearLayoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = linearLayoutManager

            val galleryAdapter =
                GalleryAdapter()
            galleryAdapter.setAdapterList(list)
            recyclerView.adapter = galleryAdapter
        }
    }


    private fun getFiles() : HashMap<String, List<File>> {
        val path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        Log.d("Files", "Path: $path")
        val directory = File(path)
        val files = directory.listFiles()
        Log.d("Files", "Size: " + files.size)
        Arrays.sort(files!!, LastModifiedFileComparator.LASTMODIFIED_REVERSE);

        files.toList()
        val list = files.groupBy { it.name.split("_")[1] }
        Log.e("YYYY", (files.groupBy { it.name.split("_")[1] } .toString()))
        for (element in list) {
            Log.d("Files", "FileName:$element")
        }
        return list as HashMap<String, List<File>>
    }

}