package com.ssafy.talkeasy.feature.aac.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ssafy.talkeasy.feature.chat.ChatViewModel
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatPartner
import com.ssafy.talkeasy.feature.chat.ui.tablet.ChatRoomBox
import com.ssafy.talkeasy.feature.chat.ui.tablet.OpenChatRoomButton
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.component.LoadingAnimationIterate
import com.ssafy.talkeasy.feature.common.ui.theme.dimens
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.common.util.SendMode
import com.ssafy.talkeasy.feature.common.util.toast
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.ui.tablet.FollowFrame
import com.ssafy.talkeasy.feature.location.ui.tablet.SOSFrame
import com.ssafy.talkeasy.feature.location.ui.tablet.SOSRequestFrame

@Composable
fun AACRouteFrame(
    aacViewModel: AACViewModel = hiltViewModel(),
    followViewModel: FollowViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val marginTop = 18.dp
    val marginRight = 36.dp
    val marginLeft = 20.dp
    val (showChangeChatPartnerDialog, setShowChangeChatPartnerDialog) = remember {
        mutableStateOf(false)
    }
    val (showFollowDialog, setShowFollowDialog) = remember {
        mutableStateOf(false)
    }
    val (showNotificationDialog, setShowNotificationDialog) = remember {
        mutableStateOf(false)
    }
    val (showSOSRequestDialog, setShowSOSRequestDialog) = remember {
        mutableStateOf(false)
    }
    val (showSOSDialog, setShowSOSDialog) = remember {
        mutableStateOf(false)
    }
    val (showCustomWordDialog, setShowCustomWordDialog) = remember {
        mutableStateOf(false)
    }
    val sendMode = remember {
        mutableStateOf(SendMode.NONE)
    }
    val chatMode by aacViewModel.chatMode.collectAsState()
    val chatPartner by aacViewModel.chatPartner.collectAsState()
    val generatedSentence by aacViewModel.generatedSentence.collectAsState()
    val selectedCard by aacViewModel.selectedCard.collectAsState()
    val memberInfo by followViewModel.memberInfo.collectAsState()
    val isSendSucceed by chatViewModel.isSendSucceed.collectAsState()

    SideEffect {
        followViewModel.requestMemberInfo()
        followViewModel.requestFollowList()
    }

    LaunchedEffect(key1 = generatedSentence, key2 = sendMode.value) {
        if (selectedCard.isNotEmpty() && generatedSentence != "") {
            when (sendMode.value) {
                SendMode.NONE -> {}
                SendMode.PREVIEW -> {
                    context.toast(generatedSentence)
                    sendMode.value = SendMode.NONE
                }

                SendMode.SEND -> {
                    when (chatMode) {
                        ChatMode.TTS -> {
                            aacViewModel.getTTSMp3Url(generatedSentence)
                        }

                        ChatMode.CHAT -> {
                            chatViewModel.sendChatMessage(
                                toUserId = chatPartner!!.userId,
                                roomId = chatPartner!!.roomId,
                                msg = generatedSentence,
                                fromUserId = memberInfo!!.userId,
                                type = 0
                            )
                            sendMode.value = SendMode.NONE
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = isSendSucceed) {
        aacViewModel.initSelectedCard()
        aacViewModel.initGeneratedSentence()
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

    if (showSOSRequestDialog) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            SOSRequestFrame(
                closeSOSRequestDialog = { setShowSOSRequestDialog(false) },
                showSOSDialog = { setShowSOSDialog(true) }
            )
        }
    }

    if (showSOSDialog) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            SOSFrame(quitSOSListener = { setShowSOSDialog(false) })
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
            setShowChangeChatPartnerDialog(!showChangeChatPartnerDialog)
        }

        ChatRoomBox(
            chatRoomRef = chatRoomRef,
            aacRef = aacRef,
            chatPartnerRef = chatPartnerRef,
            isOpened = showChangeChatPartnerDialog,
            chatMode = chatMode,
            chatPartner = chatPartner,
            marginLeft = marginLeft,
            sendMode = sendMode.value,
            memberInfo = memberInfo
        )

        // BrowseLocationBox(
        //     snackBarRef = snackBarRef,
        //     chatPartnerRef = chatPartnerRef,
        //     aacTopBarRef = aacTopBarRef,
        //     aacRef = aacRef
        // )

        TopBarBox(
            aacTopBarRef = aacTopBarRef,
            marginTop = marginTop,
            marginRight = marginRight,
            showNotificationDialog = { setShowNotificationDialog(true) },
            showSOSRequestDialog = { setShowSOSRequestDialog(true) }
        )

        AACBox(
            aacRef = aacRef,
            chatRoomRef = chatRoomRef,
            aacTopBarRef = aacTopBarRef,
            marginTop = marginTop,
            marginRight = marginRight,
            isOpened = showChangeChatPartnerDialog,
            sendMode = sendMode,
            showCustomWordDialog = { setShowCustomWordDialog(true) }
        )
    }

    if (showNotificationDialog) {
        Box(modifier = Modifier.fillMaxSize()) {
            NotificationFrame(notifications = listOf()) {
                setShowNotificationDialog(false)
            }
        }
    }

    if (showCustomWordDialog) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(dimens)
                    .clickable { setShowCustomWordDialog(false) }
            )

            Box(modifier = Modifier.padding(start = 80.dp, bottom = 45.dp)) {
                AACCustomWordDialog()
            }
        }
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
    showSOSRequestDialog: () -> Unit,
    showNotificationDialog: () -> Unit,
    aacViewModel: AACViewModel = viewModel(),
) {
    Box(
        modifier = Modifier.constrainAs(aacTopBarRef) {
            top.linkTo(parent.top, margin = marginTop)
            end.linkTo(parent.end, margin = marginRight)
            height = Dimension.wrapContent
        }
    ) {
        AACTopBar(
            onRight = aacViewModel.getOnRight(),
            showNotificationDialog = showNotificationDialog,
            showSOSRequestDialog = showSOSRequestDialog
        )
    }
}

