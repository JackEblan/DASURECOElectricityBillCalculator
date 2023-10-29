package com.android.dasurecoelectricitybillcalculator.presentation.bill_calculator_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.dasurecoelectricitybillcalculator.R
import com.android.dasurecoelectricitybillcalculator.domain.model.ApplianceItem
import com.android.dasurecoelectricitybillcalculator.ui.theme.DASURECOElectricityBillCalculatorTheme

@Composable
fun BillCalculatorScreen(
    modifier: Modifier = Modifier, viewModel: BillCalculatorViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    StatelessScreen(modifier = modifier,
                    state = state,
                    onListItemClick = { name, selectedIndex ->
                        viewModel.onEvent(
                            BillCalculatorEvent.OnSelectAppliance(
                                name, selectedIndex
                            )
                        )
                    },
                    onWattageChange = { viewModel.onEvent(BillCalculatorEvent.OnWattageChange(it)) },
                    onHoursChange = { viewModel.onEvent(BillCalculatorEvent.OnHoursChange(it)) },
                    onDaysChange = {
                        viewModel.onEvent(BillCalculatorEvent.OnDaysChange(it))
                    },
                    onRateChange = { viewModel.onEvent(BillCalculatorEvent.OnRateChange(it)) },
                    onCalculate = {
                        viewModel.onEvent(BillCalculatorEvent.OnCalculate)
                    })
}

@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    state: BillCalculatorState,
    onListItemClick: (String, Int) -> Unit,
    onWattageChange: (String) -> Unit,
    onHoursChange: (String) -> Unit,
    onDaysChange: (String) -> Unit,
    onRateChange: (String) -> Unit,
    onCalculate: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 20.dp)
            )
        } else {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(state.list.size) { i ->
                    Column(modifier = Modifier
                        .clickable {
                            onListItemClick(
                                state.list[i].wattage.toString(), i
                            )
                        }
                        .padding(15.dp), horizontalAlignment = CenterHorizontally) {
                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(corner = CornerSize(10.dp))),
                            model = state.list[i].image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = state.list[i].name, style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (state.selectedIndex == i) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_thumb_up_24),
                                contentDescription = null
                            )
                        } else {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_thumb_up_off_alt_24),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = state.wattage,
            onValueChange = { onWattageChange(it) },
            label = {
                Text(text = "Appliance Wattage")
            },
            isError = state.wattageError != null,
            supportingText = {
                Column {
                    if (state.wattageError != null) {
                        Text(text = state.wattageError, color = MaterialTheme.colorScheme.error)
                    } else if (state.selectedIndex != null) {
                        Text(text = state.list[state.selectedIndex].name)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = state.hours,
            onValueChange = { onHoursChange(it) },
            label = {
                Text(text = "Estimated No. of Hours use")
            },
            isError = state.hoursError != null,
            supportingText = {
                if (state.hoursError != null) {
                    Text(text = state.hoursError, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = state.days,
            onValueChange = { onDaysChange(it) },
            label = {
                Text(text = "Days in Month")
            },
            isError = state.daysError != null,
            supportingText = {
                if (state.daysError != null) {
                    Text(text = state.daysError, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = state.rate,
            onValueChange = { onRateChange(it) },
            label = {
                Text(text = "Power Rate")
            },
            isError = state.rateError != null,
            supportingText = {
                if (state.rateError != null) {
                    Text(text = state.rateError, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (state.applianceMonthlyBill != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = state.applianceMonthlyBill
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
               onClick = { onCalculate() }) {
            Text(text = "Calculate")
        }

    }
}

@Preview
@Composable
fun ScreenPreview() {
    DASURECOElectricityBillCalculatorTheme {
        Surface {
            StatelessScreen(state = BillCalculatorState(
                isLoading = false,
                list = List(20) { index ->
                    ApplianceItem(image = "homero", name = "Mona Ramsey", wattage = index)
                },
                wattage = "novum",
                hours = "interesset",
                days = "prompta",
                rate = "epicurei",
            ),
                            onListItemClick = { _, _ -> },
                            onWattageChange = {},
                            onHoursChange = {},
                            onDaysChange = {},
                            onRateChange = {},
                            onCalculate = {})
        }
    }
}