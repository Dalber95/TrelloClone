package pl.krusiec.trelloclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.krusiec.trelloclone.R

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
    }
}