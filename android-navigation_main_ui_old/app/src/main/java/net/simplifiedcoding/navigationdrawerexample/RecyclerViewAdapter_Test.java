package net.simplifiedcoding.navigationdrawerexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.simplifiedcoding.navigationdrawerexample.models.FlatOwner;

import java.util.List;


public abstract class RecyclerViewAdapter_Test extends RecyclerView.Adapter<RecyclerViewAdapter_Test.ViewHolder> {

    private List<FlatOwner> item;
    private Context context ;
    private ImageView iv=null;

    public RecyclerViewAdapter_Test(Context context, List<FlatOwner> item ) {
        Log.i("autolog", "RecyclerViewAdapter");
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("autolog", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.test_view_adopter, null);

        return new RecyclerViewAdapter_Test.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("autolog", "onBindViewHolder");
        holder.hobby.setText(item.get(position).getFlatnumber());
        holder.name.setText(item.get(position).getOwnername());
        holder.iv.setImageResource(R.drawable.omd2image);
       // omd2image

    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, hobby;
        public ImageView iv;




        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("autolog", "ViewHolder");

            name = (TextView) itemView.findViewById(R.id.ownername);
            hobby = (TextView) itemView.findViewById(R.id.flatnumber);
            iv=(ImageView) itemView.findViewById(R.id.flatownerimage);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }
}
