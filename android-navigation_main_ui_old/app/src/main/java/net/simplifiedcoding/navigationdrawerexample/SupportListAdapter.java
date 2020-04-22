package net.simplifiedcoding.navigationdrawerexample;
import android.app.LauncherActivity;
import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

import net.simplifiedcoding.navigationdrawerexample.models.SupportTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tutlane on 23-08-2017.
 */
public class SupportListAdapter extends BaseAdapter {
    private List<SupportTable> listData;
    private LayoutInflater layoutInflater;
    public SupportListAdapter(Context aContext, List<SupportTable> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        System.out.println(".........getCount............"+listData);

        if(listData!=null)
        return listData.size();
        else
            return 0;
    }
    @Override
    public Object getItem(int position) {
        System.out.println(".........getItem............"+position);
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        System.out.println(".........getItemId............"+position);
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        System.out.println(".........getView............");
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uDesignation = (TextView) v.findViewById(R.id.designation);
            holder.uLocation = (TextView) v.findViewById(R.id.location);

            holder.e_uName = (TextView) v.findViewById(R.id.ename);
            holder.e_uDesignation = (TextView) v.findViewById(R.id.edesignation);
            holder.e_uLocation = (TextView) v.findViewById(R.id.elocation);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getCurpaintername());
        holder.uDesignation.setText("Curpainter");
        holder.uLocation.setText(listData.get(position).getCuepainternumber());

        holder.e_uName.setText(listData.get(position).getElectrcianname());
        holder.e_uDesignation.setText("Electrician");
        holder.e_uLocation.setText(listData.get(position).getElectrciannumber());

        return v;
    }
    static class ViewHolder {
        TextView uName;
        TextView uDesignation;
        TextView uLocation;

        TextView e_uName;
        TextView e_uDesignation;
        TextView e_uLocation;
    }
}