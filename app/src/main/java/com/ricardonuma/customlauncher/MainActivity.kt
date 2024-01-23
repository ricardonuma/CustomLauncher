package com.ricardonuma.customlauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ricardonuma.customlauncher.ui.theme.CustomLauncherTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchApp(this)
        setContent {
            CustomLauncherTheme {
                LoadListScreen()
            }
        }
    }
}

var appList: List<App> = emptyList()

// Handle app launch logic
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun launchApp(context: Context) {
    val pm: PackageManager = context.packageManager
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    val flags = PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
    val activities: List<ResolveInfo> = context.packageManager.queryIntentActivities(intent, flags)

    appList = activities.map { resolveInfo ->
        App(
            name = resolveInfo.loadLabel(context.packageManager).toString(),
            packageName = resolveInfo.activityInfo.packageName,
            icon = resolveInfo.loadIcon(context.packageManager)
        )
    }
}

@Composable
fun LoadListScreen() {
    LazyColumn {
        items(appList) { app ->
            ListItem(app = app)
        }
    }
}

@Composable
fun ListItem(app: App) {
    Text(text = app.name, modifier = Modifier.padding(16.dp))
}