package com.example.openlanephotogrid.ui.components

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.example.openlanephotogrid.ui.theme.OPENLANEPhotoGridTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.time.Duration.Companion.seconds

@Composable
fun PhotoScrim(
    photosFlow: Flow<PagingData<PhotoUIModel>>,
    selectedPhotoId: String?,
    onSelectedPhotoChanged: (id: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val photos = photosFlow.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(
        pageCount = { photos.itemCount },
        initialPage = photos.itemSnapshotList.indexOfFirst { it?.id == selectedPhotoId }
            .coerceAtLeast(0)
    )

    LaunchedEffect(selectedPhotoId) {
        pagerState.scrollToPage(photos.itemSnapshotList.indexOfFirst { it?.id == selectedPhotoId }
            .coerceAtLeast(0))
    }

    LaunchedEffect(pagerState.settledPage) {
        if (photos.itemCount > 0 && pagerState.settledPage > 0) {
            onSelectedPhotoChanged(photos[pagerState.settledPage]?.id)
        }
    }

    selectedPhotoId?.let {
        Dialog(
            onDismissRequest = { onSelectedPhotoChanged(null) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var isDetailsVisible by remember { mutableStateOf(true) }

            LaunchedEffect(pagerState.currentPage) {
                isDetailsVisible = true
            }

            LaunchedEffect(isDetailsVisible, pagerState.currentPage) {
                if (isDetailsVisible) {
                    delay(2.seconds)
                    isDetailsVisible = false
                }
            }

            HorizontalPager(
                state = pagerState,
            ) { page ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable { onSelectedPhotoChanged(null) },
                    contentAlignment = Alignment.Center
                ) {
                    var width by remember { mutableIntStateOf(0) }

                    Box {
                        AsyncImage(
                            model = photos[page]?.fullImageUrl,
                            contentDescription = null,
                            contentScale = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
                                ContentScale.FillHeight
                            } else {
                                ContentScale.FillWidth
                            },
                            modifier = Modifier
                                .then(
                                    if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
                                        Modifier.fillMaxHeight()
                                    } else {
                                        Modifier.fillMaxWidth()
                                    }
                                )
                                .onGloballyPositioned { coordinates ->
                                    width = coordinates.size.width
                                }
                                .clickable { isDetailsVisible = true }
                                .animateContentSize()
                        )

                        AnimatedVisibility(
                            visible = isDetailsVisible && pagerState.currentPage == page,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .width(with(LocalDensity.current) { width.toDp() }),
                            enter = EnterTransition.None,
                            exit = fadeOut(animationSpec = tween(800))
                        ) {
                            PhotoDetails(
                                userName = photos[page]?.user.orEmpty(),
                                description = photos[page]?.description.orEmpty(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoDetails(
    userName: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Color.Black.copy(alpha = 0.7f))
    ) {
        Text(
            text = userName,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )

        if (description.isNotBlank()) {
            Text(
                text = description,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                lineHeight = 10.sp,
                color = Color.White
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PhotoScrimPreview() {
    OPENLANEPhotoGridTheme {
        PhotoScrim(
            photosFlow = flowOf(),
            selectedPhotoId = null,
            onSelectedPhotoChanged = {}
        )
    }
}
