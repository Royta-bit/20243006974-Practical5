package com.example.chinesemunicipalities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Main screen of the application, where the name of a municipality is entered.
 */
public class MainActivity extends AppCompatActivity {

    private final TextProcessor textProcessor = new TextProcessor();

    private EditText municipalityInput;
    private TextView inputErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        applyInsetsToRootLayout();

        municipalityInput = findViewById(R.id.municipalityInput);
        inputErrorText = findViewById(R.id.inputErrorText);

        municipalityInput.setOnEditorActionListener((view, actionId, event) -> {
            boolean goAction = actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE;
            boolean enterKey = event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN;

            if (!goAction && !enterKey) {
                return false;
            }
            openRequestedMunicipality();
            return true;
        });
    }

    /**
     * Validates the typed name and either opens the second screen or leaves an error message
     * below the text field while this screen stays visible.
     */
    private void openRequestedMunicipality() {
        String typedText = municipalityInput.getText().toString();
        clearError();

        if (textProcessor.isBlank(typedText)) {
            showError(R.string.error_blank_input);
            return;
        }

        String municipalityName = textProcessor.processMunicipality(typedText);
        if (municipalityName == null) {
            showError(R.string.error_unknown_municipality);
            return;
        }

        hideKeyboard();
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(Municipality.EXTRA_MUNICIPALITY_NAME, municipalityName);
        startActivity(intent);
    }

    /** @param messageResource text of the message placed below the text field */
    private void showError(int messageResource) {
        inputErrorText.setText(messageResource);
        inputErrorText.setVisibility(View.VISIBLE);
    }

    /** Removes the error message from the layout. */
    private void clearError() {
        inputErrorText.setText(null);
        inputErrorText.setVisibility(View.GONE);
    }

    /** Closes the soft keyboard that was opened for the text field. */
    private void hideKeyboard() {
        InputMethodManager keyboard = getSystemService(InputMethodManager.class);
        if (keyboard != null) {
            keyboard.hideSoftInputFromWindow(municipalityInput.getWindowToken(), 0);
        }
    }

    /** Offsets the content by the system bar sizes reported for this window. */
    private void applyInsetsToRootLayout() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }
}
