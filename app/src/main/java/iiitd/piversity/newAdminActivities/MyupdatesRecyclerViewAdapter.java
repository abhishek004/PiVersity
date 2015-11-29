package iiitd.piversity.newAdminActivities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import iiitd.piversity.R;
import iiitd.piversity.newAdminActivities.updatesFragment.OnUpdatesListFragmentInteractionListener;
import iiitd.piversity.newAdminActivities.Updates.UpdatesContent.UpdatesItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UpdatesItem} and makes a call to the
 * specified {@link OnUpdatesListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyupdatesRecyclerViewAdapter extends RecyclerView.Adapter<MyupdatesRecyclerViewAdapter.ViewHolder> {

    private final List<UpdatesItem> mValues;
    private final OnUpdatesListFragmentInteractionListener mListener;

    public MyupdatesRecyclerViewAdapter(List<UpdatesItem> items, OnUpdatesListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_updates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).content);
        holder.mContentView.setText(mValues.get(position).details);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onUpdatesListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public UpdatesItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.updatesid);
            mContentView = (TextView) view.findViewById(R.id.updatescontent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
