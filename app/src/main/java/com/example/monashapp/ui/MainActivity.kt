package com.example.monashapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monashapp.viewmodel.MainViewModel
import com.example.monashapp.R
import com.example.monashapp.model.ScheduleByDate
import com.example.monashapp.model.ScheduleForDate
import com.example.monashapp.ui.theme.MonashAppTheme
import com.example.monashapp.ui.theme.lightBackground
import dagger.hilt.android.AndroidEntryPoint

//Please note that i have used dummy font and icons as could not get them
//from the PDF

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonashAppTheme {
                Row(Modifier.background(color = lightBackground)) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                        )
                        NavigationRow(mainViewmodel)
                        CardListing(mainViewmodel)
                        ParkingHeader()
                        ParkingSpotCard(mainViewmodel)
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationRow(mainViewmodel: MainViewModel) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 4.dp),
        text = "Hey, " + mainViewmodel.getNameOfUser(),
        style = userHeading
    )
}

@Composable
fun ParkingHeader() {
    Spacer(Modifier.height(20.dp))
    Text(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 4.dp),
        text = "Available parking spots",
        style = parkingSpotHeader
    )
    Spacer(Modifier.height(16.dp))
}

@Composable
fun ParkingSpotCard(mainViewmodel: MainViewModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            mainViewmodel.getAllParkingDetails().forEach { parkingDetail ->
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(start = 16.dp)
                            .weight(1f),
                        text = parkingDetail.parkingZone,
                        textAlign = TextAlign.Start,
                        style = dayTimeInCard
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    parkingDetail.parkingSubZones.forEach { parkingSubZone ->
                        //get icon based on the sub-zone value (red or blue icon), here it is simplified
                        Spacer(modifier = Modifier.width(16.dp))
                        val icon =
                            if (parkingSubZone.subZone == "B") R.drawable.ic_launcher_background else R.drawable.ic_launcher_background
                        Icon(
                            modifier = Modifier
                                .width(15.dp)
                                .height(45.dp),
                            painter = painterResource(icon),
                            contentDescription = stringResource(R.string.content_description_for_icon_card_row)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 4.dp),
                            text = parkingSubZone.numberOfSpots,
                            style = dayTimeInCard
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
fun CardListing(mainViewmodel: MainViewModel) {
    val schedule = mainViewmodel.getScheduleByDate()
    schedule.forEach { scheduleDateItem ->
        ScheduleCard(mainViewmodel, scheduleDateItem)
    }
}

@Composable
fun ScheduleCard(mainViewmodel: MainViewModel, scheduleByDate: ScheduleByDate) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 4.dp),
                text = scheduleByDate.date,
                style = dayTimeInCard
            )
            scheduleByDate.scheduleForDate.forEach { schedule ->
                ScheduleRow(mainViewmodel, schedule)
            }
        }
    }
}

@Composable
fun ScheduleRow(mainViewmodel: MainViewModel, schedule: ScheduleForDate) {
    Column {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(15.dp)
                    .height(45.dp),
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.content_description_for_icon_card_row)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.width(60.dp)) {
                val mainTimeText = mainViewmodel.getMainTimeText(schedule)
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 4.dp),
                    text = mainTimeText,
                    style = mainTimeTextStyle
                )
                if (mainViewmodel.isValid(schedule.endTime)) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 4.dp),
                        text = "-" + schedule.endTime,
                        style = smallTimeTextStyle
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            Column {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 4.dp),
                    text = schedule.courseName,
                    style = mainTimeTextStyle
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 4.dp),
                    text = mainViewmodel.getSecondaryText(schedule),
                    style = smallTimeTextStyle
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationRowPreview() {
    MonashAppTheme {
        NavigationRow(MainViewModel())
    }
}