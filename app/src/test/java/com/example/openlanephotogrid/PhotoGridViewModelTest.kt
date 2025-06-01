package com.example.openlanephotogrid

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.openlanephotogrid.data.PhotoRepository
import com.example.openlanephotogrid.data.model.PhotoLinks
import com.example.openlanephotogrid.data.model.PhotoModel
import com.example.openlanephotogrid.data.model.ProfileImage
import com.example.openlanephotogrid.data.model.Urls
import com.example.openlanephotogrid.data.model.User
import com.example.openlanephotogrid.data.model.UserLinks
import com.example.openlanephotogrid.ui.gridscreen.PhotoGridViewModel
import com.example.openlanephotogrid.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoGridViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PhotoGridViewModel

    @MockK
    private lateinit var repository: PhotoRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun createViewModel() {
        viewModel = PhotoGridViewModel(repository).apply { setNotCached() }
    }

    @Test
    fun `when api returns data, data is empty, then state is updated with empty list`() = runTest {
        every { repository.getPhotosFlow() } returns flowOf(PagingData.from(listOf()))
        createViewModel()
        advanceUntilIdle()

        val snapShotList = viewModel.state.value.photosFlow.asSnapshot()
        assert(snapShotList.isEmpty())
    }

    @Test
    fun `when api returns data, then state is updated with correct domain model`() = runTest {
        every { repository.getPhotosFlow() } returns flowOf(PagingData.from(listOf(mockPhoto1)))
        createViewModel()
        advanceUntilIdle()

        val snapShotList = viewModel.state.value.photosFlow.asSnapshot()
        assert(snapShotList.isNotEmpty())
        assert(snapShotList.contains(mockPhoto1.toPhotoUIModel()))
    }

    private val mockPhoto1 = PhotoModel(
        id = "1",
        createdAt = "2016-05-03T11:00:28-04:00",
        updatedAt = "2023-07-10T11:00:01-05:00",
        width = 5245,
        height = 3497,
        color = "#60544D",
        blurHash = "LoC%a7IoIVxZ_NM|M{s:%hRjWAo0",
        likes = 125,
        likedByUser = false,
        description = "A detailed description of the photo.",
        user = User(
            id = "user123",
            username = "john_doe",
            name = "John Doe",
            portfolioUrl = "",
            bio = "",
            location = "",
            totalLikes = 3,
            totalPhotos = 100,
            totalCollections = 0,
            instagramUsername = "",
            twitterUsername = "",
            profileImage = ProfileImage(
                small = "",
                medium = "",
                large = ""
            ),
            links = UserLinks(
                self = "",
                html = "",
                photos = "",
                likes = "",
                portfolio = ""
            ),
        ),
        currentUserCollections = emptyList(),
        urls = Urls(
            raw = "raw",
            full = "full",
            regular = "regular",
            small = "small",
            thumb = "thumb"
        ),
        links = PhotoLinks(
            self = "",
            html = "",
            download = "",
            downloadLocation = ""
        )
    )
}
