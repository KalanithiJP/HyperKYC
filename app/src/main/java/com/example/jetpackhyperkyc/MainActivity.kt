@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackhyperkyc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.hyperverge.hyperkyc.HyperKyc
import co.hyperverge.hyperkyc.data.models.HyperKycConfig
import com.example.jetpackhyperkyc.ui.theme.JetpackHyperKYCTheme
import java.util.UUID

class MainActivity : ComponentActivity(){

    // Link to the documentation: https://documentation.hyperverge.co/sdks/android-hyperKYC-sdk/

    // Before starting, make sure to add our SDK to your project
    // This can be achieved by adding codes in app/build.gradle.kts and setting.gradle.kts

    private var config = HyperKycConfig(
        "48fa5d",
        "<<appKey>>",
        "faceAuthentication",
        UUID.randomUUID().toString()
    )

    val hyperKycLauncher = registerForActivityResult(HyperKyc.Contract()) { result ->

        // handle result post workflow finish/exit
        // result object contains Details, Error message, Error code, Transaction ID and more.
        println("Hyper Result: $result")
        when (result.status) {
            "user_cancelled" -> {
                Log.d("User Cancelled Run",result.details.toString())
            }
            "error" -> {
                Log.e("Error Run",result.errorMessage.toString())
            }
            "auto_approved",
            "auto_declined",
            "needs_review" -> {
                Log.d("Successful Run",result.details.toString())
            }
        }
    }

    var url = TextFieldValue("")



    @Composable
    private fun SimpleTextField() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                url = newText
            },
            label = { Text("Image URL") }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {
            JetpackHyperKYCTheme {
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    SimpleTextField()
                    Spacer(Modifier.size(10.dp))
                    Button(onClick = {
                        var inputs = HashMap<String, String>()
                        try {
                            inputs.put("imageUrl",url.text)
                            //inputs.put("key2","value2")
                            config.setInputs(inputs)          // To set the inputs to a workflow
                            //config.setDefaultLangCode("vi")   // To set the default language
                            hyperKycLauncher.launch(config)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }){
                        Text(text="Start KYC")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackHyperKYCTheme {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(onClick = {
            }){
                Text(text="Start KYC")
            }
        }
    }
}
