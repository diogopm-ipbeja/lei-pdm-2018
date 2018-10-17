package pt.ipbeja.aula3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText countEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        this.linearLayout = findViewById(R.id.linear_list);
        this.countEditText = findViewById(R.id.count);

    }


    private void addContactViews(int count, boolean removeExisting) {
        if(removeExisting) this.linearLayout.removeAllViewsInLayout();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.contact_item, linearLayout, false);
            TextView nameView = view.findViewById(R.id.name);
            TextView emailView = view.findViewById(R.id.email);
            String name = "Contact " + i;
            nameView.setText(name);
            emailView.setText(name + "@cenas.pt");
            linearLayout.addView(view);
        }
        Toast.makeText(this, count + " views added in " + (System.currentTimeMillis() - startTime) + "ms.", Toast.LENGTH_SHORT).show();

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ScrollActivity.class);
        context.startActivity(starter);
    }

    public void createViews(View view) {
        addContactViews(Integer.parseInt(countEditText.getText().toString()), true);
    }

}
