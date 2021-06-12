package com.lockwood.replicant.base

import androidx.annotation.CallSuper
import com.lockwood.replicant.feature.base.FeatureFragment
import com.lockwood.replicant.mvi.function.FunctionStore

abstract class ReplicantFragment : FeatureFragment() {

    private val rememberStore: FunctionStore = FunctionStore()

    @CallSuper
    override fun onDestroyView() {
        rememberStore.release()
        super.onDestroyView()
    }

//	@CallSuper
//	protected open fun onEvent(event: Event) {
//		when (event) {
//			is MessageEvent -> requireMessageView().showMessage(event.message)
//			is ErrorMessageEvent -> requireMessageView().showError(event.message)
//			is GoToBackEvent -> requireScreenView().goBack()
//			is ShowScreenEvent -> requireScreenView().showScreen(event.screen)
//			else -> error("Unknown event: $event")
//		}
//	}

    protected fun <P1, R> remember(
        action: ((P1) -> R),
        first: P1,
    ) = rememberStore.remember(first, action)

    protected fun <P1, P2, R> remember(
        action: ((P1, P2) -> R),
        first: P1,
        second: P2,
    ) = rememberStore.remember(first, second, action)

    protected fun <P1, P2, P3, R> remember(
        action: ((P1, P2, P3) -> R),
        first: P1,
        second: P2,
        third: P3,
    ) = rememberStore.remember(first, second, third, action)

}
