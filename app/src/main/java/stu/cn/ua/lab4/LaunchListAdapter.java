package stu.cn.ua.lab4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LaunchListAdapter extends RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder> {

    private List<Launch> launches;
    private Context context;

    public LaunchListAdapter() {
        this.launches = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLaunches(List<Launch> launches) {
        this.launches = launches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.launch_item, parent, false);
        return new LaunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchViewHolder holder, int position) {
        if (launches != null) {
            Launch launch = launches.get(position);
            holder.missionNameTextView.setText(launch.getMissionName());
            holder.launchDateTextView.setText(launch.getLaunchDateUtc());

            holder.itemView.setOnClickListener(v -> {
                // Перехід до DetailActivity та передача даних через Intent
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("missionName", launch.getMissionName());
                intent.putExtra("launchDate", launch.getLaunchDateUtc());
                intent.putExtra("details", launch.getDetails());
                intent.putExtra("missionPatchUrl", launch.getLinks().getMissionPatch());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return launches != null ? launches.size() : 0;
    }

    public static class LaunchViewHolder extends RecyclerView.ViewHolder {

        TextView missionNameTextView;
        TextView launchDateTextView;

        public LaunchViewHolder(@NonNull View itemView) {
            super(itemView);
            missionNameTextView = itemView.findViewById(R.id.missionNameTextView);
            launchDateTextView = itemView.findViewById(R.id.launchDateTextView);
        }
    }
}
