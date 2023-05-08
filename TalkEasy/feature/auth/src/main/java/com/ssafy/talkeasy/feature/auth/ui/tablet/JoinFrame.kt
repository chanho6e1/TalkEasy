package com.ssafy.talkeasy.feature.auth.ui.tablet

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.auth.AuthViewModel
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.component.EditProfile
import com.ssafy.talkeasy.feature.common.component.SegmentedButton
import com.ssafy.talkeasy.feature.common.component.ShowProfileDialog
import com.ssafy.talkeasy.feature.common.component.WideSeedButton
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.ui.theme.welcomeBold30
import com.ssafy.talkeasy.feature.common.ui.theme.welcomeBold32
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
internal fun JoinRouteWard(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    viewModel: AuthViewModel = hiltViewModel(navBackStackEntry),
    onJoinMember: () -> Unit,
) {
    val context = LocalContext.current
    val memberState by viewModel.memberState.collectAsState()
    if (memberState == "MEMBER") {
        onJoinMember()
    }

    JoinFrame(
        modifier = modifier,
        onJoinButtonClick = {
            viewModel.requestJoin(it)
        },
        onImagePicked = { bitmap ->
            viewModel.setProfileImg(bitmap, context.cacheDir.toString())
        },
        onChangeBirthDate = {
            viewModel.setBirthDate(it)
        },
        onChangeGender = {
            viewModel.setGender(it)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun JoinFrame(
    modifier: Modifier = Modifier,
    onJoinButtonClick: (String) -> Unit = {},
    onImagePicked: (Bitmap) -> Unit = {},
    onChangeBirthDate: (String) -> Unit = {},
    onChangeGender: (String) -> Unit = {},
) {
    val profileImage = rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val (dialogState: Boolean, setDialogState: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    Box() {
        ShowProfileDialog(
            modifier = modifier,
            visible = dialogState,
            onDismissRequest = { setDialogState(!dialogState) },
            onImagePicked = { bitmap ->
                profileImage.value = bitmap
                onImagePicked(bitmap)
                setDialogState(false)
            }
        )
        JoinContent(
            modifier = modifier,
            onJoinButtonClick = onJoinButtonClick,
            onProfileClick = {
                setDialogState(!dialogState)
            },
            onChangeGender = onChangeGender,
            onChangeBirthDate = onChangeBirthDate,
            profile = profileImage.value
        )
    }
}

@Composable
internal fun JoinContent(
    modifier: Modifier = Modifier,
    onJoinButtonClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    onChangeBirthDate: (String) -> Unit = {},
    onChangeGender: (String) -> Unit = {},
    profile: Bitmap?,
) {
    val (nickName: String, setNickName: (String) -> Unit) = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(406.dp, 40.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_member_info_input),
                style = welcomeBold32,
                fontWeight = FontWeight.Bold
            )
            EditProfile(
                size = 150,
                profile = profile,
                textStyle = welcomeBold30
            ) { onProfileClick() }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                NameTextField(modifier = modifier, nickName = nickName, setNickName = setNickName)
                SegmentedButtonSexual(onChangeGender = onChangeGender)
                BirthPicker(onChangeBirthDate = onChangeBirthDate)
            }
            WideSeedButton(
                onClicked = { onJoinButtonClick(nickName) },
                text = stringResource(id = R.string.content_join),
                textStyle = typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    nickName: String,
    setNickName: (String) -> Unit,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedTextColor = md_theme_light_primary,
        containerColor = green_white,
        focusedIndicatorColor = seed,
        focusedLabelColor = seed,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedLabelColor = delta,
        cursorColor = seed,
        errorCursorColor = md_theme_light_error,
        errorLabelColor = md_theme_light_error,
        errorIndicatorColor = md_theme_light_error
    )
    Column {
        Text(
            text = stringResource(R.string.content_name),
            style = typography.titleMedium
        )
        Spacer(modifier = modifier.height(4.dp))
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = nickName,
            onValueChange = setNickName,
            label = { stringResource(id = R.string.content_name) },
            textStyle = typography.titleLarge,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = textFieldColors
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SegmentedButtonSexual(
    onChangeGender: (String) -> Unit = {},
) {
    val sexual = remember {
        mutableStateOf("여성")
    }

    Column {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 4.dp),
            text = stringResource(R.string.title_sexual),
            style = typography.titleMedium
        )

        Row {
            SegmentedButton(sexual, "여성", -1, onChangeGender = onChangeGender)
            SegmentedButton(sexual, "남성", 1, onChangeGender = onChangeGender)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BirthPicker(
    onChangeBirthDate: (String) -> Unit = {},
) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val (datePickerSelected, setDatePickerSelected) = remember {
        mutableStateOf(false)
    }
    val birth = remember {
        mutableStateOf(System.currentTimeMillis())
    }

    onChangeBirthDate(simpleDateFormat.format(Date(birth.value)))

    if (datePickerSelected) {
        ShowDatePickerDialog(
            setDatePickerState = setDatePickerSelected,
            birth = birth
        )
    }

    Column {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 4.dp),
            text = stringResource(R.string.title_birth),
            style = typography.titleMedium
        )

        Card(
            border = BorderStroke(width = 1.dp, color = md_theme_light_outline),
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background),
            shape = shapes.extraSmall,
            onClick = { setDatePickerSelected(!datePickerSelected) }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                text = simpleDateFormat.format(Date(birth.value)),
                style = typography.titleMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePickerDialog(
    setDatePickerState: (Boolean) -> Unit,
    birth: MutableState<Long>,
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = birth.value,
            yearRange = IntRange(1900, LocalDate.now().year)
        )
    val confirmEnabled = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis != null &&
                    datePickerState.selectedDateMillis != System.currentTimeMillis()
        }
    }

    DatePickerDialog(
        shape = RoundedCornerShape(28.dp),
        onDismissRequest = { setDatePickerState(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    birth.value = datePickerState.selectedDateMillis!!
                    setDatePickerState(false)
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = stringResource(id = R.string.content_ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    setDatePickerState(false)
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = stringResource(id = R.string.content_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}