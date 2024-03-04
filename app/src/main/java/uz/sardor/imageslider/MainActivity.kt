package uz.sardor.imageslider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.sardor.imageslider.ui.theme.ImageSliderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageSliderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {


    val images = listOf(
        R.drawable.p1,
        R.drawable.p2,

        R.drawable.p3,

        R.drawable.p4,


    )
val pagerState = rememberPagerState(pageCount =

    images.size
)

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit ){
        while (true){
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }


    Column(modifier =  Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


        Box(modifier = Modifier.wrapContentSize()){
            HorizontalPager(state = pagerState, modifier = Modifier
                .wrapContentSize()
                .padding(26.dp)) { currentPage ->
                Card(
                    modifier.wrapContentSize().background(Color.Transparent),
                        elevation = CardDefaults.cardElevation(8.dp)
                ){
                    Image(painter = painterResource(id = images[currentPage]),contentDescription = null,

                        modifier = Modifier
                            .width(500.dp)
                            .height(200.dp)
                        ,


                        )

                }
                
            }

        IconButton(onClick = {
            val nextPage = pagerState.currentPage+1
            if(nextPage < images.size){
                scope.launch {
                    pagerState.scrollToPage(nextPage)
                }
            }

        },
            modifier = Modifier
                .padding(30.dp)
                .size(30.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape),

            ) {


            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null,
                modifier.fillMaxSize(),
                tint = Color.LightGray

                )


        }

            IconButton(onClick = {

                val previousPage = pagerState.currentPage-1
                if(previousPage >=0){
                    scope.launch {
                        pagerState.scrollToPage(previousPage)
                    }
                }


            },
                modifier = Modifier
                    .padding(30.dp)
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
//                colors = IconButtonDefaults.iconButtonColors(
//                    containerColor = Color(0x52373737)
//                )
            ) {


                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null,
                    modifier.fillMaxSize(),
                    tint = Color.LightGray

                )


            }
        }



PageIndicator(
    pageCount = images.size,
    currentPage = pagerState.currentPage,
    modifier = Modifier
)



    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier.Companion) {

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ){
        repeat(pageCount){
            IndicatorDost(isSelected = it == currentPage)
        }

    }
}

@Composable
fun IndicatorDost(isSelected: Boolean) {

    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
Box(modifier = Modifier.padding(2.dp).size(size.value).clip(CircleShape).background(if(isSelected) Color(0xff373737) else Color.Red,))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageSliderTheme {
        MyApp()
    }
}