@Composable
fun ConstraintLayoutScope.AACBox(
    aacRef: ConstrainedLayoutReference,
    chatRoomRef: ConstrainedLayoutReference,
    aacTopBarRef: ConstrainedLayoutReference,
    marginTop: Dp = 18.dp,
    marginRight: Dp = 36.dp,
    isOpened: Boolean,
    sendMode: MutableState<SendMode>,
    showCustomWordDialog: () -> Unit,
    aacViewModel: AACViewModel = viewModel(),
) {
    val words by aacViewModel.selectedCard.collectAsState()
    val category by aacViewModel.category.collectAsState()
    val aacWordList by aacViewModel.aacWordList.collectAsState()
    val fixedList by aacViewModel.aacFixedList.collectAsState()
    val ttsMp3Url by aacViewModel.ttsMp3Url.collectAsState()
    val context = LocalContext.current
    val (cardClickEnable, setCardClickEnable) = remember {
        mutableStateOf(true)
    }

    SideEffect {
        if (fixedList.isEmpty()) {
            aacViewModel.getWordList(categoryId = 1)
        }
    }

    LaunchedEffect(key1 = ttsMp3Url) {
        if (ttsMp3Url != "" && sendMode.value == SendMode.SEND) {
            ttsPlay(context, ttsMp3Url)
            sendMode.value = SendMode.NONE
            aacViewModel.initTTSMp3Url()
            aacViewModel.initSelectedCard()
            aacViewModel.initGeneratedSentence()
        }
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
        AACChatBox(
            words = words,
            cardClickEnable = cardClickEnable,
            sendMode = sendMode,
            setCardClickEnable = { flag: Boolean -> setCardClickEnable(flag) }
        )

        if (category != "" && aacWordList == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimationIterate(
                    loadingAnimationId = R.raw.anim_loading_green,
                    size = 200.dp
                )
            }
        } else {
            AACFixedCards(cardClickEnable = cardClickEnable)

            if (category == "") {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    AACCategory(isOpened = isOpened)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp, start = 40.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    // AACCustomWordDialogButton(showCustomWordDialog = showCustomWordDialog)
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (category != "사용자 지정") {
                        Spacer(modifier = Modifier.padding(top = 8.dp))

                        AACRelatedCards(cardClickEnable = cardClickEnable)
                    }

                    AACCardBox(
                        category = category,
                        cardClickEnable = cardClickEnable,
                        showCustomWordDialog = showCustomWordDialog
                    )
                }
            }
        }
    }
}

@Composable
fun AACCardBox(
    category: String,
    cardClickEnable: Boolean,
    showCustomWordDialog: () -> Unit,
    aacViewModel: AACViewModel = viewModel(),
) {
    val aacWordList by aacViewModel.aacWordList.collectAsState()
    val wordCountPerPage: Int
    val marginTop: Dp
    val marginBottom: Dp
    if (category != "사용자 지정") {
        wordCountPerPage = 16
        marginTop = 14.dp
        marginBottom = 10.dp
    } else {
        wordCountPerPage = 20
        marginTop = 20.dp
        marginBottom = 16.dp
    }
    val (page, setPage) = remember {
        mutableStateOf(0)
    }
    val aacWordListSize = aacWordList?.aacList?.size ?: 0
    val totalPageCount =
        aacWordListSize / wordCountPerPage + if (aacWordListSize % wordCountPerPage == 0) 0 else 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = marginTop),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(start = 30.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            aacWordList?.let {
                AACSmallCards(
                    page = page,
                    wordCountPerPage = wordCountPerPage,
                    wordList = it.aacList,
                    cardClickEnable = cardClickEnable
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = marginBottom)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 40.dp)
            ) {
                // AACCustomWordDialogButton(showCustomWordDialog = showCustomWordDialog)
            }

            Box(modifier = Modifier.align(Alignment.Center)) {
                AACPaging(page = page, totalPage = totalPageCount, setPage = setPage)
            }

            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                BackToCategory(category)
            }
        }
    }
}