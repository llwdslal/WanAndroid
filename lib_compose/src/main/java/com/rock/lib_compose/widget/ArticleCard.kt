package com.rock.lib_compose.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    title:String?,
    author:String?,
    date:String?,
    collected:Boolean = false,
    showPlaceHolder:Boolean = false,
    onCollectedClick:(Boolean)->Unit,
    onClick:()->Unit,
){
    val collectedState = remember(collected) { mutableStateOf(collected) }

    Card(
        modifier = Modifier.then(modifier).clickable(onClick = onClick),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = Color.White
    ) {

        Row{
            Column(modifier = Modifier
                .weight(1f)
                .padding(8.dp)) {
                Text(
                    modifier = Modifier.defaultPlaceHolder(showPlaceHolder).defaultMinSize(minWidth = 150.dp),
                    text = title ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        modifier = Modifier.defaultPlaceHolder(showPlaceHolder).defaultMinSize(minWidth = 20.dp),
                        text = author ?: "",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    if (author?.isEmpty() == false) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Text(
                        modifier = Modifier.defaultPlaceHolder(showPlaceHolder).defaultMinSize(minWidth = 30.dp),
                        text = date ?: "",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            IconToggleButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                checked = collectedState.value,
                onCheckedChange = { onCollectedClick(it) }) {
                val tint by animateColorAsState(targetValue = if (collectedState.value) Color.Magenta else Color.LightGray)
                Icon(Icons.Filled.Favorite, tint = tint, contentDescription = "")
            }
        }


    }
}

