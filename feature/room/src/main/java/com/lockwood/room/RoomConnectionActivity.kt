package com.lockwood.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.clearBackStack
import com.lockwood.automata.android.requireFragmentType
import com.lockwood.automata.intent.openWebPage
import com.lockwood.dwyw.core.feature.wrapper.WrapperFeature
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.recorder.manager.IMediaProjectionManager
import com.lockwood.replicant.ext.mergePermissions
import com.lockwood.replicant.inflater.ext.setContentViewAsync
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.MessageView
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.client.ui.RoomClientFragment
import com.lockwood.room.feature.discover.ui.RoomsDiscoverFragment
import com.lockwood.room.feature.host.ui.IHostView
import com.lockwood.room.feature.host.ui.RoomHostFragment
import com.lockwood.room.model.PermissionsResultCallback
import com.lockwood.room.model.PermissionsResultLauncher
import com.lockwood.room.model.UnitResultLauncher
import com.lockwood.room.navigation.launcher.RoomArgs.IS_SHOW_ADVERTISING
import com.lockwood.room.navigation.launcher.RoomArgs.ROOM_TO_SHOW
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import com.lockwood.room.screen.RoomsDiscoveryScreen

internal class RoomConnectionActivity : BaseActivity(),
		MessageView,
		ProgressView,
		IHostView,
		PermissionsResultCallback {

	private companion object {

		private const val MENU_ITEM_LICENSE_ID = 0
		private const val MENU_ITEM_ABOUT_ID = 1

		private const val SOURCE_URL = "https://github.com/kauldron/do-what-you-want"
	}

	private val roomsInteractor: IRoomsInteractor
		get() = getFeature<RoomsFeature>().roomsInteractor

	private val mediaProjectionManager: IMediaProjectionManager
		get() = getFeature<RecorderFeature>().mediaProjectionManager

	private val requiredPermissions: Array<String>
		get() {
			val payerFeature = getFeature<PlayerFeature>()
			val recorderFeature = getFeature<RecorderFeature>()
			return mergePermissions(payerFeature, recorderFeature)
		}

	private val requestPermissionLauncher: PermissionsResultLauncher = registerForActivityResult(
			ActivityResultContracts.RequestMultiplePermissions(),
			this::onActivityResult
	)

	private val requestScreenCaptureLauncher: ActivityResultLauncher<Unit> = registerForActivityResult(
			object : UnitResultLauncher() {
				override fun createIntent(context: Context, input: Unit): Intent {
					return IMediaProjectionManager.createScreenCaptureIntent(context)
				}

				override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
					if (intent != null) {
						mediaProjectionManager.handleMediaProjectionResult(resultCode, intent)
					}
					return resultCode == -1  // -1 - success, 0 - fail
				}
			}, this::onCaptureGranted)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentViewAsync(R.layout.activity_rooms) {
			initActionBar()

			handleIntent()
			requestPermissions()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean = with(menu) {
		add(0, MENU_ITEM_LICENSE_ID, MENU_ITEM_LICENSE_ID, "Licenses")
		add(0, MENU_ITEM_ABOUT_ID, MENU_ITEM_ABOUT_ID, "About")

		return super.onCreateOptionsMenu(this)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			MENU_ITEM_LICENSE_ID -> showLicense()
			MENU_ITEM_ABOUT_ID -> showAboutDialog()
		}

		return true
	}

	override fun onDestroy() {
		supportFragmentManager.clearBackStack()
		super.onDestroy()
	}

	override fun onActivityResult(result: Map<String, Boolean>) {
		val isGranted = !result.containsValue(false)

		if (isGranted) {
			navigateToScreen()
		} else {
			showToast("Not all permissions granted ( ｰ̀εｰ́ )")
		}
	}

	override fun showScreen(screen: Screen) = when (screen) {
		is RoomsDiscoveryScreen -> showFragment { RoomsDiscoverFragment.newInstance() }
		is RoomsAdvertisingScreen -> showFragment { RoomHostFragment.newInstance() }
		is RoomConnectionScreen -> showFragment { RoomClientFragment.newInstance() }
		else -> super.showScreen(screen)
	}

	override fun showProgress() {
		findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.VISIBLE
	}

	override fun hideProgress() {
		findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.GONE
	}

	override fun showMessage(message: String) {
		showToast(message)
	}

	override fun showError(message: String) {
		showToast(message)
	}

	override fun requestCapture() {
		requestScreenCaptureLauncher.launch(Unit)
	}

	override fun onCaptureGranted(isGranted: Boolean) {
		requestScreenCaptureLauncher.unregister()

		val hostFragment = supportFragmentManager.requireFragmentType<IHostView>(
				com.lockwood.replicant.R.id.fragment_container
		)
		hostFragment.onCaptureGranted(isGranted)
	}

	private fun handleIntent() = with(intent) {
		checkNotNull(extras) {
			roomsInteractor.resetCacheState()
			return
		}

		when {
			hasExtra(ROOM_TO_SHOW) -> updateConnectedRoom()
			hasExtra(IS_SHOW_ADVERTISING) -> updateAdvertising()
			else -> Unit
		}
	}

	private fun updateAdvertising() = with(roomsInteractor) {
		isBroadcasting = true
	}

	private fun updateConnectedRoom() = with(roomsInteractor) {
		isConnected = true
		connectedRoom = requireNotNull(intent.getParcelableExtra(ROOM_TO_SHOW))
	}

	private fun navigateToScreen() {
		val screenToShow = getFeature<RoomsFeature>().roomsRouter.getScreenToShow()
		showScreen(screenToShow)
	}

	private fun requestPermissions() {
		requestPermissionLauncher.launch(requiredPermissions)
	}

	private inline fun <reified T : Fragment> showFragment(
			initializer: () -> T,
	) {
		showFragment<T>(com.lockwood.replicant.R.id.fragment_container, initializer())
	}

	private fun initActionBar() {
		val toolbar = requireNotNull<Toolbar>(findViewById(R.id.toolbar))
		setActionBar(toolbar)
	}

	private fun showLicense() {
		getFeature<RoomsFeature>().thirdPartyLicenseLauncher.launch(this)
	}

	private fun showAboutDialog() {
		val appInfo = getFeature<WrapperFeature>().buildConfigWrapper.appInfo

		showDialog {
			setTitle("Do what you want")
			setMessage(appInfo)
			setNeutralButton("Source") { _, _ -> openWebPage(SOURCE_URL) }
			setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
		}
	}
}