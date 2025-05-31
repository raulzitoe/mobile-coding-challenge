package com.example.openlanephotogrid.ui.gridscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.openlanephotogrid.ui.model.PhotoUIModel
import com.example.openlanephotogrid.ui.theme.OPENLANEPhotoGridTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PhotoGridScreen(modifier: Modifier = Modifier) {
    val viewModel: PhotoGridViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PhotoGridContent(
        modifier = modifier,
        photosFlow = state.photosFlow
    )
}

@Composable
private fun PhotoGridContent(
    modifier: Modifier = Modifier,
    photosFlow: Flow<PagingData<List<PhotoUIModel>>>
) {
    val rowPhotos = photosFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
    ) {
        items(rowPhotos.itemCount) { index ->
            rowPhotos[index]?.let { rowItem ->
                if (rowItem.size == 2) {
                    PhotosRow(
                        photo1 = rowItem.first(),
                        photo2 = rowItem.last()
                    )
                } else {
                    AsyncImage(
                        model = rowItem.first().thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotosRow(
    photo1: PhotoUIModel,
    photo2: PhotoUIModel,
    modifier: Modifier = Modifier
) {
    val newWidth = photo2.width.toFloat() / (photo2.height.toFloat() / photo1.height)
    val sumWidth = photo1.width + newWidth

    Row {
        AsyncImage(
            model = photo1.thumbnailUrl,
            contentDescription = null,
            modifier = modifier.weight(photo1.width.toFloat() / sumWidth),
            contentScale = ContentScale.FillWidth
        )
        AsyncImage(
            model = photo2.thumbnailUrl,
            contentDescription = null,
            modifier = modifier.weight(newWidth / sumWidth),
            contentScale = ContentScale.FillWidth
        )
    }
}

@PreviewLightDark
@Composable
private fun PhotoGridScreenPreview() {
    OPENLANEPhotoGridTheme {
        PhotoGridContent(
            photosFlow = MutableStateFlow(PagingData.empty())
        )
    }
}
