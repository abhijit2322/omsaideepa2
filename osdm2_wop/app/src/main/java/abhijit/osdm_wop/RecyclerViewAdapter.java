package abhijit.osdm_wop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import abhijit.osdm_wop.models.FlatOwner;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<FlatOwner> item;
    private Context context ;
    private ImageView iv=null;
    private String givenRented="no";
    private String mntStatus;

    public RecyclerViewAdapter(Context context, List<FlatOwner> item ) {
        Log.i("autolog", "RecyclerViewAdapter");
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("autolog", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.owner_activity_main, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("autolog", "onBindViewHolder");
        //holder.hobby.setText(item.get(position).getFlatnumber());
        //holder.name.setText(item.get(position).getOwnername());
        holder.iv.setImageResource(R.drawable.omd2image);

        holder.flatnumber.setText(item.get(position).getFlatnumber());
        holder.ownername.setText(item.get(position).getOwnername());
        holder.ownerphonno.setText(item.get(position).getOwnercontactno());
        holder.owneremailid.setText(item.get(position).getEmail());
        mntStatus=item.get(position).getMaintaincepaid();
        System.out.println("Abhijit mntStatus at  onBindViewHolder >>>>>>>>>>>>>>>>>>"+mntStatus);
       // omd2image


    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, hobby,ownername,flatnumber,ownerphonno,owneremailid;
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("autolog", "ViewHolder");

           // name = (TextView) itemView.findViewById(R.id.ownername);
          //  hobby = (TextView) itemView.findViewById(R.id.flatnumber);
            ownername = (TextView) itemView.findViewById(R.id.ownername);
            flatnumber = (TextView) itemView.findViewById(R.id.flatnumber);
            ownerphonno= (TextView) itemView.findViewById(R.id.owner_phone_number);
            owneremailid= (TextView) itemView.findViewById(R.id.owner_email_id);

            iv=(ImageView) itemView.findViewById(R.id.flatownerimage);

            ownername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Abhijit Name seletcced >>>>>>>>>>>>>>>>>>>>>>"+ownername.getText());
                    Intent opa = new Intent(v.getContext(), Profile_Owner_Activity.class);
                    opa.putExtra("ownername", ownername.getText());
                     opa.putExtra("flatnumber", flatnumber.getText());
                    opa.putExtra("ownerphonno", ownerphonno.getText());
                    opa.putExtra("owneremailid", owneremailid.getText());
                    opa.putExtra("mntstatus",mntStatus);
                 //   v.getContext().startActivity(opa);
                    v.getContext().startActivity(opa);

                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }
}
