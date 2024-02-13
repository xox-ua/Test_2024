package com.example.mornhouse_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mornhouse_test.db.Data;

import java.util.List;
import java.util.Objects;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {
    private final Context context;
    private List<Data> dataList;

    public DataListAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public DataListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataListAdapter.MyViewHolder holder, int position) {
        if(Objects.equals(this.dataList.get(position).method, "manual")) {
            holder.llRecycleRow.setBackgroundResource(R.color.light_blue);
        } else {
            holder.llRecycleRow.setBackgroundResource(R.color.light_green);
        }

        holder.tvNumber.setText(this.dataList.get(position).number);
        holder.tvDescription.setText(this.dataList.get(position).description);
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llRecycleRow;
        TextView tvNumber;
        TextView tvDescription;

        public MyViewHolder(View view){
            super(view);
            llRecycleRow = view.findViewById(R.id.llRecycleRow);
            tvNumber = view.findViewById(R.id.tvNumber);
            tvDescription = view.findViewById(R.id.tvDescription);

            view.setOnClickListener(cView -> {
                ColorDrawable viewColor = (ColorDrawable) llRecycleRow.getBackground();
                int colorId = viewColor.getColor();

                Intent intent = new Intent(cView.getContext(), SecondActivity.class);
                intent.putExtra("color", String.format("#%06X", (0xFFFFFF & colorId)));
                intent.putExtra("number", tvNumber.getText());
                intent.putExtra("description", tvDescription.getText());
                cView.getContext().startActivity(intent);

            });
        }

    }
}
