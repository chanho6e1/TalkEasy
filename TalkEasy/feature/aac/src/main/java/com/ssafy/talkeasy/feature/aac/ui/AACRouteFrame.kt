package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.SampleData
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatContent
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatPartner
import com.ssafy.talkeasy.feature.chat.ui.tablet.OpenChatRoomButton
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode

val marginTop = 18.dp
val marginRight = 36.dp
val marginLeft = 20.dp

@Composable
@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
fun AACRouteFrame(aacViewModel: AACViewModel = hiltViewModel()) {
    val (isOpened, setIsOpened) = remember {
        mutableStateOf(false)
    }
    val chatMode by aacViewModel.chatMode.collectAsState()
    val chatPartner by aacViewModel.chatPartner.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            chatPartnerRef,
            openChatRoomButtonRef,
            chatRoomRef,
            snackBarRef,
            aacTopBarRef,
            aacRef,
        ) = createRefs()

        ChatPartnerBox(
            chatPartnerRef = chatPartnerRef,
            aacTopBarRef = aacTopBarRef,
            chatMode = chatMode,
            chatPartner = chatPartner
        )

        OpenChatRoomButtonBox(
            openChatRoomButtonRef = openChatRoomButtonRef,
            chatRoomRef = chatRoomRef
        ) {
            setIsOpened(!isOpened)
        }

        ChatRoomBox(
            chatRoomRef = chatRoomRef,
            aacRef = aacRef,
            chatPartnerRef = chatPartnerRef,
            isOpened = isOpened,
            chatMode = chatMode,
            chatPartner = chatPartner
        )

        BrowseLocationBox(
            snackBarRef = snackBarRef,
            chatPartnerRef = chatPartnerRef,
            aacTopBarRef = aacTopBarRef,
            aacRef = aacRef
        )

        TopBarBox(aacTopBarRef = aacTopBarRef)

        AACBox(
            aacRef = aacRef,
            chatRoomRef = chatRoomRef,
            aacTopBarRef = aacTopBarRef,
            isOpened = isOpened
        )
    }
}

@Composable
fun ConstraintLayoutScope.ChatPartnerBox(
    chatPartnerRef: ConstrainedLayoutReference,
    aacTopBarRef: ConstrainedLayoutReference,
    chatMode: ChatMode,
    chatPartner: Follow?,
) {
    Box(
        modifier = Modifier.constrainAs(chatPartnerRef) {
            start.linkTo(parent.start, margin = marginLeft)
            top.linkTo(aacTopBarRef.top)
            bottom.linkTo(aacTopBarRef.bottom)
        }
    ) {
        ChatPartner(chatMode = chatMode, chatPartner = chatPartner)
    }
}

@Composable
fun ConstraintLayoutScope.OpenChatRoomButtonBox(
    openChatRoomButtonRef: ConstrainedLayoutReference,
    chatRoomRef: ConstrainedLayoutReference,
    onClickAction: () -> Unit,
) {
    Box(
        modifier = Modifier
            .constrainAs(openChatRoomButtonRef) {
                start.linkTo(chatRoomRef.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .clickable { onClickAction() },
        contentAlignment = Alignment.Center
    ) {
        OpenChatRoomButton()
    }
}

@Composable
fun ConstraintLayoutScope.ChatRoomBox(
    chatRoomRef: ConstrainedLayoutReference,
    aacRef: ConstrainedLayoutReference,
    chatPartnerRef: ConstrainedLayoutReference,
    isOpened: Boolean,
    chatMode: ChatMode,
    chatPartner: Follow?,
    aacViewModel: AACViewModel = viewModel(),
) {
    val chats by aacViewModel.chats.collectAsState()

    if (isOpened) {
        Box(
            modifier = Modifier
                .constrainAs(chatRoomRef) {
                    top.linkTo(aacRef.top)
                    bottom.linkTo(parent.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = marginLeft)
                    end.linkTo(chatPartnerRef.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(color = green_white, shape = shapes.extraSmall)
        ) {
            if (chatPartner == null || chatMode == ChatMode.TTS) {
                NoContentLogoMessage(
                    message = stringResource(id = R.string.content_no_content_tts),
                    textStyle = typography.titleMedium,
                    width = 156,
                    height = 72,
                    betweenValue = 20
                )
            } else if (chats.isNullOrEmpty()) {
                NoContentLogoMessage(
                    message = stringResource(id = R.string.content_no_chat),
                    textStyle = typography.titleMedium,
                    width = 156,
                    height = 72,
                    betweenValue = 20
                )
            } else {
                chats?.let { ChatContent(chatPartner = chatPartner, chats = it) }
            }
        }
    } else {
        Box(
            modifier = Modifier.constrainAs(chatRoomRef) {
                top.linkTo(chatPartnerRef.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )
    }
}

@Composable
fun ConstraintLayoutScope.BrowseLocationBox(
    snackBarRef: ConstrainedLayoutReference,
    chatPartnerRef: ConstrainedLayoutReference,
    aacTopBarRef: ConstrainedLayoutReference,
    aacRef: ConstrainedLayoutReference,
    aacViewModel: AACViewModel = viewModel(),
) {
    Box(
        modifier = Modifier.constrainAs(snackBarRef) {
            start.linkTo(chatPartnerRef.end, margin = 16.dp)
            end.linkTo(aacTopBarRef.start, margin = 16.dp)
            top.linkTo(parent.top)
            bottom.linkTo(aacRef.top)
            width = Dimension.fillToConstraints
        }
    ) {
        BrowseLocation(aacViewModel.whoRequest)
    }
}

@Composable
fun ConstraintLayoutScope.TopBarBox(
    aacTopBarRef: ConstrainedLayoutReference,
    aacViewModel: AACViewModel = viewModel(),
) {
    Box(
        modifier = Modifier.constrainAs(aacTopBarRef) {
            top.linkTo(parent.top, margin = marginTop)
            end.linkTo(parent.end, margin = marginRight)
            height = Dimension.wrapContent
        }
    ) {
        AACTopBar(onRight = aacViewModel.getOnRight())
    }
}

@Composable
fun ConstraintLayoutScope.AACBox(
    aacRef: ConstrainedLayoutReference,
    chatRoomRef: ConstrainedLayoutReference,
    aacTopBarRef: ConstrainedLayoutReference,
    isOpened: Boolean,
    aacViewModel: AACViewModel = viewModel(),
) {
    val smallCardsColumn = if (isOpened) 4 else 5
    val words by aacViewModel.selectedCard.collectAsState()
    val category by aacViewModel.category.collectAsState()

    Column(
        modifier = Modifier.constrainAs(aacRef) {
            start.linkTo(chatRoomRef.end, margin = 24.dp)
            end.linkTo(parent.end, margin = marginRight)
            top.linkTo(aacTopBarRef.bottom, margin = marginTop)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AACChatBox(words = words)

        AACFixedCards()

        if (category == "") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AACCategory(isOpened = isOpened)
            }
        } else {
            AACCardBox(smallCardsColumn = smallCardsColumn, category = category)
        }
    }
}

@Composable
fun AACCardBox(smallCardsColumn: Int, category: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(start = 30.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            AACSmallCards(words = SampleData.string25, column = smallCardsColumn)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                AACPaging()
            }

            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                BackToCategory(category)
            }
        }
    }
}