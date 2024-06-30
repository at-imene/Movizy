package com.example.movizy.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movizy.R
import com.example.movizy.Utils.Util.BASE_IMAGE_URL
import com.example.movizy.Utils.Util.IMDB_BASE_URL
import com.example.movizy.ViewModels.MovieViewModel
import com.example.movizy.ui.theme.Black
import com.example.movizy.ui.theme.DarkPurple
import com.example.movizy.ui.theme.ExtraLightPurple
import com.example.movizy.ui.theme.IMDBYellow
import com.example.movizy.ui.theme.LightPurple
import com.example.movizy.ui.theme.robotoFamily

@Composable
fun MovieDetails(id: Int, navController: NavController) {

    val viewModel = viewModel<MovieViewModel>()
    val context = LocalContext.current

    viewModel.id = id
    val state = viewModel.state
    viewModel.loadMovieDetails()

    if (state.movieDetails != null) {
        val movie = state.movieDetails
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = BASE_IMAGE_URL + state.movieDetails.poster_path,
                contentDescription = "movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.4f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.4f)
                    .alpha(.2f)
                    .background(Color.Black)
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .clip(shape = RoundedCornerShape(26.dp))
                    .background(Color.White.copy(alpha = .25f)
                        ).clickable {
                            navController.popBackStack()
                    }
            ) {
                Icon(
                    Icons.Rounded.ArrowBack, contentDescription = "back arrow",
                    tint = Color.White, modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.65f)
                    .align(Alignment.BottomCenter)
                    .clip(shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                    .background(Color.White)
                    .padding(top = 16.dp, start = 10.dp, end = 10.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                           if(viewModel.isFavorite) Icons.Outlined.Favorite  else Icons.Outlined.FavoriteBorder,
                            tint = DarkPurple,
                            contentDescription = "favorite icon",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(28.dp)
                                .clickable {
                                    viewModel.toggleFavorite()
                                }
                        )
                    }


                    MovieRatingText(rating = movie.vote_average, bigSize = true)
                    Row {
                        for (g in movie.genres) {

                            Categorytag(g.name)
                        }
                    }

                    Row(
                        Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(.7f), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MovieInfo("Length", "${movie.runtime/60}h${movie.runtime%60}min")
                        MovieInfo("Language", movie.original_language)
                        MovieInfo("Budget", "${movie.budget/1000000}M$")
                    }

                    Text(text = "Description", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium, color = Color.Gray
                    )
                    FilledButton(onClick = {
                        openUrlInBrowser(movie.homepage,context)

                    }, text = "Home page", bgColor = ExtraLightPurple, contentColor = DarkPurple)

                    FilledButton(onClick = {
                        openUrlInBrowser(IMDB_BASE_URL + movie.imdb_id,context)
                    }, text = "IMDB Link", bgColor = IMDBYellow, contentColor = Black)

                }
            }
        }
    }

}

@Composable
fun FilledButton(onClick:()->Unit = {}, text: String, bgColor: Color, contentColor: Color){
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White,)
    ) {
        Text(
            text = text,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            color = contentColor,
            fontFamily = robotoFamily
        )
    }
}

fun openUrlInBrowser(url: String, context: Context) {
    if(!url.isNullOrEmpty()){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }else{
        Toast.makeText(context, "this link is not available.", Toast.LENGTH_SHORT).show()
    }

}

@Preview
@Composable
fun MovieDetailsPrev() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.splash_background),
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
                .alpha(.2f)
                .background(Color.Black)
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .clip(shape = RoundedCornerShape(26.dp))
                .background(Color.White.copy(alpha = .25f))
        ) {
            Icon(
                Icons.Rounded.ArrowBack, contentDescription = "back arrow",
                tint = Color.White, modifier = Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.65f)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                .background(Color.White)
                .padding(top = 16.dp, start = 10.dp, end = 10.dp)
        ) {
            Column {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Spiderman: No way home",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth(.7f)
                            .padding(bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        tint = DarkPurple
                        , contentDescription = "favorite icon",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(28.dp)
                    )
                }


                MovieRatingText(rating = 9.8, bigSize = true)
                Row {
                    Categorytag("Sci-fi")
                    Categorytag("Romance")
                    Categorytag("Fantasy")
                }

                Row(
                    Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(.7f), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MovieInfo("Length", "2h22min")
                    MovieInfo("Language", "English")
                    MovieInfo("Rating", "RG-12")
                }

                Text(text = "Description", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Several in the reign, humans have been reduced to living in the shadows. As  his empire, one young ape choices that will define a future for apes and humans alike.",
                    style = MaterialTheme.typography.bodyMedium, color = Color.Gray
                )
                FilledButton(onClick = {
                }, text = "Home page", bgColor = ExtraLightPurple, contentColor = DarkPurple)
                FilledButton(onClick = {
                }, text = "IMDB Link", bgColor = IMDBYellow, contentColor = Black)




            }
        }
    }
}

@Composable
fun Categorytag(tag: String) {
    Text(
        text = tag.toUpperCase(),
        modifier = Modifier
            .padding(end = 8.dp, top = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                ExtraLightPurple
            )
            .padding(vertical = 4.dp, horizontal = 8.dp),
        color = LightPurple,
        style = MaterialTheme.typography.labelSmall

    )
}

@Composable
fun MovieInfo(title: String, info: String) {
    Column(modifier = Modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(text = info, style = MaterialTheme.typography.titleMedium)
    }
}
