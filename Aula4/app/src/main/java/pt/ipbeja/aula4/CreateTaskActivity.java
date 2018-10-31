package pt.ipbeja.aula4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pt.ipbeja.aula4.data.Task;
import pt.ipbeja.aula4.data.TasksDatabase;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        this.titleInput = findViewById(R.id.title);
        this.descriptionInput = findViewById(R.id.description);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateTaskActivity.class);
        context.startActivity(starter);
    }

    public void onCreateTaskClicked(View view) {
        String title = this.titleInput.getText().toString();
        String description = this.descriptionInput.getText().toString();
        Task task = new Task(0, title, description);

        TasksDatabase.getInstance(this).taskDao().insert(task);

        finish();

    }
}

















