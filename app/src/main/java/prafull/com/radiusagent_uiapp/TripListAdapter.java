package prafull.com.radiusagent_uiapp;

import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder>
{
    private List<Trip> itemList;
    private Context context;

    public TripListAdapter(Context context, List<Trip> itemList)
    {

        this.context = context;
        this.itemList = itemList;
    }


    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.triplistrecyc_layout, parent, false);
        TripListAdapter.ViewHolder vh = new TripListAdapter.ViewHolder(v);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView from,to,costval,costsym,tripdurat;

        public ViewHolder(View itemView)
        {
            super(itemView);
            from=itemView.findViewById(R.id.fromtit1);
            to=itemView.findViewById(R.id.totit1);
            costval=itemView.findViewById(R.id.totalval1);
            costsym=itemView.findViewById(R.id.totalsymbol1);
            tripdurat=itemView.findViewById(R.id.travtimval1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Trip tr=itemList.get(position);
        holder.from.setText(tr.getFrom());
        holder.to.setText(tr.getTo());
        holder.costval.setText(tr.getCostval());
        Log.d("Sym",tr.getCostsymb());
        holder.costsym.setText(tr.getCostsymb());
        int durval=Integer.parseInt(tr.getTripdura().toString());
        int min=0;
        if(durval<60 && durval>min)
        {
            holder.tripdurat.setText(tr.getTripdura()+"min");
        }else if(durval>60)
        {
            int hr=0;
            min=durval%60;
            hr=durval/60;
            holder.tripdurat.setText(hr+"h"+" "+min+"min");



        }
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
