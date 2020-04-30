package com.fusheng.geeklockscreen

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class MainActivity : AppCompatActivity() {
    private lateinit var policyManager: DevicePolicyManager
    private lateinit var cName: ComponentName
    private val REQUEST_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.ThemeTranslucent)
        transParentStatusBarAndBottomNavigationBar()
        policyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        cName = ComponentName(this, AdminReceiver::class.java)
        lockScreen()
    }

    fun lockScreen() {
        val active: Boolean = policyManager.isAdminActive(cName)
        if (active) {
            policyManager.lockNow()
        } else {
            activeManage()
        }
        finish()
    }

    fun activeManage() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才能使用锁屏哦")
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val active: Boolean = policyManager.isAdminActive(cName)
            if (active) {
                policyManager.lockNow()
            } else {
                finish()
            }
        } else {
            finish()
        }
    }

    fun transParentStatusBarAndBottomNavigationBar() {
        val decorView = window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        decorView.systemUiVisibility = option
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }
}
