package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TodoDetailActivity extends AppCompatActivity {

    private int mTodoIndex;
    private String[] todoDetails;

    /* Any calling activity would call this static method and pass the necessary
       key, value data pair in an intent object. */
    public static Intent newIntent(Context packageContext, int todoIndex){
        Intent intent = new Intent(packageContext, TodoDetailActivity.class);
        intent.putExtra(TODO_INDEX,todoIndex);
        return intent;
    }

    /* name, value pair to be returned in an intent */
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    /* In case of state change, due to rotating the phone
       store the mTodoIndex to display the same mTodos element post state change

       N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
       the alternative is to abstract view data to a ViewModel which can be in scope in all
       Activity states and more suitable for larger amounts of data
    */
    private static final String TODO_INDEX = "com.example.todoIndex";

    /* override to write the value of mTodoIndex into the Bundle with TODO_INDEX as its key */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        /* TODO: refactor to a data layer */
        Resources res = getResources();
        todoDetails = res.getStringArray(R.array.todo_detail);

        /* get the intent extra int for the todos index */
        int mTodoIndex = getIntent().getIntExtra(TODO_INDEX, 0);
        updateTextViewTodoDetail(mTodoIndex);

        CheckBox checkboxIsComplete = (CheckBox)findViewById(R.id.checkBoxIsComplete);
        /* Register the onClick listener with the generic implementation mTodoListener */
        checkboxIsComplete.setOnClickListener(mTodoListener);

    }

    private void updateTextViewTodoDetail(int todoIndex) {

        final TextView textViewTodoDetail;
        textViewTodoDetail = (TextView) findViewById(R.id.textViewTodoDetail);

        /* display the first task from mTodo array in the TodoTextView */
        textViewTodoDetail.setText(todoDetails[todoIndex]);

    }

    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private OnClickListener mTodoListener = new OnClickListener() {
        public void onClick(View v) {
            // get the clicked object and do something
            switch (v.getId() /*to get clicked view object id**/) {
                case R.id.checkBoxIsComplete:
                    CheckBox checkboxIsComplete = (CheckBox)findViewById(R.id.checkBoxIsComplete);
                    setIsComplete(checkboxIsComplete.isChecked());
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void setIsComplete(boolean isChecked) {

        /* celebrate with a static Toast! */
        if(isChecked){
            Toast.makeText(TodoDetailActivity.this,
                    "Hurray, it's done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TodoDetailActivity.this,
                    "There is always tomorrow!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.putExtra(IS_TODO_COMPLETE, isChecked);
        setResult(RESULT_OK, intent);
    }

}

