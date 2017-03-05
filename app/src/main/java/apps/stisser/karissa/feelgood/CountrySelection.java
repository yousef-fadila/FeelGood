package apps.stisser.karissa.feelgood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by User on 3/4/2017.
 */
public class CountrySelection extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    public static String selectedCountry = "Lebanon";
    public static Spinner spinner;
    String[] countryOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries);
        countryOptions = getResources().getStringArray(R.array.country_arrays);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        selectedCountry = countryOptions[pos];
        
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    public static String getSelectedCountry(){
        return selectedCountry;
    }
}