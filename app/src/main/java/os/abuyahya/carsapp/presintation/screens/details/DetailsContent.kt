package os.abuyahya.carsapp.presintation.screens.details

import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import os.abuyahya.carsapp.R
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.others.Constants.BASE_URL
import os.abuyahya.carsapp.presintation.components.InfoBox
import os.abuyahya.carsapp.presintation.components.OrderedList
import os.abuyahya.carsapp.ui.theme.COLLAPSED_RADIUS_LEVEL
import os.abuyahya.carsapp.ui.theme.EXPANDED_RADIUS_LEVEL
import os.abuyahya.carsapp.ui.theme.EXTRA_LARGE_PADDING
import os.abuyahya.carsapp.ui.theme.INFO_ICON_SIZE
import os.abuyahya.carsapp.ui.theme.LARGE_PADDING
import os.abuyahya.carsapp.ui.theme.MEDIUM_PADDING
import os.abuyahya.carsapp.ui.theme.MIN_SHEET_HEIGHT
import os.abuyahya.carsapp.ui.theme.SMALL_PADDING
import os.abuyahya.carsapp.ui.theme.titleColor

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsContent(
    navController: NavHostController,
    selectedCar: Car?,
    colors: Map<String, String>
) {

    var vibrant by remember { mutableStateOf("#ffffff") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#ffffff") }
    var onDarkVibrantTitle by remember { mutableStateOf("#ffffff") }

    LaunchedEffect(key1 = selectedCar) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
        onDarkVibrantTitle = colors["onDarkVibrantTitle"]!!
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(parseColor(darkVibrant))
        )
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = Expanded)
    )

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f)
            COLLAPSED_RADIUS_LEVEL
        else
            EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedCar?.let {
                BottomSheetContent(
                    selectedCar = it,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant)),
                    contentTitleColor = Color(parseColor(onDarkVibrantTitle))
                )
            }
        },
        content = {
            selectedCar?.let { hero ->
                BackgroundContent(
                    carImage = hero.image,
                    imageFraction = currentSheetFraction,
                    backgroundColor = Color(parseColor(darkVibrant)),
                    onCloseClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}

@Composable
fun BottomSheetContent(
    selectedCar: Car,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor,
    contentTitleColor: Color = MaterialTheme.colors.titleColor,
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_car),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentTitleColor
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedCar.name,
                color = contentTitleColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_price_check),
                iconColor = infoBoxIconColor,
                bigText = "${selectedCar.price}",
                smallText = stringResource(R.string.price),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_color_lens),
                iconColor = infoBoxIconColor,
                bigText = selectedCar.color,
                smallText = stringResource(R.string.color),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_star_rate),
                iconColor = infoBoxIconColor,
                bigText = selectedCar.rating.toString(),
                smallText = stringResource(R.string.rating),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedCar.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.features),
                items = selectedCar.features,
                textColor = contentColor
            )
        }
    }
}


@ExperimentalCoilApi
@Composable
fun BackgroundContent(
    carImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${carImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + 0.5f)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.car_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = SMALL_PADDING),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        Log.d("Fraction", fraction.toString())
        Log.d("Fraction Target", targetValue.toString())
        Log.d("Fraction Current", currentValue.toString())

        return when {
            currentValue == Collapsed && targetValue == Collapsed -> 1f
            currentValue == Expanded && targetValue == Expanded -> 0f
            currentValue == Collapsed && targetValue == Expanded -> 1f - fraction
            currentValue == Expanded && targetValue == Collapsed -> 0f + fraction
            else -> fraction
        }
    }


@Composable
@Preview
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedCar = Car(
            id = 1,
            name = "Naruto",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            rating = 4.5,
            price = 111,
            color = "Block",
            features = listOf("F1, F2, F3")
        )
    )
}

