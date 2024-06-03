package com.example.hacker_plus_plus_mode

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun secondPageForTimedMode(navController: NavController) {

    for(i in 0 until noOfPlayers.value) {
        eachPlayerWhoLostPreviousGame.value[i] = false
    }

    val showGridSize = remember { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier,
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setImageResource(R.drawable.background)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { /* No update needed */ }
    )
    Column(modifier = Modifier
        .padding(10.dp)
        .size(width = 350.dp, height = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            shape = CutCornerShape(20.dp,20.dp,20.dp,20.dp),
            border = BorderStroke(2.dp,
                Color(0,0,0,2)
            ),
            modifier = Modifier
                .width(350.dp)
                .padding(top = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(254,215,183,255))
        ) {
            Text(text = "PLAYER INFORMATION",
                fontSize = 25.sp ,
                modifier = Modifier.padding(horizontal = 40.dp , vertical = 8.dp)
            )
        }
    }
    Column(modifier = Modifier.padding(0.dp)) {
        themeSelector()
        Spacer(modifier = Modifier.padding(8.dp))
        dropDownForNoOfPlayers()
        playerDetails()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(100.dp))
        Image(
            painter = painterResource(id = R.drawable.imageinsert),
            contentDescription = "image needed to be inserted there",
            modifier = Modifier.size(width = 410.dp , height = 202.dp)
        )
    }
    Column(
        modifier = Modifier
            .padding(50.dp)
            .height(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                showGridSize.value = true
            },
            modifier = Modifier
                .size(width = 250.dp, height = 70.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(35.dp)),
            colors = ButtonDefaults.buttonColors(Color(0,190,255))
        ) {
            Text(
                text = "START" ,
                fontSize = 30.sp
            )
        }

        gridSizeTimed(navController = navController , showGridSize)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gridSizeTimed(navController: NavController,
                  showGridSize: MutableState<Boolean>) {
    val dropDownMenuExpanded = remember { mutableStateOf(false) }

    val defaultValueInDropDown = remember { mutableStateOf("Select") }

    if (showGridSize.value) {
        AlertDialog(
            onDismissRequest = { showGridSize.value = false },
            modifier = Modifier.size(250.dp, 200.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.3f),
                shape = CutCornerShape(30.dp, 30.dp, 30.dp, 30.dp),
                colors = CardDefaults.cardColors(containerColor = Color(62, 64, 118)),

                ) {
                Card(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 15.dp, start = 25.dp)
                        .size(200.dp, 50.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red,
                        contentColor = Color.Black
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Choose Grid Size",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(90.dp, 45.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = defaultValueInDropDown.value,
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp)
                                    .width(50.dp),
                                textAlign = TextAlign.Center
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                    contentDescription = "Drop down arrow",
                                    modifier = Modifier
                                        .padding(top = 10.dp, end = 8.dp)
                                        .clickable {
                                            dropDownMenuExpanded.value = !dropDownMenuExpanded.value
                                        }
                                        .scale(1.4f)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = dropDownMenuExpanded.value,
                            onDismissRequest = { /*TODO*/ },
                            modifier = Modifier.width(115.dp)
                        ) {
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "3 x 3", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "3 x 3"
                                    dropDownMenuExpanded.value = false
                                    numRows = 3
                                    numColumnsPerRow = 3
                                }
                            )
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "4 x 4", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "4 x 4"
                                    dropDownMenuExpanded.value = false
                                    numRows = 4
                                    numColumnsPerRow = 4
                                }
                            )
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "5 x 5", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "5 x 5"
                                    dropDownMenuExpanded.value = false
                                    numRows = 5
                                    numColumnsPerRow = 5
                                }
                            )
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "6 x 6", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "6 x 6"
                                    dropDownMenuExpanded.value = false
                                    numRows = 6
                                    numColumnsPerRow = 6
                                }
                            )
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "7 x 7", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "7 x 7"
                                    dropDownMenuExpanded.value = false
                                    numRows = 7
                                    numColumnsPerRow = 7
                                }
                            )
                            DropdownMenuItem(text = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "8 x 8", fontSize = 20.sp)
                                }
                            },
                                onClick = {
                                    defaultValueInDropDown.value = "8 x 8"
                                    dropDownMenuExpanded.value = false
                                    numRows = 8
                                    numColumnsPerRow = 8
                                }
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            for(u in 0 until noOfPlayers.value) {
                                hasSomeoneWonTheMatch.value[u] = false
                            }

                            for(u in 0 until noOfPlayers.value) {
                                eachPlayerWinningCount.value[u] = 0
                            }
                            if (noOfPlayers.value == 1) {
                                navController.navigate("singlePlayerMode")
                            } else {
                                navController.navigate("timedMode")
                            }
                        },
                        modifier = Modifier
                            .size(width = 110.dp, height = 50.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(35.dp)),
                        colors = ButtonDefaults.buttonColors(Color(0, 190, 255))
                    ) {
                        Text(
                            text = "START",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun timedMode(navController: NavController) {

    if(firstBoxVal.value == "") {
        firstBoxVal.value = "PLAYER 1"
    }

    if(secondBoxVal.value == "") {
        secondBoxVal.value = "PLAYER 2"
    }

    val whoseTurn = remember{ mutableStateOf(0) }

    val playerGrid = remember { mutableStateOf(mutableListOf<MutableList<Int?>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { null }) }
    }) }

    val numberGrid = remember { mutableStateOf(mutableListOf<MutableList<Int>>().apply {
        repeat(numRows) { add(MutableList(numColumnsPerRow) { 0 }) }
    }) }

    val eachPlayerVal = remember { mutableStateOf(mutableListOf<Int>().apply {
        repeat(noOfPlayers.value) {add(0)}
    }) }

    val eachPlayerLosingCondition = remember { mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    }) }

    val eachPlayerWinningCondition = remember { mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(noOfPlayers.value) {add(false)}
    }) }

    val eachPlayerTimer = remember { mutableStateOf(mutableListOf<Int>().apply {
        repeat(8) {add(timerVal.value)}
    }) }

    val isEachPlayerTimerRunning = remember { mutableStateOf(mutableListOf<Boolean>().apply {
        repeat(8) {add(true)}
    }) }

    LaunchedEffect(isEachPlayerTimerRunning.value[0] and (whoseTurn.value==0)) {
        if (isEachPlayerTimerRunning.value[0] and (whoseTurn.value==0)) {
            while (eachPlayerTimer.value[0] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[0] = this[0] - 1
                }
            }
            isEachPlayerTimerRunning.value[0] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[1] and (whoseTurn.value==1)) {
        if (isEachPlayerTimerRunning.value[1] and (whoseTurn.value==1)) {
            while (eachPlayerTimer.value[1] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[1] = this[1] - 1
                }
            }
            isEachPlayerTimerRunning.value[1] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[2] and (whoseTurn.value==2)) {
        if (isEachPlayerTimerRunning.value[2] and (whoseTurn.value==2)) {
            while (eachPlayerTimer.value[2] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[2] = this[2] - 1
                }
            }
            isEachPlayerTimerRunning.value[2] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[3] and (whoseTurn.value==3)) {
        if (isEachPlayerTimerRunning.value[3] and (whoseTurn.value==3)) {
            while (eachPlayerTimer.value[3] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[3] = this[3] - 1
                }
            }
            isEachPlayerTimerRunning.value[3] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[4] and (whoseTurn.value==4)) {
        if (isEachPlayerTimerRunning.value[4] and (whoseTurn.value==4)) {
            while (eachPlayerTimer.value[4] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[4] = this[4] - 1
                }
            }
            isEachPlayerTimerRunning.value[4] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[5] and (whoseTurn.value==5)) {
        if (isEachPlayerTimerRunning.value[5] and (whoseTurn.value==5)) {
            while (eachPlayerTimer.value[5] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[5] = this[5] - 1
                }
            }
            isEachPlayerTimerRunning.value[5] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[6] and (whoseTurn.value==6)) {
        if (isEachPlayerTimerRunning.value[6] and (whoseTurn.value==6)) {
            while (eachPlayerTimer.value[6] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[6] = this[6] - 1
                }
            }
            isEachPlayerTimerRunning.value[6] = false
        }
    }

    LaunchedEffect(isEachPlayerTimerRunning.value[7] and (whoseTurn.value==7)) {
        if (isEachPlayerTimerRunning.value[7] and (whoseTurn.value==7)) {
            while (eachPlayerTimer.value[7] > 0) {
                delay(1000L)
                eachPlayerTimer.value = eachPlayerTimer.value.toMutableList().apply {
                    this[7] = this[7] - 1
                }
            }
            isEachPlayerTimerRunning.value[7] = false
        }
    }

    val count = remember { mutableIntStateOf(0) }

    val buttonGrid = generateButtonGrid(
        numRows = numRows, numColumnsPerRow = numColumnsPerRow ,
        whoseTurn , numberGrid , playerGrid , eachPlayerVal , navController, eachPlayerLosingCondition , eachPlayerWinningCondition,count)

    //set background color accordingly after deciding

    eachGameTimed(navController , eachPlayerLosingCondition , eachPlayerWinningCondition ,eachPlayerVal)
    result(navController , eachPlayerLosingCondition , eachPlayerWinningCondition ,eachPlayerVal)

    var colour : Color = Color.Transparent
    if(whoseTurn.value==0) {
        colour = Color.Red
    }
    else if(whoseTurn.value==1) {
        colour = Color.Blue
    }
    else if(whoseTurn.value==2) {
        colour = Color.Green
    }
    else if(whoseTurn.value==3) {
        colour = Color.Yellow
    }
    else if(whoseTurn.value==4) {
        colour = Color.Magenta
    }
    else if(whoseTurn.value==5) {
        colour = Color.Cyan
    }
    else if(whoseTurn.value==6) {
        colour = Color.DarkGray
    }
    else if(whoseTurn.value==7) {
        colour = Color.LightGray
    }
    else {
        colour = Color.Transparent
    }

    if(theme.value=="Normal"){
        if (whoseTurn.value == 0) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_image_red)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 1) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_image_blue)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 2) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_green)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 3) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_yellow)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 4) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_magenta)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 5) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_cyan)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 6) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_darkgrey)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else if (whoseTurn.value == 7) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setImageResource(R.drawable.solid_color_lightgray)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { /* No update needed */ }
            )
        } else {
            //no background
        }
    }
    else if(theme.value=="Space") {
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                ImageView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setImageResource(R.drawable.space_bg)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
            update = { /* No update needed */ }
        )
    }
    else if(theme.value=="Ocean") {
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                ImageView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setImageResource(R.drawable.underwater_bg)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
            update = { /* No update needed */ }
        )
    }

    for(p in eachPlayerWinningCondition.value) {
        if(p) {
            starAnimationForPlayerTwo()
            soundEffectsForWinning()
        }
    }

    Column(horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_circle_24),
            contentDescription = "background for close",
            modifier = Modifier
                .scale(2.5f)
                .padding(end = 15.dp, top = 15.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sharp_close_small_24),
            contentDescription = "close button",
            modifier = Modifier
                .scale(2.5f)
                .offset((-10).dp, (-5).dp)
                .clickable {
                    navController.navigate("firstscreen")
                }
        )
    }
    if(modeSelector.value == "Space"){
        Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {
            Card(
                modifier = Modifier
                    .size(250.dp, 60.dp)
                    .offset(0.dp, 0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Whose turn ?",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Card(
                            colors = CardDefaults.cardColors(colour),
                            modifier = Modifier.size(50.dp, 45.dp)
                        ) {
                            //no content
                        }
                    }
                }
            }
        }
    }
    else if(modeSelector.value == "Ocean"){
        Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {
            Card(
                modifier = Modifier
                    .size(250.dp, 60.dp)
                    .offset(0.dp, 0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Whose turn ?",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Card(
                            colors = CardDefaults.cardColors(colour),
                            modifier = Modifier.size(50.dp, 45.dp)
                        ) {
                            //no content
                        }
                    }
                }
            }
        }
    }
    else {
        //do nothing
    }
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.padding(top = 640.dp))
        if(eachPlayerWhoLostPreviousGame.value[whoseTurn.value]){
            powerUpsForTimed(
                eachPlayerTimer,
                num.value,
                whoseTurn,
                numberGrid,
                playerGrid,
                eachPlayerVal,
                eachPlayerLosingCondition,
                eachPlayerWinningCondition
            )
        }
        Row() {
            Card(
                shape = RoundedCornerShape(20.dp,20.dp,20.dp,20.dp),
                border = BorderStroke(2.dp,
                    Color(0,0,0)
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .offset(-20.dp, 30.dp),
                colors = CardDefaults.cardColors(containerColor = Color(47,50,59))
            ) {
                Text(text = "${eachPlayerTimer.value[whoseTurn.value] / 60} : ${eachPlayerTimer.value[whoseTurn.value] % 60}",
                    textAlign = TextAlign.Center,
                    color = Color(255,97,85),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, 10.dp),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Card(
                shape = CutCornerShape(0.dp,25.dp,25.dp,0.dp),
                border = BorderStroke(2.dp,
                    Color(0,0,0)
                ),
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
                    .offset(0.dp, 28.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(22.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(47,50,59))
            ) {
                var player = remember { mutableStateOf("PLAYER") }
                if(whoseTurn.value==0) {
                    player.value = firstBoxVal.value
                }
                else if(whoseTurn.value==1) {
                    player.value = secondBoxVal.value
                }
                else if(whoseTurn.value==2) {
                    player.value = thirdBoxVal.value
                }
                else if(whoseTurn.value==3) {
                    player.value = fourthBoxVal.value
                }
                else if(whoseTurn.value==4) {
                    player.value = fifthBoxVal.value
                }
                else if(whoseTurn.value==5) {
                    player.value = sixthBoxVal.value
                }
                else if(whoseTurn.value==6) {
                    player.value = seventhBoxVal.value
                }
                else if(whoseTurn.value==7) {
                    player.value = eighthBoxVal.value
                }
                else {
                    //do nothing
                }
                Text(text = player.value,
                    textAlign = TextAlign.Center,
                    color = Color(255,97,85),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset((-7).dp, 10.dp)
                        .shadow(15.dp, shape = RoundedCornerShape(22.dp)),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Card(
                shape = RoundedCornerShape(20.dp,0.dp,0.dp,20.dp),
                border = BorderStroke(2.dp,
                    Color(0,0,0)
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .offset(0.dp, 30.dp),
                colors = CardDefaults.cardColors(containerColor = Color(47,50,59))
            ) {
                Text(text = "${eachPlayerVal.value[whoseTurn.value]}",
                    textAlign = TextAlign.Center,
                    color = Color(255,97,85),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.size(300.dp,50.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp,20.dp,20.dp,20.dp),
                modifier = Modifier
                    .size(300.dp, 50.dp)
                    .offset(0.dp, 50.dp),
                colors = CardDefaults.cardColors(Color.DarkGray)
            ) {

            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.size(300.dp,50.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp,20.dp,20.dp,20.dp),
                modifier = Modifier.size(((300*eachPlayerTimer.value[whoseTurn.value])/timerVal.value).dp,50.dp),
                colors = CardDefaults.cardColors(Color.Magenta)
            ) {

            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Log.d("winningcondition.value" , winningCondition.value.toString())
        buttonGrid.forEach { row ->
            Spacer(modifier = Modifier.padding(vertical = 3.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { buttonContent ->
                    buttonContent()
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                }
            }
        }
    }
}

fun trackingEachGameForTimed(eachPlayerWinningCondition: MutableState<MutableList<Boolean>>,
                     eachPlayerVal: MutableState<MutableList<Int>>) {
    for(p in 0 until noOfPlayers.value) {
        if(eachPlayerWinningCondition.value[p]) {
            winningCondition.value = true
            if(eachPlayerWinningCondition.value[p]) {
                winningCondition.value = true
                if (p == 0) {
                    winner.value = firstBoxVal.value
                } else if (p == 1) {
                    winner.value = secondBoxVal.value
                } else if (p == 2) {
                    winner.value = thirdBoxVal.value
                } else if (p == 3) {
                    winner.value = fourthBoxVal.value
                } else if (p == 4) {
                    winner.value = fifthBoxVal.value
                } else if (p == 5) {
                    winner.value = sixthBoxVal.value
                } else if (p == 6) {
                    winner.value = seventhBoxVal.value
                } else if (p == 7) {
                    winner.value = eighthBoxVal.value
                } else {
                    //do nothing
                }
                winningValue.value = eachPlayerVal.value[p]
                break
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun eachGameTimed(navController: NavController,
                  eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
                  eachPlayerWinningCondition: MutableState<MutableList<Boolean>>,
                  eachPlayerVal: MutableState<MutableList<Int>>) {

    Log.d("I am inside each game" , "hi")

    if(winningCondition.value) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier.size(width = 350.dp, height = 250.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.3f),
                colors = CardDefaults.cardColors(containerColor = Color(62, 64, 118)),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        var hello : String = ""
                        if (winner.value == "0") {
                            hello = firstBoxVal.value
                        }
                        else if(winner.value == "1") {
                            hello = secondBoxVal.value
                        }
                        else if(winner.value == "2") {
                            hello = thirdBoxVal.value
                        }
                        else if(winner.value == "3") {
                            hello = fourthBoxVal.value
                        }
                        else if(winner.value == "4") {
                            hello = fifthBoxVal.value
                        }
                        else if(winner.value == "5") {
                            hello = sixthBoxVal.value
                        }
                        else if(winner.value == "6") {
                            hello = seventhBoxVal.value
                        }
                        else if(winner.value == "7") {
                            hello = eighthBoxVal.value
                        }
                        else {
                            hello = "NONE"
                        }
                        Text(text = hello, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.padding(top = 40.dp))
                    Image(
                        painter = painterResource(id = R.drawable.result),
                        contentDescription = "result",
                        modifier = Modifier.scale(2f)
                    )
                    Spacer(modifier = Modifier.padding(top = 50.dp))

                    val context = LocalContext.current
                    val sharedPreferences = remember {
                        context.getSharedPreferences(
                            "Color Conquest",
                            Context.MODE_PRIVATE
                        )
                    }

                    Button(
                        onClick = {

                            for(i in 0 until noOfPlayers.value) {
                                eachPlayerWhoLostPreviousGame.value[i] = eachPlayerLosingCondition.value[i]
                                eachPlayerLosingCondition.value[i] = false
                                eachPlayerWinningCondition.value[i] = false
                                eachPlayerVal.value[i] = 0
                            }

                            val oneMoreThanHalf = (numberOfGames.value) / 2 + 1

                            eachPlayerWinningCount.value[winner.value.toInt()]++

                            if(eachPlayerWinningCount.value[winner.value.toInt()] >= oneMoreThanHalf){
                                hasSomeoneWonTheMatch.value[winner.value.toInt()] = true
                            }

                            trackingResult(eachPlayerVal)

                            saveText(
                                sharedPreferences,
                                track.value.toString(),
                                winningValue.value.toString() + winner.value
                            )

                            managingPersistentDisplay(sharedPreferences)

                            winningCondition.value = false

                            navController.navigate("timedMode")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0, 190, 255),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(width = 150.dp, height = 40.dp)
                    ) {
                        Text(text = "Continue", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun powerUpsForTimed(eachPlayerTimer : MutableState<MutableList<Int>>,
                     num : Int,
                     whoseTurn : MutableState<Int>,
                     numberGrid : MutableState<MutableList<MutableList<Int>>>,
                     playerGrid : MutableState<MutableList<MutableList<Int?>>>,
                     eachPlayerVal: MutableState<MutableList<Int>>,
                     eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
                     eachPlayerWinningCondition: MutableState<MutableList<Boolean>>) {

    val showPlusTen = remember { mutableStateOf(true) }
    val showPlusOneRandom = remember { mutableStateOf(true) }
    val showSkipTurn = remember { mutableStateOf(true) }

    //playerLost is false when blue loses and true when red loses

    Log.d("playerLost" , playerLost.value.toString())

    Row(horizontalArrangement = Arrangement.End) {
        if(showPlusTen.value) {
            Card(modifier = Modifier.size(35.dp, 35.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.baseline_timer_10_24),
                        contentDescription = "Extra time",
                        modifier = Modifier
                            .scale(1.5f)
                            .clickable {
                                eachPlayerTimer.value[whoseTurn.value] += 10
                                showPlusTen.value = false
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        if(showPlusOneRandom.value) {
            Card(modifier = Modifier.size(35.dp, 35.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.baseline_exposure_plus_1_24),
                        contentDescription = "+1 effect",
                        modifier = Modifier
                            .scale(1.3f)
                            .clickable {
                                var a = true
                                for (i in eachPlayerVal.value) {
                                    if (i == 0) {
                                        a = false
                                        break
                                    }
                                }
                                if (a) {
                                    val row = Random.nextInt(0, numRows)
                                    val column = Random.nextInt(0, numColumnsPerRow)
                                    numberGrid.value[row][column]++
                                    playerGrid.value[row][column] = whoseTurn.value
                                    checkCondition(
                                        row,
                                        column,
                                        num,
                                        whoseTurn,
                                        numberGrid,
                                        playerGrid,
                                        eachPlayerLosingCondition,
                                        eachPlayerWinningCondition,
                                        eachPlayerVal
                                    )
                                    showPlusOneRandom.value = false
                                }
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        if(showSkipTurn.value) {
            Card(modifier = Modifier.size(35.dp, 35.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.baseline_skip_next_24),
                        contentDescription = "Skip turn",
                        modifier = Modifier
                            .scale(1.5f)
                            .clickable {
                                //does nothing for now
                                showSkipTurn.value = false
                            }
                    )
                }
            }
        }
    }
}