package com.ssafy.talkeasy.feature.location.ui.mobile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.ssafy.talkeasy.feature.common.component.LocationOpenAnimation
import com.ssafy.talkeasy.feature.common.component.WideSeedButton
import com.ssafy.talkeasy.feature.common.ui.theme.cabbage_pont
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.sunset_orange
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.location.LocationViewModel
import com.ssafy.talkeasy.feature.location.R

@Composable
internal fun LocationOpenRoute(
    onClickedStopButton: () -> Unit = {},
    locationViewModel: LocationViewModel = hiltViewModel(),
) {
    val locationsInfo by locationViewModel.locationsInfo.collectAsState()
    val isLocationOpenAccepted by locationViewModel.isLocationOpenAccepted.collectAsState()
    var isInfoLoadingFinished by remember { mutableStateOf(false) }

    SideEffect {
        if (!isLocationOpenAccepted) {
            // 위치정보 열람 요청
            locationViewModel.requestAcceptLocationInfo()
        }
    }

    LaunchedEffect(isLocationOpenAccepted) {
        if (isLocationOpenAccepted) {
            // 위치 정보 불러오기
            locationViewModel.requestLocationInfo()
        }
    }

    LaunchedEffect(key1 = locationsInfo) {
        if (locationsInfo != null) {
            isInfoLoadingFinished = true
        }
    }

    LocationOpenScreen(
        isInfoLoadingFinished = isInfoLoadingFinished,
        isLocationOpenAccepted = isLocationOpenAccepted,
        onClickedStopButton = onClickedStopButton
    )
}

@Composable
internal fun LocationOpenScreen(
    isInfoLoadingFinished: Boolean,
    isLocationOpenAccepted: Boolean,
    onClickedStopButton: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 18.dp),
                text = stringResource(id = R.string.title_open_location),
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {
                LocationOpenContent(onClickedStopButton = onClickedStopButton)

                WideSeedButton(
                    modifier = Modifier
                        .padding(start = 34.dp, end = 34.dp, bottom = 20.dp)
                        .align(Alignment.BottomCenter),
                    onClicked = { },
                    text = stringResource(id = R.string.content_stop),
                    textStyle = typography.titleLarge,
                    containerColor = sunset_orange,
                    textColor = md_theme_light_background
                )

                if (!isInfoLoadingFinished || !isLocationOpenAccepted) {
                    LocationOpenAnimation(
                        isInfoLoadingFinished = isInfoLoadingFinished,
                        isLocationOpenAccepted = isLocationOpenAccepted
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LocationOpenContent(
    onClickedStopButton: () -> Unit = {},
) {
    val locationTabItem =
        mutableListOf(stringResource(R.string.content_today), stringResource(R.string.content_week))
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = sunset_orange,
                    height = 4.dp,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                )
            }
        ) {
            locationTabItem.forEachIndexed { index, item ->
                Tab(
                    text = {
                        Text(
                            text = item,
                            style = typography.titleLarge,
                            fontWeight = if (selectedTab == index) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    },
                    selectedContentColor = sunset_orange,
                    unselectedContentColor = cabbage_pont,
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when (selectedTab) {
            0 -> LocationOpenToday()
            1 -> LocationOpenWeek()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationOpenToday(onClickedStopButton: () -> Unit = {}) {
    val defaultPosition = LatLng(36.106196, 128.416654)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 15f)
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings().copy(
                zoomControlsEnabled = false,
                compassEnabled = true
            )
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val pointsList = remember {
        mutableListOf(
            defaultPosition,
            LatLng(36.105690, 128.417557),
            LatLng(36.105599, 128.420017),
            LatLng(36.103752, 128.421514)
        )
    }
    val drawableList = remember {
        mutableListOf(
            R.drawable.ic_position_marker_with_circle,
            R.drawable.ic_sunset_orange_circle
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                for (index in 0 until pointsList.size) {
                    MapMarker(
                        context = LocalContext.current,
                        title = "",
                        snippet = "",
                        position = pointsList[index],
                        iconResourceId = if (index == 0) drawableList[0] else drawableList[1]
                    )
                }

                Polyline(
                    points = pointsList,
                    color = sunset_orange,
                    width = 20f,
                    startCap = ButtCap(),
                    endCap = ButtCap()
                )
            }
        }
    }
}

@Composable
fun LocationOpenWeek() {
    val defaultPosition = LatLng(36.106196, 128.416654)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 15f)
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings().copy(
                zoomControlsEnabled = false,
                compassEnabled = true
            )
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                MapMarker(
                    context = LocalContext.current,
                    title = "",
                    snippet = "",
                    position = defaultPosition,
                    iconResourceId = R.drawable.ic_location_marker
                )
            }
        }
    }
}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes
    iconResourceId: Int,
) {
    val icon = bitmapDescriptorFromVector(context, iconResourceId)
    Marker(
        state = MarkerState(position = position),
        title = title,
        snippet = snippet,
        icon = icon
    )
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bm)

    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bm)
}