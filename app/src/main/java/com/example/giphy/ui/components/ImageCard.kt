package com.example.giphy.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.giphy.util.Dimensions

@Composable
fun Card(
    title: String,
    imageUrl: String?
) {
    val borderShape = RoundedCornerShape(Dimensions.cornerRadiusVeryLarge)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .border(
                border = BorderStroke(
                    width = Dimensions.strokeWidth4px,
                    color = Color.Black
                ),
                shape = borderShape
            )
            .shadow(elevation = 5.dp, shape = borderShape),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = borderShape
                )
        ) {
            val (imageBoxHolder, textHolder) = createRefs()

            GifImage(
                modifier = Modifier
                    .constrainAs(imageBoxHolder) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(textHolder.top, 12.dp)
                    },
                imageUrl = imageUrl
            )

            TextMediumRegular(
                modifier = Modifier
                    .constrainAs(textHolder) {
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                text = title,
                maxLines = 1,
                color = Color.Black
            )
        }
    }
}


@Preview("Card", showBackground = true)
@Preview("Card (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CardPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        VerticalSpacer(height = 24.dp)
        Card(
            title = "Example",
            imageUrl = "https://media.istockphoto.com/id/1322277517/photo/wild-grass-in-the-mountains-at-sunset.jpg?s=612x612&w=0&k=20&c=6mItwwFFGqKNKEAzv0mv6TaxhLN3zSE43bWmFN--J5w="
        )
        VerticalSpacer(height = 24.dp)

    }
}