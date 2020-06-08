package com.zerone.cvinfotech.permissionUtils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

object PermissionUtils {

    fun requestPermission(activity : Activity,strPermission : String)  : Boolean
    {
        if(!checkPermissionGranted(activity,strPermission)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val permissions = arrayOf(strPermission)
                activity.requestPermissions(permissions,1)
            } else {
                return true
            }
        } else {
            return true
        }
        return false
    }

    fun checkPermissionGranted(
        activity: Activity,
        strPermission: String?
    ): Boolean {
        val res = activity.checkCallingOrSelfPermission(strPermission!!)
        return res == PackageManager.PERMISSION_GRANTED
    }


    fun requestMultiplePermissions(
        activity: Activity,
        strPermission: Array<String>
    ): Boolean {
        if (!checkMultiplePermissionsGranted(activity, strPermission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(strPermission, 1)
            } else {
                return true
            }
        } else {
            return true
        }
        return false
    }

    fun checkMultiplePermissionsGranted(
        activity: Activity,
        strPermission: Array<String>
    ): Boolean {
        for (index in strPermission.indices) {
            val res = activity.checkCallingOrSelfPermission(strPermission[index])
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}