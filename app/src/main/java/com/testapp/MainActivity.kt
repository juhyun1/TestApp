package com.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.testapp.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTimber()
        setContent {
            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TestBody()
                }
            }
        }
    }
}


private fun setupTimber() {
    Timber.plant(Timber.DebugTree())
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestAppTheme {
        Greeting("Android")
    }
}

@Composable
fun TestBody(vm: TestAppViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = vm) {
        vm.getTestString()
    }

    var state by vm.serializationState
    Column {
        Row {
            Text(
                text = "Kotlinx Serialization",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Button(
                modifier = Modifier
                    .weight(weight = 1f)
                    .wrapContentWidth(align = Alignment.End),
                onClick = { vm.updateTestString() }) {
                Text(
                    text = "Click",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
            Button(
                modifier = Modifier
                    .weight(weight = 1f)
                    .wrapContentWidth(align = Alignment.End),
                onClick = { vm.updateTestString2() }) {
                Text(
                    text = "Click",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .weight(weight = 1f)
                    .wrapContentWidth(align = Alignment.End),
                onClick = { vm.updateTestString3() }) {
                Text(
                    text = "Click",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
        Text(
            text = "Updated Model Text from DataStore",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        Text(
            text = state.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}