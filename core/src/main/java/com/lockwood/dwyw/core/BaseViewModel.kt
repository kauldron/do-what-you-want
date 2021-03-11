package com.lockwood.dwyw.core

import com.lockwood.replicant.base.ReplicantViewModel
import com.lockwood.replicant.state.ViewState

abstract class BaseViewModel<VS : ViewState>(initState: VS) : ReplicantViewModel<VS>(initState)