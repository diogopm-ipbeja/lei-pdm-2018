package pt.ipbeja.playground.demo;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.playground.aula1.CounterActivity;
import pt.ipbeja.playground.aula1.ResultActivity;

public class DemoData {

    public static List<Demo> getDemoList() {
        List<Demo> demos = new ArrayList<>();
        demos.add(new Demo(CounterActivity.class, "Counter", "Aula 1 (3 Oct 2018) - TextView, EditText, Button e iniciar Activities com dados extra"));
        return demos;
    }


    public static class Demo {

        private Class<? extends AppCompatActivity> targetActivity;
        private String title;
        private String description;

        public Demo(Class<? extends AppCompatActivity> targetActivity, String title, String description) {
            this.targetActivity = targetActivity;
            this.title = title;
            this.description = description;
        }

        public Class<? extends AppCompatActivity> getTargetActivity() {
            return targetActivity;
        }

        public void setTargetActivity(Class<? extends AppCompatActivity> targetActivity) {
            this.targetActivity = targetActivity;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
