package pt.ipbeja.aula4;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.aula4.data.Task;
import pt.ipbeja.aula4.data.TasksDatabase;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.adapter = new TaskAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Task> tasks = TasksDatabase
                .getInstance(this)
                .taskDao()
                .getAllTasks();
        this.adapter.setData(tasks);


    }

    public void createNewTask(View view) {
        CreateTaskActivity.start(this);
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder {


        private TextView title;
        private TextView description;
        private ImageView delete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.task_title);
            this.description = itemView.findViewById(R.id.task_description);
            this.delete = itemView.findViewById(R.id.delete_task);


            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(MainActivity.this, "Item clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });

            this.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //Toast.makeText(MainActivity.this, "Delete btn clicked "+ position, Toast.LENGTH_SHORT).show();
                    adapter.delete(position);
                }
            });


        }

        public void bind(Task task) {
            this.title.setText(task.getTitle());
            this.description.setText(task.getDescription());
        }


    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

        private List<Task> data = new ArrayList<>();

        public void delete(int position) {
            Task task = data.get(position);
            TasksDatabase.getInstance(MainActivity.this).taskDao().delete(task);
            data.remove(position);
            notifyItemRemoved(position);


        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.task_list_item, viewGroup, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
            Task task = data.get(position);
            taskViewHolder.bind(task);
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public void setData(List<Task> tasks) {
            this.data = tasks;
            notifyDataSetChanged();
        }
    }

}













