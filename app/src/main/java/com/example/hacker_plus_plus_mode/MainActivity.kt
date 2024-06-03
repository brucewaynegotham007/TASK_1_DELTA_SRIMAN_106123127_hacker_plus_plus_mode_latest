package com.example.hacker_plus_plus_mode

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hacker_plus_plus_mode.ui.theme.Hacker_plus_plus_modeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hacker_plus_plus_modeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myApp()
                }
            }
        }
    }
}

private val leckerlioneRegular = FontFamily(Font(resId = R.font.leckerlione_regular))

val theme = mutableStateOf("Normal")

var darkOrNormalMode : MutableState<Boolean> = mutableStateOf(false)

var numRows : Int = 5
var numColumnsPerRow : Int = 5

val noOfPlayers : MutableState<Int> = mutableIntStateOf(8)

val numberOfGames : MutableState<Int> = mutableIntStateOf(1)

val hasSomeoneWonTheMatch : MutableState<MutableList<Boolean>> = mutableStateOf(mutableListOf<Boolean>().apply {
    repeat(noOfPlayers.value) {add(false)}
})

val eachPlayerWinningCount : MutableState<MutableList<Int>> = mutableStateOf(mutableListOf<Int>().apply {
    repeat(noOfPlayers.value) {add(0)}
})

val eachPlayerWhoLostPreviousGame : MutableState<MutableList<Boolean>> = mutableStateOf(mutableListOf<Boolean>().apply {
    repeat(noOfPlayers.value) {add(false)}
})

val timerVal : MutableState<Int> = mutableIntStateOf(0)

//check out on the glitches man .. single player mode worked perfectly and now it is glitching like anything for no reason
//no changes were made too .. check it out .. 
//that should set up everything for the normal mode ..
//focus on proper submission too..

@Composable
fun myApp() {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("Color Conquest" , Context.MODE_PRIVATE) }
    clearSavedData(sharedPreferences)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstscreen") {
        composable("firstscreen") {
            firstPage(Modifier.fillMaxSize() , navController)
        }
        composable("secondscreen") {
            secondPage(navController)
        }
        composable("rulespage") {
            rulesPage(navController)
        }
        composable("thirdscreen") {
            thirdPage(navController)
        }
        composable("secondPageForTimedMode") {
            secondPageForTimedMode(navController)
        }
        composable("timedMode") {
            timedMode(navController)
        }
        composable("singlePlayerMode") {
            singlePlayerMode(navController)
        }
    }
}

