package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> editString = new MutableLiveData<>();
    private MutableLiveData<Boolean> drinkCoffeeLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getDrinkCoffeeLiveData() {
        return drinkCoffeeLiveData;
    }
}