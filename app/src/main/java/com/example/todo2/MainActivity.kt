package com.example.todo2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo2.ui.theme.Todo2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Todo2Theme {
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()
                val tasks = remember {
                    mutableStateListOf<task>(
                        task("amin"),
                        task("ali")
                    )
                }
                var color by remember { mutableStateOf("Option1") }
                var text by remember {
                    mutableStateOf("")

                }

                var showBottomSheet by remember { mutableStateOf(false) }
                Scaffold(
                    //topBar = { TopAppBar(title = { Text(text = "TODO ") }) },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showBottomSheet = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    }
                ) {


                        contentPadding ->
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            modifier = Modifier.height(400.dp),
                            onDismissRequest = {
                                showBottomSheet = false
                            },
                            sheetState = sheetState
                        ) {
                            // Sheet content
                            Column {
                                Row {
                                    Text(
                                        text = "task: ",
                                        Modifier.padding(top = 14.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 26.sp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedTextField(
                                        value = text, onValueChange = {
                                            text = it
                                        },
                                        singleLine = true
                                    )


                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Row {
                                    Text(
                                        text = "Red: ",
                                        Modifier.padding(top = 7.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 26.sp
                                    )
                                    RadioButton(
                                        selected = color == "Option1",
                                        onClick = { color = "Option1" })
                                }
                                Row {
                                    Text(
                                        text = "Gray: ",
                                        Modifier.padding(top = 7.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 26.sp
                                    )
                                    RadioButton(
                                        selected = color == "Option2",
                                        onClick = { color = "Option2" })
                                }
                                Row {
                                    Text(
                                        text = "Yallow: ",
                                        Modifier.padding(top = 7.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 26.sp
                                    )
                                    RadioButton(
                                        selected = color == "Option3",
                                        onClick = { color = "Option3" })
                                }
                                Button(onClick = {
                                    scope.launch {
                                        sheetState.hide()
                                    }.invokeOnCompletion {
                                        if (color == "Option3") {
                                            tasks.add(task(text, false, Color.Yellow))

                                        } else if (color == "Option1") {
                                            tasks.add(task(text, false, Color.Red))

                                        } else if (color == "Option2") {
                                            tasks.add(task(text, false, Color.Gray))

                                        } else {
                                            tasks.add(task(text, false))

                                        }



                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                            text = ""

                                        }

                                    }
                                }) {
                                    Text("SAVE")
                                }
                            }

                        }
                    }
                    LazyColumn {
                        items(tasks) { task ->

                            ListItem(
                                colors = ListItemDefaults.colors(containerColor = task.color),
                                headlineContent = {
                                    Text(text = task.task, fontSize = 30.sp)
                                }, trailingContent = {
                                    IconButton(onClick = {
                                        tasks.remove(task)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = ""
                                        )
                                    }
                                }, leadingContent = {
                                    var checked by remember {
                                        mutableStateOf(task.isComplete)
                                    }
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = {
                                            checked = it

                                        }
                                    )
                                })
//                    Row(
//                        Modifier
//                            .fillMaxWidth()
//                            .background(task.color),
//                        horizontalArrangement = Arrangement.spacedBy(20.dp)
//                    ) {
//
//
//
//                    }

                        }
                    }
                }
            }
        }
    }
}

data class task(val task: String, var isComplete: Boolean = false, var color: Color = Color.Gray)
