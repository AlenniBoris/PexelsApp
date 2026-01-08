package com.example.pexapp.presentation.uikit.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pexapp.R

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun launchForPermission(
    permission: PermissionType,
    context: Context,
    onPermissionGrantedAction: (PermissionType) -> Unit,
    onPermissionNotGrantedAction: (PermissionType) -> Unit,
    onShowRationale: (PermissionType) -> Unit,
    onLaunchAgain: (PermissionType) -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            permission.toPermission()
        ) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGrantedAction(permission)
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            context.findActivity(), permission.toPermission()
        ) -> {
            onShowRationale(permission)
        }

        else -> {
            onPermissionNotGrantedAction(permission)
            onLaunchAgain(permission)
        }
    }
}

enum class PermissionType {
    PERMISSION_WRITE_STORAGE
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermission(): String = when (this) {
    PermissionType.PERMISSION_WRITE_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionExplanation(): Int = when (this) {
    PermissionType.PERMISSION_WRITE_STORAGE -> R.string.write_storage_permission
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionName(): Int = when (this) {
    PermissionType.PERMISSION_WRITE_STORAGE -> R.string.write_storage_permission_name

}