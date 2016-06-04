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
public class CustomAdapter extends BaseAdapter {
    ArrayList<Seisme> myList = new ArrayList<Seisme>();
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
        MyViewHolder mViewHolder = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.list_item_view, parent, false);

            mViewHolder = new MyViewHolder();
            mViewHolder.textViewTitle = (TextView) convertView
                    .findViewById(R.id.textViewTitle);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Seisme listItem = (Seisme) getItem(position);
        mViewHolder.textViewTitle.setText(listItem.getPlace());
        return convertView;
    }

    public class MyViewHolder{
        TextView textViewTitle;
    }
}
