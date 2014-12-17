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
import java.util.List;
import android.graphics.Typeface;

import android.app.Activity;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


import static sudharshanapps.clock.Constants.FIRST_COLUMN;
import static sudharshanapps.clock.Constants.SECOND_COLUMN;


//Constructing custom adapter instead of standard array adapters etc
public class TwocolumnAdapter extends BaseExpandableListAdapter {

    //Using constructed Hashmap and Main activity reference
    private HashMap<String, List<String[]>> list;
    private List<String> headers;
    private Activity activity;

    //Constructor
    public TwocolumnAdapter(Activity activity, HashMap<String, List<String[]>> list, List<String> headers) {
        super();

        //Assigning contents to local instances
        this.activity = activity;
        this.list = list;
        this.headers = headers;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (list.get(headers.get(groupPosition))).size();
    }

    private class HeaderViewHolder {
        TextView txtFirst;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        LayoutInflater inflater = activity.getLayoutInflater();

        HeaderViewHolder headerViewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.simpleheader, null);

            headerViewHolder = new HeaderViewHolder();

            headerViewHolder.txtFirst = (TextView) convertView.findViewById(R.id.lblListHeader);

            convertView.setTag(headerViewHolder);
        } else {

            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }

        headerViewHolder.txtFirst.setTypeface(null, Typeface.BOLD);
        headerViewHolder.txtFirst.setText(headerTitle);

        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (list.get(headers.get(groupPosition))).get(childPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        final String[] rowContent = (String[]) getChild(groupPosition, childPosition);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.simplerow, null);
            holder = new ViewHolder();

            holder.txtFirst = (TextView) convertView.findViewById(R.id.rowTextView1);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.rowTextView2);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtFirst.setText(rowContent[FIRST_COLUMN]);
        holder.txtSecond.setText(rowContent[SECOND_COLUMN]);

        return convertView;
        //return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // Return Adapter size
    /*@Override
    public int getCount() {
        return list.size();
    }*/

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;

    }

}
    /*public void ChangeTimeFormat(int Counter){

        Iterator iterator = list.iterator();
        Integer index =0;


        while(iterator.hasNext()){
            try {
                HashMap<String, String> tempValue = (HashMap<String, String>) iterator.next();
                Set<Map.Entry<String, String>> mapSet = tempValue.entrySet();
                Iterator<Map.Entry<String, String>> mapIterator = mapSet.iterator();
                HashMap<String, String> new_list = new HashMap<>();
                while (mapIterator.hasNext()) {
                    Map.Entry<String, String> mapEntry = mapIterator.next();
                    if (mapEntry.getKey().equals(SECOND_COLUMN)) {
                        try {
                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                            if (Counter == 1) {
                                Date date = _24HourSDF.parse(mapEntry.getValue());
                                new_list.put(mapEntry.getKey(), _12HourSDF.format(date));
                            } else {
                                Date date = _12HourSDF.parse(mapEntry.getValue());
                                new_list.put(mapEntry.getKey(), _24HourSDF.format(date));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        new_list.put(mapEntry.getKey(), mapEntry.getValue());
                    }
                }
                list.set(index, new_list);
                index++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.notifyDataSetChanged();
        /*if(Counter == 0){


        }else{


        }
    }*/

    //Creating view of Adapter
   /*  @Override
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

        HashMap<String, String> map=list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));

        return convertView;
    }*/

    //return selected List item ID
    /*@Override
    public long getItemId(int position) {
        return 0;
    }

    //Returns Item when position is passed
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }*/




