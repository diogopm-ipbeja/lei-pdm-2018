package pt.ipbeja.aula3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.hello_world);

    }

    public void openScrollActivity(View view) {
        ScrollActivity.start(this);
    }

    public void openRecyclerActivity(View view) {
        RecyclerActivity.start(this);
    }
}
