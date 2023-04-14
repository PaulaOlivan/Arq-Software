package eina.unizar.myapplication;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.Toast;
import eina.unizar.myapplication.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ViewModel extends BaseObservable {

    private Model model;
    private Context context;
    private boolean mIsScreenTapped;
    private int mCounter=1;


    public void setClientTap(android.widget.ImageView im) {
        model.giveFish(im);
    }

    /*
    @Bindable
    public boolean isScreenTapped() {
        return mIsScreenTapped;
    }
    */

    public void screenTap() {
        //mIsScreenTapped = true;
        ImageView cliente1 = ((Activity) context).findViewById(R.id.clienT1);
        ImageView cliente2 = ((Activity) context).findViewById(R.id.clienT2);
        ImageView cliente3 = ((Activity) context).findViewById(R.id.inspecTor);



        setCounter(model.reroll());
        notifyPropertyChanged(BR.counter);
        //notifyPropertyChanged(BR.screenTapped); // notify that screenTapped has changed
        model.checkImage(cliente1);
        model.checkImage(cliente2);
        model.checkImage(cliente3);
        //Toast.makeText(context, String.valueOf(getCounter()), Toast.LENGTH_SHORT).show();



    }

    @Bindable
    public int getCounter(){
        return model.getTurno();
    }

    public String getSTurno(){ return String.valueOf(model.getTurno());}





    public void setCounter(int counter) {
        mCounter = counter;
        model.setTurno(counter);
        notifyPropertyChanged(BR.counter);
    }


    // constructor of ViewModel class
    public ViewModel(Context context) {

        // instantiating object of
        // model class
        // valor de turno puesto a 0
        this.context = context;
        model = new Model(0);
        mCounter = model.getTurno();

    }


}