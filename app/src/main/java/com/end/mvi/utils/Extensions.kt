package com.end.mvi.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectWhileStarted(
    lifecycleOwner: LifecycleOwner,
    action: suspend (value: T) -> Unit
) {
    var job: Job? = null
    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                job = source.lifecycleScope.launch {
                    collect(action)
                }
            }
            Lifecycle.Event.ON_STOP -> {
                job?.cancel()
                job = null
            }
            else -> {
            }
        }
    })
}

fun Context.toastTodo() = Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()