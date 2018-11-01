package ashad.app.torch.flashlight.todo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    dbhelper dbhelper;
    ListView listViewoftask;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewoftask=findViewById(R.id.todolist);
        dbhelper=new dbhelper(this);
        loadalltask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch(id)
        {
            case R.id.addtodo:
                //show alert box

                showalertbox();
        }
        return super.onOptionsItemSelected(item);

    }

    public void showalertbox()
    {
        final EditText edittext=new EditText(this);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle("Add new Task")
                .setMessage("What to do today")
                .setView(edittext)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String task=edittext.getText().toString();
                        dbhelper.insertNewTask(task);
                        //todo load all task
                        loadalltask();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        alertDialog.show();

    }
    public void loadalltask()
    {

        ArrayList<String> listoftaskes=dbhelper.getTasskLit();
        if (arrayAdapter==null){
            arrayAdapter=new ArrayAdapter<>(this,R.layout.ampleitem,R.id.taskindividual,listoftaskes);
            listViewoftask.setAdapter(arrayAdapter);
        }
        else
        {
            arrayAdapter.clear();
            arrayAdapter.addAll(listoftaskes);
            arrayAdapter.notifyDataSetChanged();
            //todo i think i nedd to add vie here
        }
    }

    public void deletetask(View v)
    {
        try {
            int index=listViewoftask.getPositionForView(v);
            String task=arrayAdapter.getItem(index++);
            dbhelper.deleteTask(task);
            loadalltask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
