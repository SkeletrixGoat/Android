package com.example.losowaniekostek;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText liczbaKostek;
    private GridLayout siatkaKostek;
    private TextView wynikiKostek;
    private final List<Integer> wyniki = new ArrayList<>();
    private final Random generator = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ekran), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });
        liczbaKostek = findViewById(R.id.liczbaKostek);
        siatkaKostek = findViewById(R.id.siatkaKostek);
        wynikiKostek = findViewById(R.id.wynikiKostek);
            Button losowanie = findViewById(R.id.przyciskLosuj);
            Button rosnaco = findViewById(R.id.sortowanieRosnace);
            Button malejaco = findViewById(R.id.sortowanieMalejace);
            losowanie.setOnClickListener(v -> losuj());
            rosnaco.setOnClickListener(v -> {
                if (wyniki.size() == 0) {
                    Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
                } else {
                    sortuj(true);
                    odswiez();
                }
        });
        malejaco.setOnClickListener(v -> {
            if (wyniki.size() == 0) {
                Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
            } else {
                sortuj(false);
                odswiez();
            }
        });
    }
        private void losuj() {
            int ilosc;
            try {
                ilosc = Integer.parseInt(liczbaKostek.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Podaj poprawną liczbę kostek", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ilosc < 1 || ilosc > 100) {
                Toast.makeText(this, "Podaj liczbę od 1 do 100", Toast.LENGTH_SHORT).show();
                return;
            }
            wyniki.clear();
            for (int i = 0; i < ilosc; i++) {
                int wynik = generator.nextInt(6) + 1;
            wyniki.add(wynik);
        }
        odswiez();
    }
    private void sortuj(boolean rosnaco) {
        for (int i = 0; i < wyniki.size(); i++) {
            for (int j = i + 1; j < wyniki.size(); j++) {

                    int pierwsza = wyniki.get(i);
                    int druga = wyniki.get(j);

                    if ((rosnaco && pierwsza > druga) ||
                            (!rosnaco && pierwsza < druga)) {

                         wyniki.set(i, druga);
                         wyniki.set(j, pierwsza);
                }
            }
        }
    }
    private void odswiez() {
        String tekst = "";

        for (int i = 0; i < wyniki.size(); i++) {
            tekst += wyniki.get(i);

            if (i != wyniki.size() - 1) {
                tekst += ", ";
            }
        }
        wynikiKostek.setText(tekst);
        siatkaKostek.removeAllViews();
        siatkaKostek.setColumnCount(5);
        int rozmiar = (int) (56 * getResources().getDisplayMetrics().density);
        for (int i = 0; i < wyniki.size(); i++) {
            TextView kostka = new TextView(this);
            kostka.setText(String.valueOf(wyniki.get(i)));
            kostka.setTextSize(24);
            kostka.setGravity(Gravity.CENTER);
            kostka.setTextColor(0xFF000000);
            kostka.setBackgroundColor(0xFFE0E0E0);

            GridLayout.LayoutParams ustawienia = new GridLayout.LayoutParams();
            ustawienia.width = rozmiar;
            ustawienia.height = rozmiar;
            kostka.setLayoutParams(ustawienia);
            siatkaKostek.addView(kostka);
        }
    }
}