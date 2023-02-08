package com.example.texttospeech;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;


import com.example.texttospeech.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    private final ActivityResultLauncher<String[]> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
        Log.d("----->", result.toString());
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Book book = new Book(getResources().getString(R.string.DFS));
        binding.setBook(book);
        textToSpeech = new TextToSpeech(getApplicationContext(), i -> {
            textToSpeech.setLanguage(Locale.US);
            textToSpeech.setVoice(new Voice("voice_1", Locale.US, Voice.QUALITY_HIGH, Voice.LATENCY_HIGH, false, null));
        });
        binding.speak.setOnClickListener(view -> textToSpeech.speak(book.getText(), TextToSpeech.QUEUE_FLUSH, null));

        binding.openDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

    }

    public void openFile(){
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        Intent.createChooser(intent, "Select files");
        activityResultLauncher.launch(new String[]{"*/*"});

    }
}