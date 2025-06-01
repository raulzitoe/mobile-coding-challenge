package com.example.openlanephotogrid.ui.gridscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.openlanephotogrid.ui.components.PhotoScrim
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.example.openlanephotogrid.ui.theme.OPENLANEPhotoGridTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun PhotoGridScreen(modifier: Modifier = Modifier) {
    val viewModel: PhotoGridViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PhotoGridContent(
        modifier = modifier,
        selectedPhotoId = state.selectedPhotoId,
        photosFlow = state.photosFlow,
        onSelectedPhotoChanged = viewModel::onSelectedPhotoChanged
    )
}

@Composable
private fun PhotoGridContent(
    photosFlow: Flow<PagingData<PhotoUIModel>>,
    selectedPhotoId: String?,
    onSelectedPhotoChanged: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val photos = photosFlow.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedPhotoId) {
        selectedPhotoId?.let { photoId ->
            scope.launch {
                val index = photos.itemSnapshotList.indexOfFirst { it?.id == photoId }

                if (index > 0) {
                    scrollState.animateScrollToItem(index - 1)
                }
            }
        }
    }

    PhotoScrim(
        photosFlow = photosFlow,
        selectedPhotoId = selectedPhotoId,
        onSelectedPhotoChanged = onSelectedPhotoChanged,
    )

    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        items(photos.itemCount) { index ->
            if (index % 2 == 0) {
                val photo1 = photos[index]
                val photo2 = if (index + 1 < photos.itemCount) photos[index + 1] else null

                if (photo1 != null && photo2 != null) {
                    PhotosRow(
                        photo1 = photo1,
                        photo2 = photo2,
                        onPhotoClicked = onSelectedPhotoChanged
                    )
                } else {
                    photo1?.let {
                        AsyncImage(
                            model = it.thumbnailUrl,
                            contentDescription = null,
                            modifier = Modifier.clickable { onSelectedPhotoChanged(it.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotosRow(
    photo1: PhotoUIModel,
    photo2: PhotoUIModel,
    onPhotoClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val newWidth = remember(photo1, photo2) { photo2.width.toFloat() / (photo2.height.toFloat() / photo1.height) }
    val sumWidth = remember(photo1, newWidth) { photo1.width + newWidth }

    Row {
        AsyncImage(
            model = photo1.thumbnailUrl,
            contentDescription = null,
            modifier = modifier
                .weight(photo1.width.toFloat() / sumWidth)
                .clickable { onPhotoClicked(photo1.id) },
            contentScale = ContentScale.FillWidth,
        )
        AsyncImage(
            model = photo2.thumbnailUrl,
            contentDescription = null,
            modifier = modifier
                .weight(newWidth / sumWidth)
                .clickable { onPhotoClicked(photo2.id) },
            contentScale = ContentScale.FillWidth
        )
    }
}

@PreviewLightDark
@Composable
private fun PhotoGridScreenPreview() {
    OPENLANEPhotoGridTheme {
        PhotoGridContent(
            photosFlow = MutableStateFlow(PagingData.empty()),
            selectedPhotoId = null,
            onSelectedPhotoChanged = {}
        )
    }
}
