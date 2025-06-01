package com.example.openlanephotogrid.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.example.openlanephotogrid.ui.theme.OPENLANEPhotoGridTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
            HorizontalPager(
                state = pagerState,
            ) { page ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable { onSelectedPhotoChanged(null) }
                        .animateContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = photos[page]?.fullImageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
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
