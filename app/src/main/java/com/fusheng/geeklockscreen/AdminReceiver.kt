package com.fusheng.geeklockscreen

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * @author lixiaowei
 * @date:2020/4/30 15:45
 * Description:
 */
class AdminReceiver : DeviceAdminReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("lixiaowei", "收到了")
    }
}