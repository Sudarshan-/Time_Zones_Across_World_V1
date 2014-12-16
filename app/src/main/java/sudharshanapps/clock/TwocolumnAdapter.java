package sudharshanapps.clock;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import android.app.Activity;
import android.widget.TextView;
import android.view.LayoutInflater;


import static sudharshanapps.clock.Constants.FIRST_COLUMN;
import static sudharshanapps.clock.Constants.SECOND_COLUMN;


//Constructing custom adapter instead of standard array adapters etc
public class TwocolumnAdapter extends BaseAdapter {

    //Using constructed Hashmap and Main activity reference
    private ArrayList<String[]> list;
    private Activity activity;

    //Constructor
    public TwocolumnAdapter(Activity activity,ArrayList<String[]> list) {
        super();

        //Assigning contents to local instances
        this.activity = activity;
        this.list = list;
    }

    // Return Adapter size
    @Override
    public int getCount() {
        return list.size();
    }

    private class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;

    }

    public void ChangeTimeFormat(int Counter){

        Iterator iterator = list.iterator();
        Integer index =0;


        while(iterator.hasNext()){
                String[] tempValue = (String[]) iterator.next();
                String[] new_list = new String[2];
                new_list[FIRST_COLUMN] = tempValue[FIRST_COLUMN];
                        try {
                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                            if (Counter == 1) {
                                Date date = _24HourSDF.parse(tempValue[SECOND_COLUMN]);
                                new_list[SECOND_COLUMN] = _12HourSDF.format(date);
                            } else {
                                Date date = _12HourSDF.parse(tempValue[SECOND_COLUMN]);
                                new_list[SECOND_COLUMN] = _24HourSDF.format(date);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                list.set(index, new_list);
                index++;

        }
        this.notifyDataSetChanged();
        /*if(Counter == 0){


        }else{


        }*/
    }

    //Creating view of Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.simplerow, null);
            holder=new ViewHolder();

            holder.txtFirst=(TextView) convertView.findViewById(R.id.rowTextView1);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.rowTextView2);

            convertView.setTag(holder);
        }else{

            holder=(ViewHolder) convertView.getTag();
        }

        String[] map=list.get(position);
        holder.txtFirst.setText(map[FIRST_COLUMN]);
        holder.txtSecond.setText(map[SECOND_COLUMN]);

        return convertView;
    }

    //return selected List item ID
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Returns Item when position is passed
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }




}
