package pt.ipbeja.playground.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pt.ipbeja.playground.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView demoList;
    private DemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demoList = findViewById(R.id.demo_list);
        adapter = new DemoAdapter(DemoData.getDemoList());
        demoList.setAdapter(adapter);

    }


    public class DemoAdapter extends RecyclerView.Adapter<DemoViewHolder> {

        private final List<DemoData.Demo> data;

        public DemoAdapter(List<DemoData.Demo> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.demo_list_item, viewGroup, false);

            return new DemoViewHolder(view);
        }



        @Override
        public void onBindViewHolder(@NonNull DemoViewHolder demoViewHolder, int i) {
            demoViewHolder.bind(data.get(i));
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }
    }

    class DemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView description;
        private DemoData.Demo demo;

        DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
        }

        void bind(DemoData.Demo demo) {
            this.demo = demo;
            this.title.setText(demo.getTitle());
            this.description.setText(demo.getDescription());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, demo.getTargetActivity());
            startActivity(intent);
        }
    }
}
