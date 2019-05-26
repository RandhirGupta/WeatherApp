package com.example.cyborg.weatherapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import org.junit.Rule
import org.mockito.Mock

class SingleLiveEventTest {

    @Rule
    val instantTaskRuleExecutor = InstantTaskExecutorRule()

    @Mock
    private val mOwner: LifecycleOwner? = null

    @Mock
    private val mEventObserver: Observer<Int>? = null
}