package com.scube.scubepermission

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.scube.scubepermission.databinding.FragmentMainBinding
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.EasyPermissions

private const val TAG = "MainFragment"
private const val REQUEST_CODE_SMS_PERMISSION = 126

@Suppress("UNUSED")
class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    // ============================================================================================
    //  Fragment Lifecycle
    // ============================================================================================

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var fragmentMAinBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        fragmentMAinBinding = binding
        return binding.root
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        fragmentMAinBinding = null
        super.onDestroyView()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMAinBinding!!.buttonSms.setOnClickListener {
            onClickRequestPermissionSMSButton()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // ============================================================================================
    //  Implementation Permission Callbacks
    // ============================================================================================

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_granted, requestCode, perms.size))
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_denied, requestCode, perms.size))
    }

    // ============================================================================================
    //  Private Methods
    // ============================================================================================

    @AfterPermissionGranted(REQUEST_CODE_SMS_PERMISSION)
    private fun onClickRequestPermissionSMSButton() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_SMS)) {
            // Have permission, do the thing!
            Toast.makeText(activity, "TODO: SMS things", Toast.LENGTH_LONG).show()
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_sms_rationale_message),
                REQUEST_CODE_SMS_PERMISSION,
                Manifest.permission.READ_SMS
            )
        }
    }
}