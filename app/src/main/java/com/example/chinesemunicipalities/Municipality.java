package com.example.chinesemunicipalities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Second screen of the application, which presents the municipality received from the main
 * screen.
 *
 * <p>Author: He Chengxiang (20243006974)</p>
 */
public class Municipality extends AppCompatActivity {

    /** Key of the Intent extra that carries the normalised municipality name. */
    public static final String EXTRA_MUNICIPALITY_NAME =
            "com.example.chinesemunicipalities.extra.MUNICIPALITY_NAME";

    /**
     * Resources that belong to a municipality. The constants are listed in the order used by
     * TextProcessor, so the position of a name in that list is also its position here.
     */
    private enum Style {
        BEIJING(R.color.municipality_beijing, R.drawable.beijing),
        SHANGHAI(R.color.municipality_shanghai, R.drawable.shanghai),
        CHONGQING(R.color.municipality_chongqing, R.drawable.chongqing),
        TIANJIN(R.color.municipality_tianjin, R.drawable.tianjin);

        private final int accentColour;
        private final int photo;

        Style(int accentColour, int photo) {
            this.accentColour = accentColour;
            this.photo = photo;
        }
    }

    private final TextProcessor textProcessor = new TextProcessor();

    private String municipalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        applyInsetsToRootLayout();

        String receivedName = getIntent().getStringExtra(EXTRA_MUNICIPALITY_NAME);
        municipalityName = textProcessor.processMunicipality(receivedName);
        if (municipalityName == null) {
            Toast.makeText(this, R.string.error_missing_municipality, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setTitle(municipalityName);
        showMunicipality(municipalityName);
    }

    /** Applies the name, colours, photograph and summary of the given municipality. */
    private void showMunicipality(String name) {
        Style[] styles = Style.values();
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        int position = textProcessor.positionOf(name);

        if (position < 0 || position >= styles.length || position >= summaries.length) {
            Toast.makeText(this, R.string.error_missing_municipality, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Style style = styles[position];
        int accentColour = ContextCompat.getColor(this, style.accentColour);
        int lightTextColour = ContextCompat.getColor(this, R.color.color_text_light);

        TextView nameView = findViewById(R.id.municipalityName);
        ImageView photoView = findViewById(R.id.municipalityPhoto);
        TextView summaryView = findViewById(R.id.summaryText);
        Button visitSiteButton = findViewById(R.id.visitSiteButton);

        nameView.setText(name);
        nameView.setBackgroundColor(accentColour);
        nameView.setTextColor(lightTextColour);

        photoView.setImageResource(style.photo);
        photoView.setContentDescription(
                getString(R.string.description_municipality_photo_named, name));

        summaryView.setText(summaries[position]);
        summaryView.setTextColor(accentColour);

        visitSiteButton.setBackgroundColor(accentColour);
        visitSiteButton.setBackgroundTintList(ColorStateList.valueOf(accentColour));
        visitSiteButton.setTextColor(lightTextColour);
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