@Composable
fun firstPage(modifier: Modifier = Modifier , navController: NavController) {

    modeSelector.value = "Normal"

    AndroidView(
        modifier = modifier,
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
    ContentOnTopOfImage(navController = navController)
}

@Composable
fun ContentOnTopOfImage(modifier: Modifier = Modifier , navController: NavController) {

    Column(
        modifier = Modifier.padding(end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "COLOUR",
            textAlign = TextAlign.Center,
            fontSize = 72.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = leckerlioneRegular,
            style = TextStyle(brush = Brush.linearGradient(
                colors = listOf(Color(0xff1b1010) , Color(0xff744a3e))
            ))
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "CONQUEST",
            textAlign = TextAlign.Center,
            fontSize = 72.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = leckerlioneRegular,
            style = TextStyle(brush = Brush.linearGradient(
                colors = listOf(Color(0xff925956) , Color(0xffdc8681))
            )
            )
        )
    }

    persistentDisplay()

    Column(
        modifier = Modifier
            .height(36.dp)
            .offset(0.dp, 84.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.imageinsert),
            contentDescription = "image needed to be inserted there",
            modifier = Modifier.size(width = 370.dp , height = 202.dp)
        )
    }

    val isChecked = remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .padding(top = 620.dp)
        .fillMaxWidth()
    ) {
        Text(text = "LIGHT MODE",
            modifier = Modifier.padding(start = 50.dp , top = 8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(start = 15.dp))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Switch(checked = isChecked.value,
                onCheckedChange = {
                    darkOrNormalMode.value = !darkOrNormalMode.value
                    isChecked.value = !isChecked.value
                }
            )
        }
        Spacer(modifier = Modifier.padding(start = 15.dp))
        Text(text = "DARK MODE",
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    Column(
        modifier = modifier
            .padding(50.dp)
            .height(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val showAdditionalModeOption = remember { mutableStateOf(false) }
            Button(
                onClick = { showAdditionalModeOption.value = true},
                modifier = Modifier.size(width = 250.dp , height = 70.dp),
                colors = ButtonDefaults.buttonColors(Color(0,190,255))
            ) {
                Text(
                    text = "PLAY" ,
                    fontSize = 30.sp
                )
            }
            if(showAdditionalModeOption.value) {
                additionalModeOption(navController)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Image(painter = painterResource(id = R.drawable.baseline_help_24),
                contentDescription = "Help Button",
                modifier = Modifier
                    .clickable {
                        navController.navigate("rulespage")
                    }
                    .scale(3f)
                    .height(65.dp)
            )
        }
    }
}

val modeSelector = mutableStateOf("Normal")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun additionalModeOption(navController: NavController) {

    val heightOfDialog = remember{ mutableIntStateOf(0) }

    val topPadding = remember { mutableIntStateOf(0) }

    val playbuttonPadding = remember { mutableIntStateOf(0) }

    val showMinutesAndSeconds = remember { mutableStateOf(false) }

    val textFieldMinutes = remember { mutableStateOf("0")}

    val textFieldSeconds = remember { mutableStateOf("0")}

    if(modeSelector.value == "Timed") {
        heightOfDialog.value = 200
        topPadding.value = 15
        playbuttonPadding.value = 5
        showMinutesAndSeconds.value = true
    }
    else {
        heightOfDialog.value = 0
        topPadding.value = 0
        playbuttonPadding.value = 0
        showMinutesAndSeconds.value = false
    }

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        modifier = Modifier.size(width = 275.dp, height = (450 + heightOfDialog.value).dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.3f),
            colors = CardDefaults.cardColors(containerColor = Color.Yellow),
        ) {
            Spacer(modifier = Modifier.padding((20 + topPadding.value).dp))
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .size(200.dp, 60.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red,
                    contentColor = Color.Black
                )
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Choose number of Games per Match :", fontSize = 18.sp , textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.padding(7.dp))
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                noOfGamesInCurrentMatch()
            }
            Spacer(modifier = Modifier.padding(top = 30.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Card(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(start = 0.dp)
                            .size(140.dp, 50.dp),
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
                                text = "Choose Game Mode : ",
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Card(
                        modifier = Modifier.size(90.dp, 45.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = modeSelector.value,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(2.dp))

                val selectedNormal = remember { mutableStateOf(true) }
                val selectedTimed = remember { mutableStateOf(false) }

                Column() {
                    Row() {
                        RadioButton(selected = selectedNormal.value,
                            onClick = {
                                selectedNormal.value = true
                                selectedTimed.value = false
                                modeSelector.value = "Normal"
                            }
                        )
                        Text(
                            text = "NORMAL MODE",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Row() {
                        RadioButton(selected = selectedTimed.value,
                            onClick = {
                                selectedTimed.value = true
                                selectedNormal.value = false
                                modeSelector.value = "Timed"
                            }
                        )
                        Text(
                            text = "TIMED MODE",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }

                if(showMinutesAndSeconds.value) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Row(modifier = Modifier.padding(5.dp)) {
                            Text("Minutes : ",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .size(80.dp, 45.dp)
                                    .padding(start = 5.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    BasicTextField(value = textFieldMinutes.value,
                                        onValueChange = {
                                            textFieldMinutes.value = it
                                        },
                                        textStyle = TextStyle(
                                            textAlign = TextAlign.Center,
                                            fontSize = 15.sp
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.padding(top = 5.dp)) {
                            Text("Seconds : ",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .size(80.dp, 45.dp)
                                    .padding(start = 5.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    BasicTextField(value = textFieldSeconds.value,
                                        onValueChange = {
                                            textFieldSeconds.value = it
                                        },
                                        textStyle = TextStyle(
                                            textAlign = TextAlign.Center,
                                            fontSize = 15.sp
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                }
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.padding((10 + playbuttonPadding.value).dp))

                Button(
                    onClick = {
                        if(modeSelector.value == "Normal") {
                            navController.navigate("secondscreen")
                        }
                        else {
                            if(textFieldSeconds.value == "") textFieldSeconds.value = "0"
                            if(textFieldMinutes.value == "") textFieldMinutes.value = "0"
                            var a = textFieldMinutes.value.toIntOrNull()
                            var b = textFieldSeconds.value.toIntOrNull()
                            if(a==null) a=0
                            if(b==null) b=0
                            timerVal.value = a*60 + b
                            if(timerVal.value <= 0) timerVal.value = 60
                            navController.navigate("secondPageForTimedMode")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0, 190, 255),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(width = 120.dp, height = 45.dp)
                ) {
                    Text(text = "PLAY", fontSize = 24.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun noOfGamesInCurrentMatch() {

    val defaultValueInDropDown = remember { mutableStateOf("Select") }
    val dropDownMenuExpanded = remember{ mutableStateOf(false) }

    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(90.dp,45.dp)
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
                    Text(text = "1", fontSize = 20.sp)
                }
            },
                onClick = {
                    defaultValueInDropDown.value = "1"
                    dropDownMenuExpanded.value = false
                    numberOfGames.value = 1
                }
            )
            DropdownMenuItem(text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "3", fontSize = 20.sp)
                }
            },
                onClick = {
                    defaultValueInDropDown.value = "3"
                    dropDownMenuExpanded.value = false
                    numberOfGames.value = 3
                }
            )
            DropdownMenuItem(text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "5", fontSize = 20.sp)
                }
            },
                onClick = {
                    defaultValueInDropDown.value = "5"
                    dropDownMenuExpanded.value = false
                    numberOfGames.value = 5
                }
            )
            DropdownMenuItem(text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "7", fontSize = 20.sp)
                }
            },
                onClick = {
                    defaultValueInDropDown.value = "7"
                    dropDownMenuExpanded.value = false
                    numberOfGames.value = 7
                }
            )
            DropdownMenuItem(text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "9", fontSize = 20.sp)
                }
            },
                onClick = {
                    defaultValueInDropDown.value = "9"
                    dropDownMenuExpanded.value = false
                    numberOfGames.value = 9
                }
            )
        }
    }
}

@Composable
fun firstPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 115.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = "1" ,
            color = Color(5,5,129),
            fontSize = 140.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp , -78.dp)
        )
        Text(text = "1" ,
            color = Color(255,0,0),
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp , -55.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 60f), // Starting point (x, y)
                end = Offset(170f, 60f), // Ending point (x, y)
                color = Color.Red, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(275f, 60f), // Starting point (x, y)
                end = Offset(445f, 60f), // Ending point (x, y)
                color = Color.Red, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(255,0,0)),
            modifier = Modifier
                .padding(top = 55.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 1",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun secondPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "2",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "2",
            color = Color(0, 190, 255),
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color(0, 190, 255), // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color(0, 190, 255), // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0,190,255)),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 2",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun thirdPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "3",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "3",
            color = Color.Green,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.Green, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.Green, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 3",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun fourthPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "4",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "4",
            color = Color.Yellow,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.Yellow, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.Yellow, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 4",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun fifthPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "5",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "5",
            color = Color.Magenta,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.Magenta, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.Magenta, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 5",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun sixthPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "6",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "6",
            color = Color.Cyan,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.Cyan, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.Cyan, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 6",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun seventhPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "7",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "7",
            color = Color.DarkGray,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.DarkGray, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.DarkGray, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 7",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun eighthPlayerIconSecondPage() {
    Box(
        modifier = Modifier
            .size(width = 185.dp, height = 125.dp)
            .background(
                color = Color(red = 5, blue = 129, green = 5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "8",
            color = Color(5, 5, 129),
            fontSize = 110.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(1.dp, -65.dp)
        )
        Text(
            text = "8",
            color = Color.LightGray,
            fontSize = 95.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset(0.dp, -52.dp)
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                start = Offset(0f, 70f), // Starting point (x, y)
                end = Offset(155f, 70f), // Ending point (x, y)
                color = Color.LightGray, // Line color
                strokeWidth = 10f // Line width
            )
            drawLine(
                start = Offset(290f, 70f), // Starting point (x, y)
                end = Offset(445f, 70f), // Ending point (x, y)
                color = Color.LightGray, // Line color
                strokeWidth = 10f // Line width
            )
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier
                .padding(top = 65.dp)
                .size(200.dp, 50.dp)
        ) {
            Text(text = "PLAYER 8",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

val firstBoxVal : MutableState<String> = mutableStateOf("PLAYER 1")
val secondBoxVal : MutableState<String> = mutableStateOf("PLAYER 2")
val thirdBoxVal : MutableState<String> = mutableStateOf("PLAYER 3")
val fourthBoxVal : MutableState<String> = mutableStateOf("PLAYER 4")
val fifthBoxVal : MutableState<String> = mutableStateOf("PLAYER 5")
val sixthBoxVal : MutableState<String> = mutableStateOf("PLAYER 6")
val seventhBoxVal : MutableState<String> = mutableStateOf("PLAYER 7")
val eighthBoxVal : MutableState<String> = mutableStateOf("PLAYER 8")

val track : MutableState<Int> = mutableIntStateOf(0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField() {
    var textState by remember { mutableStateOf("") }

    TextField(
        value = textState,
        onValueChange = { textState = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Enter text here") },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rulesPage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.size(400.dp,800.dp)) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("GAME LOGIC : \n\n" +
                        "1st Turn of each player: Players can choose any tile on the grid on this turn only. Clicking a tile assigns your colour to it and awards you 3 points on that tile.\n" + "\n" +
                        "Subsequent Turns: After the first turn, players can only click on tiles that already have their own colour. Clicking a tile with your colour adds 1 point to that tile.The background colour indicates the next player.\n" + "\n" +
                        "Conquest and Expansion: When a tile with your colour reaches 4 points, it triggers an expansion:\n" + "\n" +
                        "The colour completely disappears from the original tile.\n" + "\n" +
                        "Your colour spreads to the four surrounding squares in a plus shape (up, down, left, right).\n" + "\n" +
                        "Each of the four surrounding squares gains 1 point with your colour.\n" + "\n" +
                        "If any of the four has your opponent’s colour, you conquer the opponent's points on that tile while adding a point to it, completely erasing theirs.\n" + "\n" +
                        "The expansion is retriggered if the neighbouring tile as well reaches 4 points this way.\n" + "\n" +
                        "Players take turns clicking on tiles and the objective is to eliminate your opponent's colour entirely from the screen.")
            }
        }
    }
}

fun checkCondition(
    i:Int,
    j:Int,
    num: Int,
    whoseTurn: MutableState<Int>,
    numberGrid: MutableState<MutableList<MutableList<Int>>>,
    playerGrid: MutableState<MutableList<MutableList<Int?>>>,
    eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
    eachPlayerWinningCondition: MutableState<MutableList<Boolean>>,
    eachPlayerVal: MutableState<MutableList<Int>>
) {

    // copied from chatgpt's response for "make my clumpsy code neat , tidy and small" ... the logic is
    // the exact same one that I have written for normal and hacker mode ..

    val rowIndex = i
    val columnIndex = j

    if (numberGrid.value[i][j] >= 4) {
        numberGrid.value[i][j] = 0

        // Define directions for neighbors (up, down, left, right)
        val directions = listOf(
            Pair(-1, 0), // Up
            Pair(1, 0),  // Down
            Pair(0, -1), // Left
            Pair(0, 1)   // Right
        )

        // Update neighbors
        for ((dx, dy) in directions) {
            val newX = i + dx
            val newY = j + dy
            if (newX in numberGrid.value.indices && newY in numberGrid.value[newX].indices) {
                numberGrid.value[newX][newY]++
                playerGrid.value[newX][newY] = whoseTurn.value
            }
        }

        // Recursively check neighbors
        for ((dx, dy) in directions) {
            val newX = i + dx
            val newY = j + dy
            if (newX in numberGrid.value.indices && newY in numberGrid.value[newX].indices && numberGrid.value[newX][newY] >= 4) {
                checkCondition(newX, newY, num , whoseTurn, numberGrid, playerGrid, eachPlayerLosingCondition, eachPlayerWinningCondition,eachPlayerVal)
            }
        }
    }

    //Reset eachPlayerVal
    val newEachPlayerVal = MutableList(eachPlayerVal.value.size) { 0 }
    eachPlayerVal.value = newEachPlayerVal

    // Recalculate eachPlayerVal
    for (p in 0 until numRows) {
        for (q in 0 until numColumnsPerRow) {
            val a = playerGrid.value[p][q]
            if (a != null) {
                newEachPlayerVal[a] += numberGrid.value[p][q]
            }
        }
    }
    eachPlayerVal.value = newEachPlayerVal
    var a = 0
    for(z in 0 until eachPlayerVal.value.size) {
        if(eachPlayerVal.value[z] == 0) {
            eachPlayerLosingCondition.value[z] = true
        }
        else {
            a++
        }
    }

    Log.d("a" , a.toString())

    if(a == 1) {
        for(n in 0 until noOfPlayers.value) {
            if(eachPlayerVal.value[n] != 0) {
                eachPlayerWinningCondition.value[n] = true
            }
            Log.d("eachPlayerWinningCondition.value[$n]",eachPlayerWinningCondition.value[n].toString())
        }
    }

    trackingEachGame(eachPlayerWinningCondition, eachPlayerVal)

}

@Composable
private fun explosionAnimation(): ExitTransition {
    val explosionAnimation = fadeOut(
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 100,
            easing = FastOutSlowInEasing
        )
    ) + scaleOut(
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 100,
            easing = FastOutSlowInEasing
        )
    )
    return explosionAnimation
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun displayContent(
    i:Int,
    j:Int,
    num: Int,
    whoseTurn: MutableState<Int>,
    numberGrid: MutableState<MutableList<MutableList<Int>>>,
    playerGrid: MutableState<MutableList<MutableList<Int?>>>,
    eachPlayerVal: MutableState<MutableList<Int>>,
    navController: NavController,
    eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
    eachPlayerWinningCondition: MutableState<MutableList<Boolean>>
) {

    //The following piece of code used for animating was entirely written by ChatGPT 4o
    val enterTransition = remember {
        fadeIn(animationSpec = tween(durationMillis = 300)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 300))
    }

    val exitTransition = remember {
        fadeOut(animationSpec = tween(durationMillis = 300)) +
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300))
    }

    var dummy = 100

    //The following piece of code used for animating was entirely written by ChatGPT 4o
    AnimatedContent(
        targetState = dummy,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            } else {
                (slideInVertically { height -> -height } + fadeIn()).togetherWith(slideOutVertically { height -> height } + fadeOut())
            }
        }, label = "hello"
    ) {targetNum ->
        if(targetNum > 0){
            val hearSound = remember { mutableStateOf(false) }
            if (hearSound.value) {
                soundEffectsForInvalidMove()
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var colorOfButton: Color = Color.White
                if (playerGrid.value[i][j] == 0) {
                    colorOfButton = Color.Red
                } else if (playerGrid.value[i][j] == 1) {
                    colorOfButton = Color.Blue
                } else if (playerGrid.value[i][j] == 2) {
                    colorOfButton = Color.Green
                } else if (playerGrid.value[i][j] == 3) {
                    colorOfButton = Color.Yellow
                } else if (playerGrid.value[i][j] == 4) {
                    colorOfButton = Color.Magenta
                } else if (playerGrid.value[i][j] == 5) {
                    colorOfButton = Color.Cyan
                } else if (playerGrid.value[i][j] == 6) {
                    colorOfButton = Color.DarkGray
                } else if (playerGrid.value[i][j] == 7) {
                    colorOfButton = Color.LightGray
                } else {
                    colorOfButton = Color.Transparent
                }

                Button(
                    onClick = {

                        trackingWhoseTurn(eachPlayerLosingCondition, whoseTurn)

                        if (whoseTurn.value != playerGrid.value[i][j]) {
                            hearSound.value = true
                        } else {
                            hearSound.value = false
                            // Convert numberGrid.value[i][j]++
                            val newNumberGrid =
                                numberGrid.value.map { it.toMutableList() }.toMutableList()
                            newNumberGrid[i][j]++
                            numberGrid.value = newNumberGrid

                            checkCondition(
                                i,
                                j,
                                num,
                                whoseTurn,
                                numberGrid,
                                playerGrid,
                                eachPlayerLosingCondition,
                                eachPlayerWinningCondition,
                                eachPlayerVal
                            )

                            whoseTurn.value = ((whoseTurn.value + 1) % (noOfPlayers.value))

                            trackingWhoseTurn(eachPlayerLosingCondition, whoseTurn)

                            //Reset eachPlayerVal
                            val newEachPlayerVal = MutableList(eachPlayerVal.value.size) { 0 }
                            eachPlayerVal.value = newEachPlayerVal

                            // Recalculate eachPlayerVal
                            for (p in 0 until numRows) {
                                for (q in 0 until numColumnsPerRow) {
                                    val a = playerGrid.value[p][q]
                                    if (a != null) {
                                        newEachPlayerVal[a] += numberGrid.value[p][q]
                                    }
                                }
                            }

                            var a = 0

                            eachPlayerVal.value = newEachPlayerVal
                            for (z in 0 until eachPlayerVal.value.size) {
                                if (eachPlayerVal.value[z] == 0) {
                                    eachPlayerLosingCondition.value[z] = true
                                    a++
                                } else {
                                    eachPlayerLosingCondition.value[z] = false
                                }
                            }

                            if (a == noOfPlayers.value - 1) {
                                for (n in 0 until noOfPlayers.value) {
                                    if (eachPlayerVal.value[n] != 0) {
                                        eachPlayerWinningCondition.value[n] = true
                                        break
                                    }
                                }
                            }

                            trackingEachGame(eachPlayerWinningCondition, eachPlayerVal)

                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorOfButton,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .size(
                            width = (60 - (numRows - 3) * 5).dp,
                            height = (60 - (numColumnsPerRow - 3) * 5).dp
                        ), //5=50 , 3=60 , 4=55 , 6=45 , 7=40
                    contentPadding = PaddingValues(1.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$num",
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            fontSize = (35 - (numRows - 3) * 2).sp, // 3=35 4=33 5=31 6=29 7=27 8=25
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun trackingWhoseTurn(eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
                      whoseTurn: MutableState<Int>) {
    if(eachPlayerLosingCondition.value[whoseTurn.value]) {
        whoseTurn.value = ((whoseTurn.value+1)%(noOfPlayers.value))
        trackingWhoseTurn(eachPlayerLosingCondition,whoseTurn)
    }
    else {
        //do nothing
    }
}

@Composable
fun generateButtonGrid(
    numRows: Int,
    numColumnsPerRow: Int,
    whoseTurn: MutableState<Int>,
    numberGrid: MutableState<MutableList<MutableList<Int>>>,
    playerGrid: MutableState<MutableList<MutableList<Int?>>>,
    eachPlayerVal: MutableState<MutableList<Int>>,
    navController: NavController,
    eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
    eachPlayerWinningCondition: MutableState<MutableList<Boolean>>,
    count: MutableState<Int>
): List<List<@Composable () -> Unit>> {
    val buttonGrid = mutableListOf<MutableList<@Composable () -> Unit>>()

    for (i in 0 until numRows) {
        val row = mutableListOf<@Composable () -> Unit>()
        for (j in 0 until numColumnsPerRow) {
            val buttonContent: @Composable () -> Unit = {
                Box(
                    modifier = Modifier
                        .size(
                            width = (65 - (numColumnsPerRow - 3) * 5).dp,
                            height = (65 - (numRows - 3) * 5).dp
                        ) // 8=40 , 5=55 , 6=50 , 7=45 , 4=60 , 3=65
                        .background(
                            color = Color(245, 229, 206),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .scale(1f)
                        .clickable {

                            trackingWhoseTurn(eachPlayerLosingCondition, whoseTurn)

                            Log.d("count", count.value.toString())

                            if (count.value < noOfPlayers.value) {
                                val newNumberGrid = numberGrid.value
                                    .map { it.toMutableList() }
                                    .toMutableList()
                                newNumberGrid[i][j] += 3
                                numberGrid.value = newNumberGrid

                                val newPlayerGrid = playerGrid.value
                                    .map { it.toMutableList() }
                                    .toMutableList()
                                newPlayerGrid[i][j] = whoseTurn.value
                                playerGrid.value = newPlayerGrid

                                whoseTurn.value = ((whoseTurn.value + 1) % (noOfPlayers.value))
                            } else {
                                //do nothing
                            }

                            count.value++

                        }
                ) {
                    val rowIndex = i
                    val columnIndex = j

                    val enterTransition = remember {
                        fadeIn(animationSpec = tween(durationMillis = 300)) +
                                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 300))
                    }

                    val exitTransition = remember {
                        fadeOut(animationSpec = tween(durationMillis = 300)) +
                                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300))
                    }

                    var dummy = 100

                    //The following piece of code used for animating was entirely written by ChatGPT 4o
                    AnimatedContent(
                        targetState = dummy,
                        transitionSpec = {
                            if (targetState > initialState) {
                                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
                            } else {
                                (slideInVertically { height -> -height } + fadeIn()).togetherWith(slideOutVertically { height -> height } + fadeOut())
                            }
                        }, label = "hello"
                    ) {targetNum ->
                        if(targetNum > 0){
                            if (numberGrid.value[i][j] != 0) {
                                displayContent(
                                    i = i,
                                    j = j,
                                    num = numberGrid.value[i][j],
                                    whoseTurn = whoseTurn,
                                    playerGrid = playerGrid,
                                    numberGrid = numberGrid,
                                    eachPlayerLosingCondition = eachPlayerLosingCondition,
                                    eachPlayerVal = eachPlayerVal,
                                    navController = navController,
                                    eachPlayerWinningCondition = eachPlayerWinningCondition
                                )
                            }
                        }
                    }
                }
            }
            row.add(buttonContent)
        }
        buttonGrid.add(row)
    }

    return buttonGrid.toList()
}

val thisPlayerWonTheMatch = mutableStateOf(false)
val whoWonTheMatch = mutableStateOf("n")
val valueOfWinnerOfMatch = mutableIntStateOf(0)

fun trackingResult(eachPlayerVal: MutableState<MutableList<Int>>) {
    for(y in 0 until hasSomeoneWonTheMatch.value.size) {
        if(hasSomeoneWonTheMatch.value[y]) {
            thisPlayerWonTheMatch.value = true
            whoWonTheMatch.value = y.toString()
            var hello : String = ""
            if (whoWonTheMatch.value == "0") {
                hello = firstBoxVal.value
            }
            else if(whoWonTheMatch.value == "1") {
                hello = secondBoxVal.value
            }
            else if(whoWonTheMatch.value == "2") {
                hello = thirdBoxVal.value
            }
            else if(whoWonTheMatch.value == "3") {
                hello = fourthBoxVal.value
            }
            else if(whoWonTheMatch.value == "4") {
                hello = fifthBoxVal.value
            }
            else if(whoWonTheMatch.value == "5") {
                hello = sixthBoxVal.value
            }
            else if(whoWonTheMatch.value == "6") {
                hello = seventhBoxVal.value
            }
            else if(whoWonTheMatch.value == "7") {
                hello = eighthBoxVal.value
            }
            else {
                hello = "NONE"
            }
            valueOfWinnerOfMatch.value = eachPlayerVal.value[y]
            whoWonTheMatch.value = hello
            break
        }
        else {
            thisPlayerWonTheMatch.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun result(navController: NavController,
           eachPlayerLosingCondition: MutableState<MutableList<Boolean>>,
           eachPlayerWinningCondition : MutableState<MutableList<Boolean>>,
           eachPlayerVal: MutableState<MutableList<Int>>) {

    trackingResult(eachPlayerVal)

    if(thisPlayerWonTheMatch.value) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier.size(width = 350.dp, height = 350.dp)
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
                    Spacer(modifier = Modifier.padding(top = 20.dp))
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
                        Text(text = whoWonTheMatch.value , fontSize = 18.sp)
                    }
                    Column(modifier = Modifier.size(150.dp,150.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.trophy),
                            contentDescription = "result",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 0.dp))
                    Button(
                        onClick = {
                            thisPlayerWonTheMatch.value = false
                            if(modeSelector.value=="Normal") {
                                navController.navigate("secondscreen")
                            }
                            else {
                                navController.navigate("secondPageForTimedMode")
                            }
                            firstBoxVal.value = "PLAYER 1"
                            secondBoxVal.value = "PLAYER 2"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0, 190, 255),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(width = 150.dp, height = 40.dp)
                    ) {
                        Text(text = "Play Again" , fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Button(
                        onClick = {
                            thisPlayerWonTheMatch.value = false
                            navController.navigate("firstscreen")
                            firstBoxVal.value = "PLAYER 1"
                            secondBoxVal.value = "PLAYER 2"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(width = 150.dp, height = 40.dp)
                    ) {
                        Text(text = "Home" , fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

var savedItems : MutableList<String> = mutableListOf()
var savedPlayers : MutableList<String> = mutableListOf()

fun managingPersistentDisplay(sharedPreferences: SharedPreferences) {

    var temp : String = getSavedText(sharedPreferences , track.value.toString())

    if (temp != "No Data") {
        var winner: Char = temp.last()
        temp = temp.dropLast(1)
        savedItems.add(temp)
        if (winner == '0') {
            savedPlayers.add(firstBoxVal.value)
        }
        else if(winner == '1') {
            savedPlayers.add(secondBoxVal.value)
        }
        else if(winner == '2') {
            savedPlayers.add(thirdBoxVal.value)
        }
        else if(winner == '3') {
            savedPlayers.add(fourthBoxVal.value)
        }
        else if(winner == '4') {
            savedPlayers.add(fifthBoxVal.value)
        }
        else if(winner == '5') {
            savedPlayers.add(sixthBoxVal.value)
        }
        else if(winner == '6') {
            savedPlayers.add(seventhBoxVal.value)
        }
        else if(winner == '7') {
            savedPlayers.add(eighthBoxVal.value)
        }
        else {
            savedPlayers.add("NONE")
        }
    }
}

@Composable
fun persistentDisplayTableHeadings(temp : String) { // temp must be made globally .. just for time being it is passed here
    if(temp!= "No Data") {
        Row(
            modifier = Modifier.offset(0.dp, (-100).dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "S.no",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(-62.dp,0.dp),
                fontSize = 20.sp
            )
            Text(text = "Player who won",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(10.dp,0.dp),
                fontSize = 20.sp
            )
            Text(text = "Score",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(60.dp,0.dp),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun persistentDisplay() {

    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("Color Conquest" , Context.MODE_PRIVATE) }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {

        persistentDisplayTableHeadings(temp = "Hello world") // change this accordingly mate

        LazyColumn(
            modifier = Modifier
                .offset(0.dp, (-80).dp)
                .height(105.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(savedItems) { index, item ->

                Log.d("index", index.toString())

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = (index + 1).toString(),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .offset(-62.dp, 0.dp)
                            .width(50.dp),
                        fontSize = 20.sp
                    )
                    Text(text = savedPlayers[index],
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .offset(5.dp, 0.dp)
                            .width(110.dp),
                        fontSize = 20.sp
                    )
                    Text(text = item,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .offset(100.dp, 0.dp)
                            .width(50.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

fun getSavedText(sharedPreferences: SharedPreferences , key : String) : String {
    return sharedPreferences.getString( key, "No Data") ?: "No Data"
}

fun saveText(sharedPreferences: SharedPreferences, key: String , value: String) {
    sharedPreferences.edit().putString(key , value).apply()
}

fun clearSavedData(sharedPreferences: SharedPreferences) {
    val editor = sharedPreferences.edit()
    editor.clear().apply()
}

val playerLost : MutableState<Boolean?> = mutableStateOf(null)

@Composable
fun starAnimationForPlayerTwo() {
    val infiniteTransition = rememberInfiniteTransition(label = "Moving Star Animation")

    val xPosition by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000 , easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Moving Star Animation x coordinate"
    )

    val yPosition by infiniteTransition.animateFloat(
        initialValue = -1200f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000 , easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Moving Star Animation y coordinate"
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset(xPosition.dp, yPosition.dp)
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset((xPosition - 100).dp, (yPosition - 400).dp)
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset((xPosition - 200).dp, (yPosition - 800).dp)
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset((xPosition - 600).dp, (yPosition - 800).dp)
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset((xPosition - 500).dp, (yPosition - 400).dp)
    )

    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
        contentDescription = "star1",
        modifier = Modifier
            .scale(0.2f)
            .offset((xPosition - 400).dp, (yPosition).dp)
    )
}

@Composable
fun soundEffectsForWinning() {

    val context = LocalContext.current
    var soundPool by remember{ mutableStateOf<SoundPool?>(null) }
    var soundId by remember { mutableIntStateOf(0) }

    DisposableEffect(Unit) {

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build().apply {
                soundId = load(context, R.raw.win_sound, 1)
            }

        onDispose {
            soundPool?.release()
        }

    }

    soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)

}

@Composable
fun soundEffectsForInvalidMove() {

    val context = LocalContext.current
    var soundPool by remember{ mutableStateOf<SoundPool?>(null) }
    var soundId by remember { mutableIntStateOf(0) }

    DisposableEffect(Unit) {

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build().apply {
                soundId = load(context, R.raw.wrong_sound, 1)
            }

        onDispose {
            soundPool?.release()
        }

    }

    soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)

}

var num = mutableIntStateOf(0)

@Composable
fun playerDetails() {
    var colour : Color = Color.Transparent
    if(num.value==0) {
        colour = Color.Red
    }
    else if(num.value==1) {
        colour = Color.Blue
    }
    else if(num.value==2) {
        colour = Color.Green
    }
    else if(num.value==3) {
        colour = Color.Yellow
    }
    else if(num.value==4) {
        colour = Color.Magenta
    }
    else if(num.value==5) {
        colour = Color.Cyan
    }
    else if(num.value==6) {
        colour = Color.DarkGray
    }
    else if(num.value==7) {
        colour = Color.LightGray
    }
    else {
        colour = Color.Transparent
    }

    Column(modifier = Modifier.offset(0.dp,-30.dp)) {
        Spacer(modifier = Modifier.padding(top = 150.dp))
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 5.dp)
        ) {

            if(num.value==0) {
                firstPlayerIconSecondPage()
            }
            else if(num.value==1) {
                secondPlayerIconSecondPage()
            }
            else if(num.value==2) {
                thirdPlayerIconSecondPage()
            }
            else if(num.value==3) {
                fourthPlayerIconSecondPage()
            }
            else if(num.value==4) {
                fifthPlayerIconSecondPage()
            }
            else if(num.value==5) {
                sixthPlayerIconSecondPage()
            }
            else if(num.value==6) {
                seventhPlayerIconSecondPage()
            }
            else if(num.value==7) {
                eighthPlayerIconSecondPage()
            }
            else {
                //do nothing
            }

            Spacer(modifier = Modifier.padding(horizontal = 7.dp))
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 115.dp)
                    .background(
                        color = Color(red = 5, blue = 129, green = 5),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if(num.value==0) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_red_24),
                            contentDescription = "red player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==1) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "blue player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==2) {
                        Image(
                            painter = painterResource(id = R.drawable.green_player),
                            contentDescription = "green player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==3) {
                        Image(
                            painter = painterResource(id = R.drawable.yellow_person),
                            contentDescription = "yellow player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==4) {
                        Image(
                            painter = painterResource(id = R.drawable.magenta_person),
                            contentDescription = "magenta player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==5) {
                        Image(
                            painter = painterResource(id = R.drawable.cyan_person),
                            contentDescription = "cyan player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==6) {
                        Image(
                            painter = painterResource(id = R.drawable.dark_grey_person),
                            contentDescription = "darkgrey player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else if(num.value==7) {
                        Image(
                            painter = painterResource(id = R.drawable.lightgrey_person),
                            contentDescription = "lightgrey player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }
                    else {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_red_24),
                            contentDescription = "red player icon",
                            modifier = Modifier
                                .scale(2.5f)
                                .padding(top = 5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    if(num.value==0) {
                        BasicTextField(
                            value = firstBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    firstBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==1) {
                        BasicTextField(
                            value = secondBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    secondBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==2) {
                        BasicTextField(
                            value = thirdBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    thirdBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==3) {
                        BasicTextField(
                            value = fourthBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    fourthBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==4) {
                        BasicTextField(
                            value = fifthBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    fifthBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==5) {
                        BasicTextField(
                            value = sixthBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    sixthBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==6) {
                        BasicTextField(
                            value = seventhBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    seventhBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else if(num.value==7) {
                        BasicTextField(
                            value = eighthBoxVal.value,
                            onValueChange = {
                                if (it.length <= 8) {
                                    eighthBoxVal.value = it
                                }
                            },
                            textStyle = TextStyle(
                                color = Color(red = 151, green = 151, blue = 176),
                                textAlign = TextAlign.Left,
                                letterSpacing = 10.sp,
                                fontFamily = FontFamily.Monospace
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color(red = 5, green = 5, blue = 129),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(150.dp, 15.dp)
                                .offset(-3.dp, 18.dp)
                        )
                    }
                    else {
                        //do nothing
                    }

                    Text(
                        text = "_ _ _ _ _ _ _ _",
                        color = colour,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .size(150.dp, 30.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        if(num.value < noOfPlayers.value-1){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        num.value++
                    },
                    modifier = Modifier.size(150.dp, 50.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownForNoOfPlayers() {

    val dropDownMenuExpanded = remember { mutableStateOf(false) }

    val defaultValueInDropDown = remember { mutableStateOf("Select") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(0.dp, 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Card(
                modifier = Modifier.size(250.dp,45.dp),
                colors = CardDefaults.cardColors(Color(red = 5, green = 5, blue = 129))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "SELECT NO OF PLAYERS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Yellow
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
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
                            Text(text = "1", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "1"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 1
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "2", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "2"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 2
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "3", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "3"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 3
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "4", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "4"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 4
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "5", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "5"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 5
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "6", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "6"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 6
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "7", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "7"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 7
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "8", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "8"
                            dropDownMenuExpanded.value = false
                            noOfPlayers.value = 8
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun themeSelector() {
    val dropDownMenuExpanded = remember { mutableStateOf(false) }

    val defaultValueInDropDown = remember { mutableStateOf("Select") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(0.dp, 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Card(
                modifier = Modifier.size(250.dp,45.dp),
                colors = CardDefaults.cardColors(Color(red = 5, green = 5, blue = 129))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "SELECT THEME",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Yellow
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
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
                            Text(text = "Normal", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "Normal"
                            dropDownMenuExpanded.value = false
                            theme.value = "Normal"
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Space", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "Space"
                            dropDownMenuExpanded.value = false
                            theme.value = "Space"
                        }
                    )
                    DropdownMenuItem(text = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Ocean", fontSize = 20.sp)
                        }
                    },
                        onClick = {
                            defaultValueInDropDown.value = "Ocean"
                            dropDownMenuExpanded.value = false
                            theme.value = "Ocean"
                        }
                    )
                }
            }
        }
    }
}

