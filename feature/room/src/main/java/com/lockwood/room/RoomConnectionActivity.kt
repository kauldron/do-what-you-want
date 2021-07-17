package com.lockwood.room

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import com.lockwood.dwyw.core.feature.wrapper.WrapperFeature
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.recorder.ext.createScreenCaptureIntent
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.recorder.manager.IMediaProjectionManager
import com.lockwood.replicant.feature.ext.mergePermissions
import com.lockwood.replicant.inflater.ext.setContentViewAsync
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.host.ui.HostView
import com.lockwood.room.navigation.launcher.RoomArgs.IS_SHOW_ADVERTISING
import com.lockwood.room.navigation.launcher.RoomArgs.ROOM_TO_SHOW

internal class RoomConnectionActivity : BaseActivity(),
    ProgressView, HostView {

    private companion object {

        private const val MENU_ITEM_ABOUT_ID = 1

        private const val CAPTURE_REQUEST_CODE = 1

        private const val SOURCE_URL = "https://github.com/kauldron/do-what-you-want"
    }

//    private val roomsInteractor: IRoomsInteractor
//        get() = getFeature<RoomsFeature>().roomsInteractor

    private val mediaProjectionManager: IMediaProjectionManager
        get() = getFeature<RecorderFeature>().mediaProjectionManager

    private val requiredPermissions: Array<String>
        get() {
            val payerFeature = getFeature<PlayerFeature>()
            val recorderFeature = getFeature<RecorderFeature>()
            return mergePermissions(payerFeature, recorderFeature)
        }

    //	private val requestPermissionLauncher: PermissionsResultLauncher = registerForActivityResult(
//			ActivityResultContracts.RequestMultiplePermissions(),
//			this::onActivityResult
//	)
//
//	private val requestScreenCaptureLauncher: ActivityResultLauncher<Unit> = registerForActivityResult(
//			object : UnitResultLauncher() {
//				override fun createIntent(context: Context, input: Unit): Intent {
//					return IMediaProjectionManager.createScreenCaptureIntent(context)
//				}
//
//				override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
//					if (intent != null) {
//						mediaProjectionManager.handleMediaProjectionResult(resultCode, intent)
//					}
//					return resultCode == -1  // -1 - success, 0 - fail
//				}
//			}, this::onCaptureGranted)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewAsync(R.layout.activity_rooms) {
            initActionBar()

//            handleIntent()
            requestPermissions(*requiredPermissions)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean = with(menu) {
        add(0, MENU_ITEM_ABOUT_ID, MENU_ITEM_ABOUT_ID, "About")

        return super.onCreateOptionsMenu(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_ITEM_ABOUT_ID -> showAboutDialog()
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        onActivityCaptureResult(requestCode, resultCode, data)
    }

//	override fun showScreen(screen: Screen) = when (screen) {
//		is RoomsDiscoveryScreen -> showFragment { RoomsDiscoverFragment.newInstance() }
//		is RoomsAdvertisingScreen -> showFragment { RoomHostFragment.newInstance() }
//		is RoomConnectionScreen -> showFragment { RoomClientFragment.newInstance() }
//		else -> super.showScreen(screen)
//	}

    override fun requestCapture() {
        val captureIntent = createScreenCaptureIntent()
        startActivityForResult(captureIntent, CAPTURE_REQUEST_CODE)
    }

    override fun onCaptureGranted(isGranted: Boolean) {
//		requestScreenCaptureLauncher.unregister()
//
//		val hostFragment = supportFragmentManager.requireFragmentType<IHostView>(
//				com.lockwood.replicant.R.id.fragment_container
//		)
//		hostFragment.onCaptureGranted(isGranted)
    }

    private fun onActivityCaptureResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode != CAPTURE_REQUEST_CODE) {
            return
        }

//        val isGranted = !result.containsValue(false)
//
//        if (isGranted) {
//            navigateToScreen()
//        } else {
//            showError("Not all permissions granted ( ｰ̀εｰ́ )")
//        }
    }

    override fun showProgress() {
        findViewById<View>(com.lockwood.dwyw.ui.core.R.id.progress_bar)?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        findViewById<View>(com.lockwood.dwyw.ui.core.R.id.progress_bar)?.visibility = View.GONE
    }

//    private fun handleIntent() = with(intent) {
//        when {
//            hasExtra(ROOM_TO_SHOW) -> updateConnectedRoom()
//            hasExtra(IS_SHOW_ADVERTISING) -> updateAdvertising()
//            else -> Unit
//        }
//    }

//    private fun updateAdvertising() = with(roomsInteractor) {
//        isBroadcasting = true
//    }
//
//    private fun updateConnectedRoom() = with(roomsInteractor) {
//        isConnected = true
////		connectedRoom = requireNotNull(intent.getParcelableExtra(ROOM_TO_SHOW))
//    }

//    private fun navigateToScreen() {
//        val screenToShow = getFeature<RoomsFeature>().roomsRouter.getScreenToShow()
//        showScreen(screenToShow)
//    }

    private inline fun <reified T : Fragment> showFragment(
        initializer: () -> T,
    ) {
//		showFragment<T>(com.lockwood.replicant.R.id.fragment_container, initializer())
    }

    private fun initActionBar() {
        val toolbar = requireNotNull<Toolbar>(findViewById(R.id.toolbar))
        setActionBar(toolbar)
    }

    private fun showAboutDialog() {
        val appInfo = getFeature<WrapperFeature>().buildConfigWrapper.appInfo

        showDialog {
            setTitle("Do what you want")
            setMessage(appInfo)
            setNeutralButton("Source") { _, _ -> openSourceWebPage() }
            setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        }
    }

    private fun openSourceWebPage() {
        val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_URL))
        startActivity(viewIntent)
    }

}