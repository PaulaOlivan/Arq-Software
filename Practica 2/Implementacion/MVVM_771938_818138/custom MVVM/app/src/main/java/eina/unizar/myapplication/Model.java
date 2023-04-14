package eina.unizar.myapplication;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class Model {
    int turno = 0;
    int calidad;

    public Model(int turno){
        this.turno = turno;
    }

    public int getTurno(){
        return turno;
    }



    public void setTurno(@Nullable int turno){
        this.turno = turno;
    }

    /*
     *1
     */
    public void checkImage(ImageView im){
        int defaultIm = R.drawable.persona;
        int myTurnIm = R.drawable.myturn;
        int thankYou = R.drawable.ty;
        Drawable currentIm = im.getDrawable();


        int currentImage = ((BitmapDrawable) currentIm).getBitmap().getGenerationId();


        Integer myTurnTag = Integer.parseInt((String) im.getTag());
        int myTurn = myTurnTag.intValue();
        if (myTurn == getTurno() ){
            im.setImageResource((R.drawable.myturn));
        }else {

            im.setImageResource(R.drawable.persona);
        }


    }

    public void giveFish(ImageView im){
        Drawable currentIm = im.getDrawable();
        int currentImage = ((BitmapDrawable) currentIm).getBitmap().getGenerationId();
        int myTurnIm = R.drawable.myturn;

        if (Integer.parseInt((String)im.getTag()) == turno){

            if(im.getId() == R.id.inspecTor){
                Random random = new Random();
                int randomNumber = random.nextInt(3) + 1;

                if(randomNumber == 3){
                    im.setImageResource(R.drawable.ohno);
                    setTurno(-1);
                }
                else{
                    im.setImageResource(R.drawable.ty);
                }

            }else {
                im.setImageResource(R.drawable.ty);
            }
        }else{
            im.setImageResource(R.drawable.wrong);
        }

    }

    public int reroll() {
        setTurno(getTurno() + 1);
        if (getTurno() > 3) {
            setTurno(1);
        }

        return getTurno();
    }


}
