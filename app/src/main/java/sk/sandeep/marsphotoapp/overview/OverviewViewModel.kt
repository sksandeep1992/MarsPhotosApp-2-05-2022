package sk.sandeep.marsphotoapp.overview


import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.sandeep.marsphotoapp.network.MarsApi
import sk.sandeep.marsphotoapp.network.MarsPhoto


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    /* // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
     // with new values
     private val _photos = MutableLiveData<List<MarsPhoto>>()

     // The external LiveData interface to the property is immutable, so only this class can modify
     val photos: LiveData<List<MarsPhoto>> = _photos*/

    private val _photos = MutableLiveData<MarsPhoto>()
    val photos: LiveData<MarsPhoto> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {

        viewModelScope.launch {
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()[0]
                _status.value = "   First Mars image URL : ${_photos.value!!.imgSrcUrl}"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
