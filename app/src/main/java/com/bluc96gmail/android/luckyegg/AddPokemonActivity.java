package com.bluc96gmail.android.luckyegg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bluc on 7/19/16.
 */
public class AddPokemonActivity extends AppCompatActivity{

    public static final String EXTRA_RETURN_ADDITION = "com.bluc96@gmail.android.luckyegg.return_addition";


    Button mAddButton;
    EditText mNumPokemon;
    EditText mNumCandies;
    AutoCompleteTextView mPokemon;
    Addition mAddition;

    private List<String> mPokemonList;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AddPokemonActivity.class);
        return intent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.pokemon_add);

        mPokemonList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Pokemon_Array)));

        mAddition = new Addition();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mPokemonList);

        mPokemon = (AutoCompleteTextView) findViewById(R.id.pokemon_select_auto);
        mPokemon.setAdapter(adapter);
        //restrict autcompletetextview entries
        mPokemon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    if (mPokemonList.size() == 0 || mPokemonList.indexOf(mPokemon.getText().toString()) == -1) {
                        mPokemon.setError(getString(R.string.nonexistent_pokemon));
                    }
                }
            }
        });



        ///////////////////////////Edit Texts------------

        mNumCandies = (EditText) findViewById(R.id.num_candy_edit);
        mNumCandies.setFilters(new InputFilter[] { new InputFilterMinMax("1", "9999")});
        mNumCandies.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        mNumPokemon = (EditText) findViewById(R.id.num_pokemon_edit);
        mNumPokemon.setFilters(new InputFilter[] { new InputFilterMinMax("1", "250")});
        mNumPokemon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ///////////Buttons

        mAddButton = (Button) findViewById(R.id.add_activity_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If add is clicked and the text entered isn't a pokemon, appw ill crash due to valueof
                if (mPokemon.getText().toString().length() <= 0) {
                    mPokemon.setError(getString(R.string.no_pokemon_added));
                } else if (mPokemonList.indexOf(mPokemon.getText().toString()) == -1) {
                    mPokemon.setError(getString(R.string.nonexistent_pokemon));
                } else if (mNumPokemon.getText().toString().length() <= 0) {
                    mNumPokemon.setError(getString(R.string.no_num_pokemon_toast));
                } else if (mNumCandies.getText().toString().length() <= 0) {
                    mNumCandies.setError(getString(R.string.no_num_candy_toast));
                } else {
                    mAddition.setNumCandies(Integer.parseInt(mNumCandies.getText().toString()));
                    mAddition.setNumPokemon(Integer.parseInt(mNumPokemon.getText().toString()));
                    mAddition.setPokemon(mPokemon.getText().toString());
                    int index = mPokemonList.indexOf(mAddition.getPokemon());

                    if (index <= 2) {
                        mAddition.setCandiesToEvolve(12);
                    } else if (index >= 3 && index <= 17) {
                        mAddition.setCandiesToEvolve(25);
                    } else if (index >= 18 && index <= 55) {
                        mAddition.setCandiesToEvolve(50);
                    } else if (mAddition.getPokemon().equals(mPokemonList.get(mPokemonList.size() - 1))) {
                        mAddition.setCandiesToEvolve(400);
                    } else {
                        mAddition.setCandiesToEvolve(100);
                    }
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_RETURN_ADDITION, mAddition);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
