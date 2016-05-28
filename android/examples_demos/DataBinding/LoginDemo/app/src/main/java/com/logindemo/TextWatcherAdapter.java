package com.logindemo;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;

/**
 * Created by RRR on 03-04-2016.
 */
public class TextWatcherAdapter implements TextWatcher {

    public final ObservableField<String> value =
            new ObservableField<>();
    private final ObservableField<String> field;

    private boolean isInEditMode = false;

    public TextWatcherAdapter(ObservableField<String> f) {
        this.field = f;

        field.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback(){
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (isInEditMode){
                    return;
                }
                value.set(field.get());
            }
        });
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //
    }

    @Override public void afterTextChanged(Editable s) {
        if (!Objects.equals(field.get(), s.toString())) {
            isInEditMode = true;
            field.set(s.toString());
            isInEditMode = false;
        }
    }

}
