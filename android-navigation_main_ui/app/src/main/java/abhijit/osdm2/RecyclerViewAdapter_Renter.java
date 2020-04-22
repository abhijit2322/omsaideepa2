package abhijit.osdm2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import abhijit.osdm2.R;

import abhijit.osdm2.constants.GlobalVariables;
import abhijit.osdm2.models.Renter;

import java.util.List;
public class RecyclerViewAdapter_Renter extends RecyclerView.Adapter<RecyclerViewAdapter_Renter.ViewHolder> {

    private List<Renter> item;

    private Context context ;
    private ImageView iv=null;
    private String mntStatus;

    public RecyclerViewAdapter_Renter(Context context, List<Renter> item ) {
        Log.i("autolog", "RecyclerViewAdapter");
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("autolog", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.renter_list_adapter_main, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("autolog", "onBindViewHolder");
       // holder.hobby.setText(item.get(position).getRentername());
       // holder.name.setText(item.get(position).getFlatnumber());
        holder.iv.setImageResource(R.drawable.omd2image);

        holder.rflatnumber.setText(item.get(position).getFlatnumber());
        holder.rname.setText(item.get(position).getRentername());
        holder.rphonno.setText(item.get(position).getRentercontactno());
        holder.remailid.setText(item.get(position).getEmail());
        mntStatus=item.get(position).getRmaintaincepaid();
        // omd2image

    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, hobby,rname,rflatnumber,rphonno,remailid;
        public ImageView iv;




        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("autolog", "ViewHolder");

           // name = (TextView) itemView.findViewById(R.id.ownername);
           // hobby = (TextView) itemView.findViewById(R.id.flatnumber);
            iv=(ImageView) itemView.findViewById(R.id.flatrentimage);
            rname = (TextView) itemView.findViewById(R.id.rentname);
            rflatnumber = (TextView) itemView.findViewById(R.id.rentflatnumber);
            rphonno= (TextView) itemView.findViewById(R.id.rent_phone_number);
            remailid= (TextView) itemView.findViewById(R.id.rent_email_id);

            rname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent opa = new Intent(v.getContext(), Profile_Renter_Activity.class);

                    System.out.println("GlobalVariables -rule (RecyclerViewAdapter_Renter)>>>>>>>>>>>>>>>>>>     "+AppSettingsData.GetRule());

                    opa.putExtra("rentname", rname.getText());
                    opa.putExtra("rentflatnumber", rflatnumber.getText());
                    opa.putExtra("rentphonno", rphonno.getText());
                    opa.putExtra("rentemailid", remailid.getText());
                    opa.putExtra("mntstatus", mntStatus);
                    //   v.getContext().startActivity(opa);
                    v.getContext().startActivity(opa);

                }
            });
        }
    }
}
