package eina.unizar.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import eina.unizar.myapplication.ViewModel;
import eina.unizar.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModel viewModel = new ViewModel(this);
        activityMainBinding.setViewModel(viewModel); // set the view model
        activityMainBinding.executePendingBindings();

    }


    @BindingAdapter("counterToString")
    public static void setCounterToString(TextView textView, int value) {
        //textView.setText(String.valueOf(value));
    }
}





