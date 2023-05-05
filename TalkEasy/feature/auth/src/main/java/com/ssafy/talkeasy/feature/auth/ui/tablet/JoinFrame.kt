package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.component.SegmentedButton
import com.ssafy.talkeasy.feature.common.ui.theme.TalkEasyTheme
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun JoinFrame() {
    TalkEasyTheme {
        Column {
            SegmentedButtonSexual()

            Spacer(modifier = Modifier.height(24.dp))

            BirthPicker()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SegmentedButtonSexual() {
    val sexual = remember {
        mutableStateOf("")
    }

    Column {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 4.dp),
            text = stringResource(R.string.title_sexual),
            style = typography.titleMedium
        )

        Row {
            SegmentedButton(sexual, "여성", -1)
            SegmentedButton(sexual, "남성", 1)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BirthPicker() {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val (datePickerSelected, setDatePickerSelected) = remember {
        mutableStateOf(false)
    }
    val birth = remember {
        mutableStateOf(System.currentTimeMillis())
    }

    if (datePickerSelected) {
        ShowDatePickerDialog(setDatePickerState = setDatePickerSelected, birth)
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
fun ShowDatePickerDialog(setDatePickerState: (Boolean) -> Unit, birth: MutableState<Long>) {
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
                Text(text = "확인")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    setDatePickerState(false)
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = "취소")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}