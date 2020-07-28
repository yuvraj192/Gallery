package com.yuvraj.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuvraj.gallery.GalleryAdapter.PhotoListener
import com.yuvraj.gallery.MainActivity

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var galleryAdapter: GalleryAdapter? = null
    var images: List<String>? = null
    var gallery_number: TextView? = null

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gallery_number = findViewById(R.id.gallery_number)
        recyclerView = findViewById(R.id.recyclerview_gallery_images)
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_READ_PERMISSION_CODE)
        } else {
            loadImages()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun loadImages() {
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(this, 4)
        images = ImagesGallery.listOfImages(this)
        galleryAdapter = GalleryAdapter(this, images, PhotoListener { path -> Toast.makeText(this@MainActivity, "" + path, Toast.LENGTH_SHORT).show() })
        recyclerView!!.adapter = galleryAdapter
        gallery_number!!.text = "Photos(" + images + ")"
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read External Storage Permission Granted", Toast.LENGTH_SHORT).show()
                loadImages()
            } else {
                Toast.makeText(this, "Read External Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val MY_READ_PERMISSION_CODE = 101
    }
}