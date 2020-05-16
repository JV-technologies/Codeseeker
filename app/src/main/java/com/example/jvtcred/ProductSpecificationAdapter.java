package com.example.jvtcred;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {  //because we have multiple layout//
        switch (productSpecificationModelList.get(position).getType()) {  //to check the layout is of which type//
            case 0:
                return ProductSpecificationModel.SPECIFICATION_TITLE;

            case 1:
                return ProductSpecificationModel.SPECIFICATION_BODY;

            default:
                return -1;


        }
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {  //inflate the layout if there is swutch it means that the viewtype will hwlp us to inflate the layout//

        switch (viewType) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:

                TextView title = new TextView(viewGroup.getContext()); // we will not create a whole another layout for just  a text view so we create a text view here///
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //this is to set margin//
                layoutParams.setMargins(setDp(16, viewGroup.getContext())
                        , setDp(16, viewGroup.getContext()),
                        setDp(16, viewGroup.getContext()),
                        setDp(8, viewGroup.getContext()));
                title.setLayoutParams(layoutParams);
                return new ViewHolder(title); //text view is finally created

            case ProductSpecificationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_specification_item_layout, viewGroup, false);
                return new ViewHolder(view);
            default:
                return null;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder viewHolder, int position) { // binding data // we have to extract both the text from list///
        switch (productSpecificationModelList.get(position).getType()) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                viewHolder.setTitle(productSpecificationModelList.get(position).getTitle());
                break;
            case ProductSpecificationModel.SPECIFICATION_BODY:
                String featureTitle = productSpecificationModelList.get(position).getFeatureName(); // title
                String featureDetail = productSpecificationModelList.get(position).getFeatureValue();
                viewHolder.setFeatures(featureTitle, featureDetail); //calling the method through viewholder
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private void setTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);
        }

        private void setFeatures(String featureTitle, String featuredetail) {
            // to set the text in both the textview
            featureName = itemView.findViewById(R.id.feature_name);
            featureValue = itemView.findViewById(R.id.feature_value);
            featureName.setText(featureTitle);
            featureValue.setText(featuredetail);
        }
    }

    private int setDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()); //to get the margin valves in dp
        //inshort iy gives pixels in dp//
    }
}
