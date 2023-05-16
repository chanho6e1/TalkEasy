package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
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
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatPartner
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatRoomBox
import com.ssafy.talkeasy.feature.chat.ui.tablet.OpenChatRoomButton
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.ui.tablet.FollowFrame

@Composable
fun AACRouteFrame(
    aacViewModel: AACViewModel = hiltViewModel(),
    followViewModel: FollowViewModel = hiltViewModel(),
) {
    val marginTop = 18.dp
    val marginRight = 36.dp
    val marginLeft = 20.dp
    val (isOpened, setIsOpened) = remember {
        mutableStateOf(false)
    }
    val (showFollowDialog, setShowFollowDialog) = remember {
        mutableStateOf(false)
    }
    val chatMode by aacViewModel.chatMode.collectAsState()
    val chatPartner by aacViewModel.chatPartner.collectAsState()

    SideEffect {
        followViewModel.requestMemberInfo()
        followViewModel.requestFollowList()
    }

    if (showFollowDialog) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            FollowFrame(
                onDismiss = { setShowFollowDialog(false) },
                setChatMode = { chatMode -> aacViewModel.setChatMode(chatMode) },
                setChatPartner = { chatPartner -> aacViewModel.setChatPartner(chatPartner) }
            )
        }
    }

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
            chatPartner = chatPartner,
            marginLeft = marginLeft
        ) {
            followViewModel.requestFollowList()
            setShowFollowDialog(true)
        }

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
            chatPartner = chatPartner,
            marginLeft = marginLeft
        )

        BrowseLocationBox(
            snackBarRef = snackBarRef,
            chatPartnerRef = chatPartnerRef,
            aacTopBarRef = aacTopBarRef,
            aacRef = aacRef
        )

        TopBarBox(aacTopBarRef = aacTopBarRef, marginTop = marginTop, marginRight = marginRight)

        AACBox(
            aacRef = aacRef,
            chatRoomRef = chatRoomRef,
            aacTopBarRef = aacTopBarRef,
            isOpened = isOpened,
            marginTop = marginTop,
            marginRight = marginRight
        )
    }
}

@Composable
fun ConstraintLayoutScope.ChatPartnerBox(
    chatPartnerRef: ConstrainedLayoutReference,
    aacTopBarRef: ConstrainedLayoutReference,
    chatMode: ChatMode,
    chatPartner: Follow?,
    marginLeft: Dp = 20.dp,
    onChangeButtonClickListener: () -> Unit,
) {
    Box(
        modifier = Modifier.constrainAs(chatPartnerRef) {
            start.linkTo(parent.start, margin = marginLeft)
            top.linkTo(aacTopBarRef.top)
            bottom.linkTo(aacTopBarRef.bottom)
        }
    ) {
        ChatPartner(chatMode = chatMode, chatPartner = chatPartner, onChangeButtonClickListener)
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
    marginTop: Dp = 18.dp,
    marginRight: Dp = 36.dp,
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
    marginTop: Dp = 18.dp,
    marginRight: Dp = 36.dp,
    aacViewModel: AACViewModel = viewModel(),
) {
    val smallCardsColumn = if (isOpened) 4 else 5
    val words by aacViewModel.selectedCard.collectAsState()
    val category by aacViewModel.category.collectAsState()
    val generatedSentence by aacViewModel.generatedSentence.collectAsState()

    LaunchedEffect(key1 = generatedSentence) {
        aacViewModel.initSelectedCard()
    }

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