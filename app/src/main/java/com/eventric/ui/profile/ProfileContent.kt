package com.eventric.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eventric.R
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.vo.User


@Composable
fun ProfileContent(
    navController: NavController,
    id: String,
    user: User,
    self: Boolean,
    followed: Boolean,
    onFollowClick: () -> Unit,
    onEditPress: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(50.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = CircleShape,
                        clip = true,
                        spotColor = Color.Transparent
                    )
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = CircleShape
                    )
                    .border(
                        width = 0.8.dp,
                        color = Color.Transparent,
                        shape = CircleShape
                    ),
                enabled = false,
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile",
                    tint = MaterialTheme.colors.onBackground,
                )
            }
            Text(
                text = user.name+" "+user.surname,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Row(modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(10.dp)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = MaterialTheme.colors.onPrimary,
                            bounded = false
                        )
                    ){ /*TODO onclick*/ }
                ) {
                    Text(
                        text = ""+user.followingUsers.size,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = stringResource(id = R.string.followed),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = MaterialTheme.colors.onPrimary,
                            bounded = false
                        )
                    ){ /*TODO onclick*/ }
                ) {
                    Text(
                        text = "800",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = stringResource(id = R.string.followers),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (self){
                CustomButtonSecondary(
                    text = stringResource(id = R.string.edit_profile),
                    modifier = Modifier
                        .width(200.dp),
                    iconId = R.drawable.ic_edit,
                    onClick = onEditPress,
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            else
            {
                CustomButtonSecondary(
                    text = if(followed) stringResource(id = R.string.unfollow_label) else stringResource(id = R.string.follow_label),
                    modifier = Modifier
                        .width(200.dp),
                    iconId = if(followed) R.drawable.ic_unfollow else R.drawable.ic_follow,
                    onClick = onFollowClick
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 30.dp),
                text = stringResource(id = R.string.bio),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 30.dp),
                text = user.bio ?: "nessuna bio",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}
