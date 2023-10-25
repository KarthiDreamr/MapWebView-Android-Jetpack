package com.example.exercise5

import android.os.Bundle
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.exercise5.ui.theme.Exercise5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise5Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BingMap()
                }
            }
        }
    }
}

@Composable
fun BingMap() {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                setGeolocationEnabled(true)
            }
            webChromeClient = object : WebChromeClient() {
                override fun onGeolocationPermissionsShowPrompt(
                    origin: String,
                    callback: GeolocationPermissions.Callback
                ) {
                    callback.invoke(origin, true, false)
                }
            }
            loadDataWithBaseURL(null, """
                <!DOCTYPE html>
                <html>
                <head>
                    <title></title>
                    <meta charset="utf-8" />
                    <script type='text/javascript'
                    src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&key=YOUR_BING_MAPS_KEY' 
                    async defer></script>
                    <script type='text/javascript'>
                    function GetMap()
                    {
                        var map = new Microsoft.Maps.Map(document.getElementById('myMap'), {
                            credentials: 'Agk8_TlNOjusAEKMyoAJ6sW2CoNtPLpbYjZM_xo5AMbGIp0J1RbmW8kKsoplzUvg',
                            showLocateMeButton: true
                        });
                        map.setView({ center: new Microsoft.Maps.Location(11.0778, 76.9897), zoom: 17 });
                    }
                    </script>
                </head>
                <body>
                    <div id="myMap" style="position:relative;width:100%;height:800px;"></div>
                </body>
                </html>
            """, "text/html", "UTF-8", null
            )
        }
    })
}
