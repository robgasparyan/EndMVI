package com.end.mvi.utils

import android.os.Bundle



sealed class NavigationToNextScreen {
    class NavigationToDetailsScreen(val bundle: Bundle?) : NavigationToNextScreen()
}