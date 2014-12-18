package sudharshanapps.clock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


import static sudharshanapps.clock.Constants.FIRST_COLUMN;
import static sudharshanapps.clock.Constants.SECOND_COLUMN;


//Constructing custom adapter instead of standard array adapters etc
public class TwocolumnAdapter extends BaseExpandableListAdapter {

    //Using constructed Hashmap and Main activity reference(Child list)
    private HashMap<String, ArrayList<String[]>> list;

    //Headers list
    private ArrayList<String> headers;

    //Context passed from Activity
    private Context context;

    //Constructor
    public TwocolumnAdapter(Context context, HashMap<String, ArrayList<String[]>> list, ArrayList<String> headers) {
        super();

        //Assigning contents to local instances
        this.context = context;
        this.list = list;
        this.headers = headers;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (list.get(headers.get(groupPosition))).size(); // Returning child count of each header from hashmap
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition); //retrieving Group header title from headers list using group position

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.simpleheader, null);

        }

        TextView txtFirst = (TextView) convertView.findViewById(R.id.lblListHeader); // Obtaining reference of header from layout

        txtFirst.setText(headerTitle); // Assigning text to header

        return convertView; // returning head view
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition); // returning header string using group position
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition; //returning group position
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (list.get(headers.get(groupPosition))).get(childPosition); //returning child arraylist using group reference
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String[] rowContent = (String[]) getChild(groupPosition, childPosition); //Obtaining list of children for a group

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simplerow, null);
        }
            //Obtaining Child 1 references
            TextView txtFirst = (TextView) convertView.findViewById(R.id.rowTextView1);

            //Obtaining child 2 reference
            TextView txtSecond = (TextView) convertView.findViewById(R.id.rowTextView2);

        txtFirst.setText(rowContent[FIRST_COLUMN]); //Assigning content to children
        txtSecond.setText(rowContent[SECOND_COLUMN]); //Assigning content to children

        return convertView;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition; // Returning child reference
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return headers.size(); // Returning no of headers
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


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






