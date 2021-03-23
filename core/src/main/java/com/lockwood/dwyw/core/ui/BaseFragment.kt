package com.lockwood.dwyw.core.ui

import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.state.ViewState

abstract class BaseFragment<VS : ViewState> : ReplicantFragment<VS>()
