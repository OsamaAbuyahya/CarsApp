package os.abuyahya.carsapp.presintation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.navigation.Screen
import os.abuyahya.carsapp.others.Constants.BASE_URL
import os.abuyahya.carsapp.presintation.components.RatingWidget
import os.abuyahya.carsapp.ui.theme.CAR_ITEM_HEIGHT
import os.abuyahya.carsapp.R
import os.abuyahya.carsapp.presintation.components.ShimmerEffect
import os.abuyahya.carsapp.ui.theme.LARGE_PADDING
import os.abuyahya.carsapp.ui.theme.MEDIUM_PADDING
import os.abuyahya.carsapp.ui.theme.SMALL_PADDING
import os.abuyahya.carsapp.ui.theme.topAppBarContentColor

@ExperimentalCoilApi
@Composable
fun ListContent(
    cars: LazyPagingItems<Car>,
    navController: NavHostController
) {

    val result = handlePagingResult(cars = cars)
    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = cars,
                key = { car ->
                    car.id
                }
            ) { car ->
                car?.let {
                    CarItem(car = car, navController = navController)
                }
            }
        }
    }

}

@ExperimentalCoilApi
@Composable
fun CarItem(
    car: Car,
    navController: NavHostController
) {
    val painter = rememberImagePainter(data = "$BASE_URL${car.image}") {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .height(CAR_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passCarId(carId = car.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.car_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = car.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = car.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = car.rating
                    )
                    Text(
                        text = "(${car.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium),
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(cars: LazyPagingItems<Car>): Boolean {
    cars.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, cars = cars)
                false
            }
            cars.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@ExperimentalCoilApi
@Composable
@Preview
fun HeroItemPreview() {
    CarItem(
        car = Car(
            id = 1,
            name = "BM",
            image = "",
            about = "....................................",
            rating = 3.0,
            price = 12,
            color = "Black",
            features = emptyList()
        ),
        navController = rememberNavController()
    )
}

@ExperimentalCoilApi
@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun HeroItemDarkPreview() {
    CarItem(
        car = Car(
            id = 1,
            name = "BM",
            image = "",
            about = "....................................",
            rating = 3.0,
            price = 12,
            color = "Black",
            features = emptyList()
        ),
        navController = rememberNavController()
    )
}
