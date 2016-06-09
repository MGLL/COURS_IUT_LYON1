package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/06/2016.
 */

/* Cette class, CustomAdapter, sert à adapter comme on le souhaite les éléments de la listView.
    (Peut ajouter des images, du texte supplémentaire ou une couleur spécifique pour le texte. */

public class CustomAdapter extends BaseAdapter {
    //On déclare une array liste pour récupérer les informations reçu sur les séismes.
    ArrayList<Seisme> myList = new ArrayList<>();
    Context context;

    public CustomAdapter(Context context,ArrayList<Seisme> myList) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public int getCount() {return myList.size();}

    @Override
    public Object getItem(int position) {return myList.get(position);}

    @Override
    public long getItemId(int position) {return myList.indexOf(getItem(position));}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //C'est ici que l'on va personnaliser notre ListView comme on le souhaite.
        MyViewHolder mViewHolder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            //On récupère l'exemple pour personnaliser.
            convertView = mInflater.inflate(R.layout.list_item_view, parent, false);

            mViewHolder = new MyViewHolder();
            mViewHolder.textViewTitle = (TextView) convertView
                    .findViewById(R.id.textViewTitle);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Seisme listItem = (Seisme) getItem(position);
        //On ne récupère que la "place" pour afficher dans la listView.
        mViewHolder.textViewTitle.setText(listItem.getPlace());
        return convertView;
    }

    public class MyViewHolder{
        TextView textViewTitle;
    }
}
