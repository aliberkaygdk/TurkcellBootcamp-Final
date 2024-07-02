package com.aliberkaygedikoglu.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aliberkaygedikoglu.finalproject.databinding.ActivityMainBinding
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController:NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var mRemoteConfig: FirebaseRemoteConfig
    lateinit var editor: Editor
    lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        binding.navigationView.setupWithNavController(navController)


        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment, R.id.profileFragment, R.id.categoriesFragment, R.id.searchFragment, R.id.likedsFragment, R.id.ordersFragment),binding.main)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val exit = binding.navigationView.menu.findItem(R.id.exit)
        exit.setOnMenuItemClickListener(object : OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                val intent=Intent(this@MainActivity,LoginCompose::class.java)
                this@MainActivity.startActivity(intent)
                this@MainActivity.finish()
                return true
            }
        })

        val headerView= binding.navigationView.getHeaderView(0)
        val navUsername : TextView =headerView.findViewById(R.id.textViewDrawer)
        val navImage :ImageView = headerView.findViewById(R.id.imageViewDrawer)

        val getUser = intent.getSerializableExtra("user",User::class.java)
        navUsername.text = "${getUser!!.firstName} ${getUser!!.lastName}"
        Glide.with(this).load(getUser.image).into(navImage)


         sharedPreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
         editor = sharedPreferences.edit()


        mRemoteConfig= FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build()

        mRemoteConfig.setConfigSettingsAsync(configSettings)
        mRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        fetchAndActivate()
        configUpdateListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    fun fetchAndActivate(){
        mRemoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Log.e("Datax", "Config params updated: $updated")
                val color = mRemoteConfig.getString("backgroundColor")
                editor.putString("color", color).apply()


            } else {
                Toast.makeText(
                    this,
                    "Fetch failed",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    fun configUpdateListener(){
        mRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.e("onUpdate", "Updated keys: " + configUpdate.updatedKeys)

                if (configUpdate.updatedKeys.contains("backgroundColor")) {
                    mRemoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val color = mRemoteConfig.getString("backgroundColor")
                            editor.putString("color", color).apply()
                        }
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("onError", "Config update error with code: " + error.code, error)
            }
        })
    }


}
