package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.room_database.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private Button btnAdd;
    private RecyclerView rsvUser;

    private UserAdapter userAdapter;
    private List<User> mListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        userAdapter = new UserAdapter();
        mListUser = new ArrayList<>();
        userAdapter.setData(mListUser);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rsvUser.setLayoutManager(linearLayoutManager);
        rsvUser.setAdapter(userAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });


    }

    private void addUser() {
        String name = txtName.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            return;
        }
        User user = new User(name);
        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this,"Add user sucessfully",Toast.LENGTH_SHORT).show();
        txtName.setText("");
        hideSoftKeyboard();

        mListUser =  UserDatabase.getInstance(this).userDAO().getListUser();
        userAdapter.setData(mListUser);
    }

    private void initUI(){
        txtName = findViewById(R.id.txtName);
         btnAdd = findViewById(R.id.btnAdd);
         rsvUser = findViewById(R.id.rscUser);
    }

    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}