package com.example.movizy.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movizy.R
import com.example.movizy.Utils.Util.BASE_IMAGE_URL
import com.example.movizy.Utils.Util.MOVIE_DETAILS_SCREEN
import com.example.movizy.ViewModels.MovieViewModel
import com.example.movizy.models.MovieItem
import com.example.movizy.ui.theme.LightGray
import com.example.movizy.ui.theme.gold
import com.example.movizy.ui.theme.readstoreFamily
import com.example.movizy.ui.theme.robotoFamily

@Composable
fun BrowseMoviesScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() }) { innerPadding ->
        val viewModel = viewModel<MovieViewModel>()
        val state = viewModel.state

        Surface(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                // Error text item
                item {
                    state.error?.let { Text(text = it) }
                }

                // Category title for popular movies
                item {
                    CategoryTitle("Upcoming")
                }

                // Upcoming movies LazyRow
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(state.upComingMovies) { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = { navController.navigate("${MOVIE_DETAILS_SCREEN}/${movie.id}") })
                        }
                    }
                }
                item {
                    CategoryTitle("Top Rated")
                }
                item {
                    LazyRow(
                        reverseLayout = true,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(state.topRatedMovies) { movie ->
                            MovieCard(movie = movie,
                                onClick = {
                                    navController.navigate("${MOVIE_DETAILS_SCREEN}/${movie.id}")
                                })
                        }
                    }
                }

                item {
                    CategoryTitle("All Movies")
                }

                // All movies LazyColumn
                items(state.movies) { movie ->
                    HorizontalMovieItem(
                        movie = movie,
                        onClick = { navController.navigate("${MOVIE_DETAILS_SCREEN}/${movie.id}") })
                }
            }
        }
    }
}

@Composable
fun CategoryTitle(title: String) {
    Text(
        text = title, style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun MovieCard(movie: MovieItem, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.clickable {
        onClick()
    }) {
        Column(modifier = Modifier.width(100.dp)) {
            PosterImage(imagePath = movie.poster_path)
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            MovieRatingText(rating = movie.vote_average)
        }
    }
}

@Composable
fun MovieRatingText(modifier: Modifier = Modifier, rating: Double, bigSize: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            Icons.Rounded.Star,
            tint = gold,
            contentDescription =
            "star icon", modifier = Modifier
                .size(if (bigSize) 18.dp else 15.dp)
                .padding(end = 2.dp)
        )
        Text(
            text = String.format("%.1f", rating) + "/10 IMDB",
            style = TextStyle(
                color = LightGray,
                fontSize = if (bigSize) 12.sp else 9.sp,
                fontFamily = robotoFamily
            )
        )
    }
}

@Composable
fun HorizontalMovieItem(movie: MovieItem, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 4.dp)
            .clickable { onClick() }) {
        PosterImage(
            Modifier
                .width(120.dp)
                .height(160.dp), imagePath = movie.poster_path
        )

        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp)
        ) {
            Text(
                text = movie.release_date, modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.labelSmall, color = Color.Gray
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            MovieRatingText(modifier = Modifier.padding(top = 4.dp), rating = 9.8, bigSize = true)

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}


@Composable
fun PosterImage(
    modifier: Modifier = Modifier
        .width(140.dp)
        .height(160.dp),
    imagePath: String,
) {
    AsyncImage(
        model = BASE_IMAGE_URL + imagePath,
        contentDescription = "Translated description of what the image contains",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(6.dp),
            ),
    )
}

@Composable
fun TopBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp, start = 10.dp, end = 10.dp)
    ) {
        Icon(Icons.Rounded.Menu, contentDescription = "menu icon")
        Text(
            text = "Movizy",
            style = TextStyle(
                fontSize = 22.sp,
                color = Color.DarkGray,
                fontFamily = readstoreFamily
            )
        )
        Icon(Icons.Outlined.Notifications, contentDescription = "notification icon")
    }
}

//////  Preview
@Composable
fun MovieCardPrev(ImageModifier: Modifier = Modifier) {
    Column(modifier = Modifier.width(100.dp)) {

        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "Translated description of what the image contains",
            contentScale = ContentScale.Crop,
            modifier = ImageModifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(6.dp),
                )
        )

        Text(
            text = "Spiderman: No way home",
            style = MaterialTheme.typography.labelSmall,

            modifier = Modifier.padding(top = 4.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Rounded.Star,
                tint = gold,
                contentDescription =
                "star icon", modifier = Modifier
                    .size(15.dp)
                    .padding(end = 2.dp)
            )
            Text(
                text = "9.1/10 IMDB",
                style = TextStyle(color = LightGray, fontSize = 9.sp, fontFamily = robotoFamily)
            )
        }
    }
}


@Composable
fun HorizontalMovieItemPrev(ImageModifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "Translated description of what the image contains",
            contentScale = ContentScale.Crop,
            modifier = ImageModifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(6.dp),
                )
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = "22/07/2023", modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.labelSmall, color = Color.Gray
            )
            Text(
                text = "Spiderman: the world last time",
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium
            )
            MovieRatingText(rating = 9.8, bigSize = true)

            Text(
                text = "Loream and the world last time, oream and the world last time. K oream and the world last time. oream last time",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)

            )
        }
    }
}

@Preview
@Composable
fun BrowseMoviesPrev() {
    val viewModel = viewModel<MovieViewModel>()
    val state = viewModel.state
    val ImageModifier = Modifier
        .width(100.dp)
        .height(160.dp)

    val ImageModifier2 = Modifier
        .width(100.dp)
        .height(140.dp)
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() }) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {

                CategoryTitle("Popular")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        MovieCardPrev(ImageModifier)
                    }
                    item {
                        MovieCardPrev(ImageModifier)
                    }
                    item {
                        MovieCardPrev(ImageModifier)
                    }
                    item {
                        MovieCardPrev(ImageModifier)
                    }


                }
                CategoryTitle("All Movies")
//                LazyColumn {
                HorizontalMovieItemPrev(ImageModifier2)
//                }
            }
        }
    }


}
