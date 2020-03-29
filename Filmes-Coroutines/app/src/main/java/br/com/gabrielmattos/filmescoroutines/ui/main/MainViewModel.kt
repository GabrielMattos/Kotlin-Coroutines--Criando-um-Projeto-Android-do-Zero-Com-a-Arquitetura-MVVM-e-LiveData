package br.com.gabrielmattos.filmescoroutines.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class MainViewModel(private val repositoty: MainRepository /*Forma correta*/) : ViewModel()
{
    //val repository = MainRepository() //forma errada

    val filmesLiveData = MutableLiveData<List<Filme>>()

    fun getFilmes()
    {
        repositoty.getFilmes { filmes ->
            filmesLiveData.postValue(filmes)
        }
    }

    fun getFilmesCoroutines()
    {
        CoroutineScope(Dispatchers.Main).launch {
            val filmes = withContext(Dispatchers.Default)
            {
                repositoty.getFilmesCoroutines()
            }

            filmesLiveData.value = filmes
        }
    }


    class MainViewModelFactory(private val repositoty: MainRepository) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
        {
            return MainViewModel(repositoty) as T
        }

    }
}
