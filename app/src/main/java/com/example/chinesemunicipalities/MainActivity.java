package com.example.chinesemunicipalities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Main screen of the application.
 *
 * <p>Author: He Chengxiang (20243006974)</p>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        applyInsetsToRootLayout();
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
