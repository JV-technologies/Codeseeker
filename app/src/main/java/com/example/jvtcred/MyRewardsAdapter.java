package com.example.jvtcred;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.MoreObjects;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    private Boolean useMiniLayout = false;
    private RecyclerView coupensRecyclerView;
    private LinearLayout selectedCoupen;
    private String productOriginalPrice;
    private TextView selectedcoupenTitle;
    private TextView selectedcoupenExpiryDate;
    private TextView selectedcoupenBody;
    private TextView discountedPrice;
    private int cartItemPostion = -1;
    private List<CartItemModel>cartItemModelList;

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView coupensRecyclerView, LinearLayout selectedCoupen, String productOriginalPrice, TextView coupenTitle, TextView coupenExpiryDate, TextView coupenBody, TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.coupensRecyclerView = coupensRecyclerView;
        this.selectedCoupen = selectedCoupen;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedcoupenTitle = coupenTitle;
        this.selectedcoupenExpiryDate = coupenExpiryDate;
        this.selectedcoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
    }

    public MyRewardsAdapter(int cartItemPostion, List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView coupensRecyclerView, LinearLayout selectedCoupen, String productOriginalPrice, TextView coupenTitle, TextView coupenExpiryDate, TextView coupenBody, TextView discountedPrice,List<CartItemModel>cartItemModelList) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.coupensRecyclerView = coupensRecyclerView;
        this.selectedCoupen = selectedCoupen;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedcoupenTitle = coupenTitle;
        this.selectedcoupenExpiryDate = coupenExpiryDate;
        this.selectedcoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
        this.cartItemPostion = cartItemPostion;
        this.cartItemModelList = cartItemModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (useMiniLayout) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewards_item_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String coupenId = rewardModelList.get(position).getCoupenId();
        String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
        String body = rewardModelList.get(position).getCoupenBody();
        String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discORamt = rewardModelList.get(position).getDiscORamt();
        Boolean alreadyUsed = rewardModelList.get(position).isAlreadyUsed();

        viewHolder.setData(coupenId, type, validity, body, upperLimit, lowerLimit, discORamt, alreadyUsed); // to call the above methods

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView coupenTitle;
        private TextView coupenExpiryDate;
        private TextView coupenBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }

        private void setData(final String coupenId, final String type, final Date validity, final String body, final String upperLimit, final String lowerLimit, final String discORamt, final boolean alreadyUsed) {
            if (type.equals("Discount")) {
                coupenTitle.setText(type);

            } else {
                coupenTitle.setText("FLAT Rs." + discORamt + " OFF");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY");

            if (alreadyUsed) {
                coupenExpiryDate.setText("Already Redeemed");
                coupenExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.baby));
                coupenBody.setTextColor(Color.parseColor("#50FFFFFF"));
                coupenTitle.setTextColor(Color.parseColor("#50FFFFFF"));

            } else {
                coupenBody.setTextColor(Color.parseColor("#FFFFFF"));
                coupenTitle.setTextColor(Color.parseColor("#FFFFFF"));
                coupenExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                coupenExpiryDate.setText("till " + simpleDateFormat.format(validity));
            }


            coupenBody.setText(body);


            if (useMiniLayout) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!alreadyUsed) {

                            selectedcoupenTitle.setText(type);  //access the class first//
                            selectedcoupenExpiryDate.setText(simpleDateFormat.format(validity));
                            selectedcoupenBody.setText(body);

                            if (Long.valueOf(productOriginalPrice) > Long.valueOf(lowerLimit) && Long.valueOf(productOriginalPrice) < Long.valueOf(upperLimit)) {

                                if (type.equals("Discount")) {

                                    Long discountAmount = Long.valueOf(productOriginalPrice) * Long.valueOf(discORamt) / 100;
                                    discountedPrice.setText("Rs." + String.valueOf(Long.valueOf(productOriginalPrice) - discountAmount) + "/-");

                                } else {
                                    discountedPrice.setText("Rs." + String.valueOf(Long.valueOf(productOriginalPrice) - Long.valueOf(discORamt)) + "/-");

                                }
                               if (cartItemPostion != -1) {
                                   cartItemModelList.get(cartItemPostion).setSelectedCoupenId(coupenId);
                               }

                            } else {

                                if (cartItemPostion!=-1) {
                                    cartItemModelList.get(cartItemPostion).setSelectedCoupenId(null);
                                }
                                discountedPrice.setText("Invalid");
                                Toast.makeText(itemView.getContext(), "Sorry ! Course does not match the coupon terms", Toast.LENGTH_SHORT).show();
                            }

                            if (coupensRecyclerView.getVisibility() == GONE) {
                                coupensRecyclerView.setVisibility(View.VISIBLE);
                                selectedCoupen.setVisibility(GONE);
                            } else {
                                coupensRecyclerView.setVisibility(GONE);
                                selectedCoupen.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
            }
        }
    }
}