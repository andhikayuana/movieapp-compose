/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package id.yuana.compose.movieapp.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.yuana.compose.movieapp.R
import id.yuana.compose.movieapp.domain.model.Movie
import id.yuana.compose.movieapp.navigation.MovieRoutes
import id.yuana.compose.movieapp.navigation.graph.home.BottomNavItem
import id.yuana.compose.movieapp.navigation.graph.home.HomeNavGraph
import id.yuana.compose.movieapp.navigation.graph.home.MovieAppBottomBar
import id.yuana.compose.movieapp.navigation.graph.home.MovieHomeNavigation
import id.yuana.compose.movieapp.navigation.graph.root.MovieRootNavigation
import id.yuana.compose.movieapp.presentation.screen.moviedetail.MovieDetailScreen
import id.yuana.compose.movieapp.presentation.screen.moviefavorite.MovieFavoriteScreen
import id.yuana.compose.movieapp.presentation.screen.moviepopular.MoviePopularScreen
import id.yuana.compose.movieapp.presentation.ui.theme.MovieAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieRootNavigation()
                }
            }
        }
    }
}
