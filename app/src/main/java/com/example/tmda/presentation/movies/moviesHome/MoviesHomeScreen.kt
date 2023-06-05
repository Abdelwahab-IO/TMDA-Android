package com.example.tmda.presentation.movies.moviesHome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies.domain.enities.Movie
import com.example.tmda.R
import com.example.tmda.presentation.movies.moviesList.ScreenType
import com.example.tmda.presentation.navigation.Destinations
import com.example.tmda.presentation.shared.ImageCard
import com.example.tmda.presentation.shared.ItemsLazyRowComponent
import com.example.tmda.ui.theme.PineGreen
import com.example.tmda.ui.theme.WhiteTransparent60
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun MoviesHomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<MoviesHomeViewModel>()

    LazyColumn {
        item { NowPlayingHeader(viewModel.nowPlayingMoviesState.value) }
        item {
            ItemsLazyRowComponent(
                title = "Popular",
                onSeeAllClicked = {
                    navigateToMovieListScreen(
                        "Popular Movies",
                        ScreenType.Popular,
                        navController
                    )
                },
                items = viewModel.popularMoviesState.value
            ) { MovieHomeCard(movie = it) }
        }
        item {
            ItemsLazyRowComponent(
                title = "Upcoming",
                onSeeAllClicked = {
                    navigateToMovieListScreen(
                        "Upcoming Movies",
                        ScreenType.Upcoming,
                        navController
                    )
                },
                items = viewModel.upComingMoviesState.value
            ) { MovieHomeCard(movie = it) }
        }
        item {
            ItemsLazyRowComponent(
                title = "Top Rated",
                hasBottomDivider = false,
                onSeeAllClicked = {
                    navigateToMovieListScreen(
                        "Top Rated Movies",
                        ScreenType.TopRated,
                        navController
                    )
                },
                items = viewModel.topRatedMoviesState.value
            ) { MovieHomeCard(movie = it) }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingHeader(moviesList: List<Movie>) {
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { moviesList.size }
    Column(Modifier.background(Color.Transparent)) {
        HorizontalPager(state = pagerState) {
            NowPlayingCard(moviesList[it])
        }
        Spacer(Modifier.height(8.dp))
        DotsIndicator(totalDots = pagerState.pageCount, currentIndex = pagerState.currentPage)

    }

}

@Composable
fun NowPlayingCard(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + movie.posterPath!!,
            contentDescription = movie.title + "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "Marvel Studios",
                style = MaterialTheme.typography.bodySmall,
                color = PineGreen
            )
            Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
//            Text(
//                text = "In The Multiverse Of  Madness",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
            Spacer(modifier = Modifier.height(8.dp))
            TotalRatingIcons(rating = movie.voteAverage.toFloat(), iconSize = 24.dp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "From ${movie.voteCount} users",
                style = MaterialTheme.typography.bodySmall,
                color = WhiteTransparent60
            )

        }
    }
}

@Composable
fun TotalRatingIcons(
    maxRating: Int = 10,
    rating: Float,
    starsCount: Int = 5,
    iconSize: Dp = 12.dp
) {
    val actualRating = rating * starsCount / maxRating
    val filledStars = floor(actualRating).toInt()
    val unFilledStars = starsCount - ceil(actualRating).toInt()
    val halfFilledStar = starsCount - (filledStars + unFilledStars)
    val sharedModifier = Modifier
        .size(iconSize)
        .padding(end = 8.dp)
        .size(iconSize)

    Row {
        for (i in 0 until filledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        if (halfFilledStar == 1) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_half_star),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        for (i in 0 until unFilledStars) {
            Icon(
                modifier = sharedModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.White
            )
        }
    }

}

@Composable
fun DotsIndicator(totalDots: Int, currentIndex: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        for (i in 0 until totalDots) {
            if (i == currentIndex)
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_round_rectangle),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            else
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    painter = painterResource(id = R.drawable.ic_dot),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
        }
    }
}


@Composable
fun MovieHomeCard(movie: Movie) {
    Box(contentAlignment = Alignment.BottomCenter) {
        ImageCard(movie.posterPath ?: movie.backdropPath!!, movie.title)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(170.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = getMoviesYearAndGenres(movie),
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                TotalRatingIcons(rating = movie.voteAverage.toFloat(), iconSize = 16.dp)
            }
        }

    }

}

fun getMoviesYearAndGenres(movie: Movie): String {
    val genresName = movie.genres.take(2).map { it.name }.reduce { l, r -> "$l / $r" }
    return movie.releaseDate.subSequence(0, 4).toString() + " . " + genresName

}

fun navigateToMovieListScreen(
    screenTitle: String,
    screenType: ScreenType,
    navController: NavController
) {
    navController.navigate("${Destinations.MOVIES_LIST_SCREEN}/${screenTitle}/${screenType}/${-1}")
}