package com.example.movizy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movizy.Utils.Util.BROWSE_MOVIES_SCREEN
import com.example.movizy.Utils.Util.MOVIE_DETAILS_SCREEN
import com.example.movizy.Utils.Util.SPLASH_SCREEN

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(BROWSE_MOVIES_SCREEN) {
            BrowseMoviesScreen(navController = navController)
        }
        composable("${MOVIE_DETAILS_SCREEN}/{id}",
            arguments = listOf(navArgument("id", {
             NavType.IntType
                defaultValue = -2

        }))) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("id")
            movieId?.let { id ->
                MovieDetails(id = id, navController = navController)
            }
        }
    }

}
