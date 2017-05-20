package org.tll.falciniz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by burhanboz on 20/05/2017.
 */
public class OldMagicAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EskiFallar>     eskiFallarList;

    public OldMagicAdapter(Activity activity, List<EskiFallar> fallar) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        eskiFallarList = fallar;
    }

    @Override
    public int getCount() {
        return eskiFallarList.size();
    }

    @Override
    public EskiFallar getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return eskiFallarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.activity_column_old_magics,null);
        TextView textView =
                (TextView) satirView.findViewById(R.id.activity_old_magic_content);
        ImageView imageView = (ImageView) satirView.findViewById(R.id.activity_list_image);

        Random r = new Random();
        int rand =r.nextInt(1) + 1;

        if (rand == 1){
            imageView.setImageResource(R.drawable.falci_1);
        }else if (rand == 2 ){
            imageView.setImageResource(R.drawable.falci_2);
        }else{
            imageView.setImageResource(R.drawable.falci_kadin_5);
        }

        EskiFallar fallar = eskiFallarList.get(position);


        textView.setText(fallar.getMagic());

        return satirView;
    }
}
