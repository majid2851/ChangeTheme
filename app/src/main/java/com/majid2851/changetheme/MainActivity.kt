package com.majid2851.changetheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.majid2851.changetheme.ui.theme.ChangeThemeTheme


class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            ChangeThemeTheme {

                val darkTheme=remember{
                    mutableStateOf(true)
                }
                Surface(
                    color = if(darkTheme.value==true) Color.Black else Color.White,
                    modifier = Modifier.fillMaxSize())
                {

                    val animateState=remember{
                        mutableStateOf(false) //false->Off,True ->On
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )
                    {
                        val SwitchWidth=320
                        val SwitchHeight=70


                        CustomSwitch(
                            width = SwitchWidth,
                            height = SwitchHeight,
                            animateState = animateState,
                            darkTheme = darkTheme
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    width: Int,
    height: Int,
    animateState: MutableState<Boolean>,
    darkTheme: MutableState<Boolean>
)
{
    val animationPriod=600
    val progressState= animateFloatAsState(
        targetValue = if(animateState.value)
        {
            1f
        }else{
            0f
        }
    )
    val switchBackground= animateColorAsState(
        targetValue = if (animateState.value)
        {
            Color.LightGray
        }else{
            Color.DarkGray
        },
        animationSpec = tween(animationPriod)
    )
    val switchColor= animateColorAsState(
        targetValue = if (animateState.value)
        {
            Color(0xFF2196F3)
        }else{
            Color(0xFFF44336)
        },
        animationSpec = tween(animationPriod)
    )
    val textOffFontSize= animateIntAsState(targetValue =
        if(animateState.value){
            25
        }else{
            25
        }

    )



    MotionLayout(
        start = startConstraintSet(parentWidth = width, parentHeight = height),
        end = endConstraintSet(parentWidth = width, parentHeight = height),
        progress = progressState.value
    )
    {
        val boxCorner=50

        Box(
            modifier = modifier
                .layoutId("switchBackground")
                .clip(RoundedCornerShape(boxCorner))
                .background(switchBackground.value)
        )

        Box(
            modifier = modifier
                .layoutId("switch")
                .clip(RoundedCornerShape(boxCorner))
                .background(switchColor.value)
                .clickable {
                    animateState.value=!animateState.value
                    darkTheme.value=!darkTheme.value
                }
        )


        Box(
            modifier = modifier
                .layoutId("textOff"),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = "Light",
                color = Color.White,
                fontSize = textOffFontSize.value.sp,
                fontWeight = FontWeight.Bold,

            )
        }


        Box(
            modifier = modifier
                .layoutId("textOn"),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = "Dark",
                color = Color.White,
                fontSize = textOffFontSize.value.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        
    }

}

private fun startConstraintSet(parentWidth: Int, parentHeight: Int): ConstraintSet {
    return ConstraintSet() {
        val switchBackground = createRefFor("switchBackground")
        val switch = createRefFor("switch")
        val textOff = createRefFor("textOff")
        val textOn = createRefFor("textOn")

        constrain(switchBackground) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(parentWidth.dp)
            height = Dimension.value(parentHeight.dp)
            centerHorizontallyTo(parent)
        }

        // switch: left side default
        constrain(switch) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth * 0.5).dp)
            height = Dimension.value((parentHeight).dp)
        }

        // textOff: left side default
        constrain(textOff) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth * 0.5).dp)
            height = Dimension.value((parentHeight).dp)
        }

        // textOn: right side default
        constrain(textOn) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth *.5).dp)
            height = Dimension.value((parentHeight).dp)
        }
    }
}

private fun endConstraintSet(parentWidth: Int, parentHeight: Int): ConstraintSet {
    return ConstraintSet() {
        val switchBackground = createRefFor("switchBackground")
        val switch = createRefFor("switch")
        val textOff = createRefFor("textOff")
        val textOn = createRefFor("textOn")

        constrain(switchBackground) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(parentWidth.dp)
            height = Dimension.value(parentHeight.dp)
            centerHorizontallyTo(parent)
        }

        // switch: right side default
        constrain(switch) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth * 0.5).dp)
            height = Dimension.value((parentHeight).dp)
        }

        // textOff: right side default
        constrain(textOff) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth * 0.5 ).dp)
            height = Dimension.value((parentHeight).dp)
        }

        // textOn: left side default
        constrain(textOn) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value((parentWidth * 0.5).dp)
            height = Dimension.value((parentHeight).dp)
        }
    }
}
