package com.sazib.graphql.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sazib.graphql.presentation.continents.ContinentScreen
import com.sazib.graphql.presentation.continents.ContinentsViewModel
import com.sazib.graphql.presentation.details.FetchDetails
import com.sazib.graphql.presentation.details.FetchDetailsViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Dest.Home) {

        composable<Dest.Home> {
            val viewModel = hiltViewModel<ContinentsViewModel>()
            ContinentScreen(continentsViewModel = viewModel) {
                navController.navigate(Dest.Details(it))
            }
        }

        composable<Dest.Details> {
            val viewModel = hiltViewModel<FetchDetailsViewModel>()
            val code = it.toRoute<Dest.Details>().code
            LaunchedEffect(key1 = code) {
                viewModel.fetchDetails(code)
            }

            FetchDetails(fetchDetailsViewModel = viewModel)
        }


    }
}

@Serializable
sealed class Dest {

    @Serializable
    data object Home : Dest()

    @Serializable
    data class Details(val code: String) : Dest()

}

